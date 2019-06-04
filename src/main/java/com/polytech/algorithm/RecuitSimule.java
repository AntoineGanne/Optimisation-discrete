package com.polytech.algorithm;

import com.polytech.landscape.BasicPermutation;
import com.polytech.landscape.Landscape;
import com.polytech.landscape.Permutation;
import com.polytech.logger.FileLogger;
import com.polytech.logger.FitnessLogger;
import com.polytech.model.ProblemModel;
import com.polytech.util.ConfigurationUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RecuitSimule implements GenericAlgorithm<int[],ProblemModel> {
    private int nbMaxSteps =10000;
    Landscape<int[],Permutation> landscape;

    double mu=0.99;
    double p;

    FitnessLogger fitnessLogger=new FitnessLogger("./resultats/fitnessRecuit.txt");


    private int[] bestSolution;
    private long bestFitness;
    private int stepOfBestSolution;

    /**
     * constructor
     * @param landscapeName
     * @param nbStepsInput
     * @param mu_ coefficient de diminution de la température
     * @param p_ compris entre 0 et 1, il correspond a la proba d'accepter le pire voisin au temps zéro.
     *           Plus il est grand, et plus l'algo pourra sortir des minimas locaux
     *           mais plus il met du temps a se stabiliser.
     * @throws Exception
     */
    public RecuitSimule(String landscapeName, int nbStepsInput,double mu_,double p_) throws Exception {
        assert (landscapeName!=null && !landscapeName.isEmpty());
        landscape=Landscape.getLandscape(landscapeName);
        nbMaxSteps=nbStepsInput;
        mu=mu_;
        p=p_;
    }

    private void writeBaseInfosOnLoggers(String nameProblemModel){
        StringBuilder introductionBuilder = new StringBuilder();
        introductionBuilder.append("Algorithme de Recuit Simulé sur \t"+nameProblemModel+" \n");
        introductionBuilder.append("mu="+mu+"\t p="+p+"\t nbMaxSteps="+nbMaxSteps+"\n");
        fitnessLogger.writeLine(introductionBuilder.toString());
        fitnessLogger.writeEntete();
    }

    @Override
    public int[] resolve(ProblemModel model) {
        int[][] dist = model.getDist();
        int[][] weight = model.getWeight();
        int[] initialSolution=new int[model.getN()];
        for(int i=0;i<initialSolution.length;++i){
            initialSolution[i]=i+1;
        }
        double initialTemp=calculateInitialTemperature(0.8,weight,dist);
        writeBaseInfosOnLoggers(model.getName());

        return resolve(weight,dist,initialSolution,initialTemp);
    }

    public int[] resolve(final int[][] weight,final int[][] dist, int[] initialSolution,double initialTemp) {
        Random rdm=new Random();
        assert (initialTemp > 0);
        bestSolution =initialSolution.clone();
        bestFitness=ConfigurationUtil.getFitness(initialSolution,weight,dist);
        double temp=initialTemp;
        int[] solution=initialSolution.clone();
        long fitnessSolution=ConfigurationUtil.getFitness(solution,weight,dist);
        for(int k = 0; k< nbMaxSteps; ++k){
            fitnessLogger.writeLineFitness(k,solution,fitnessSolution);

            // sélection d'un voisin aléatoire
            int[] randomNeighbor = landscape.getRandomNeighbor(solution);

            //calcul de la différence de fitness
            long fitnessNeighbor=ConfigurationUtil.getFitness(randomNeighbor,weight,dist);
            long fitnessDifference=fitnessNeighbor-fitnessSolution;

            //application des conditions de sélection du voisin
            if(fitnessDifference<=0){
                solution=randomNeighbor;
                fitnessSolution=fitnessNeighbor;
            }else{
                float p=rdm.nextFloat();
                if(p<Math.exp(-fitnessDifference/temp)){
                    solution=randomNeighbor;
                    fitnessSolution=fitnessNeighbor;
                }
            }

            //on verifie si la fitness de la nouvelle solution est meilleure
            if(fitnessNeighbor<bestFitness){
                bestSolution=solution.clone();
                bestFitness=fitnessNeighbor;
                stepOfBestSolution=k;
                System.out.println("(pas n"+k+") nouvelle meilleure solution trouvée!, fitness= "+bestFitness);
                System.out.println("solution: "+ConfigurationUtil.ConfigToString(bestSolution)+"\n");

            }

            temp*=mu;
        }

        System.out.println("Recuit Simulé:\n" +
                "solution trouvée:" + Arrays.toString(bestSolution)+"\n" +
                "avec fitness de:"+bestFitness);
        return bestSolution;
    }

    private Double calculateInitialTemperature(final double p,final int[][] weight,final int[][] dist){
        int n=weight.length;
        long worstFitnessDifference=0;

        for(int k=0;k<3;++k){
            int[] rdmConfig = ConfigurationUtil.randomConfiguration(n);
            long fitnessConfig=ConfigurationUtil.getFitness(rdmConfig,weight,dist);
            List<int[]> allNeighbors = landscape.getNeighbors(rdmConfig);
            long worstFitnessNeighbor=0;
            for(int i=0;i<n;++i){
                long fitnessNeighbor = ConfigurationUtil.getFitness(allNeighbors.get(i), weight, dist);
                if(fitnessNeighbor>worstFitnessNeighbor){
                    worstFitnessNeighbor=fitnessNeighbor;
                }
            }
            long fitnesDiff=worstFitnessNeighbor-fitnessConfig;
            if(fitnesDiff>worstFitnessDifference){
                worstFitnessDifference=fitnesDiff;
            }
        }

        double t0 = -worstFitnessDifference / Math.log(p);
        return t0;
    }

    @Override
    public int[] getBestSolution() {
        return bestSolution;
    }

    @Override
    public long getBestFitness() {
        return bestFitness;
    }

    @Override
    public int getStepOfBestSolution() {
        return stepOfBestSolution;
    }
}
