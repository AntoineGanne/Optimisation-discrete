package com.polytech.algorithm;

import com.polytech.landscape.BasicPermutation;
import com.polytech.landscape.Landscape;
import com.polytech.model.ProblemModel;
import com.polytech.util.ConfigurationUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RecuitSimule implements GenericAlgorithm<int[],ProblemModel> {
    final int MAX_STEPS=100000;
    Landscape<int[]> landscape=new BasicPermutation();

    @Override
    public int[] resolve(ProblemModel model) {
        int[][] dist = model.getDist();
        int[][] weight = model.getWeight();
        int[] initialSolution=new int[model.getN()];
        for(int i=0;i<initialSolution.length;++i){
            initialSolution[i]=i;
        }
        double initialTemp=ConfigurationUtil.getFitness(initialSolution,weight,dist);
        return resolve(weight,dist,initialSolution,initialTemp);
    }

    public int[] resolve(final int[][] weight,final int[][] dist, int[] initialSolution,double initialTemp) {
        Random rdm=new Random();
        double mu=0.999;
        int[] minSol =initialSolution.clone();
        long minFitness=ConfigurationUtil.getFitness(initialSolution,weight,dist);
        int i=0;
        double temp=initialTemp;
        int[] solution=initialSolution.clone();
        for(int k=0;k<MAX_STEPS;++k){
            // sélection d'un voisin aléatoire
            List<int[]> neighbors = landscape.getNeighbors(solution);
            int randomIndex=rdm.nextInt(neighbors.size());
            int[] randomNeighbor=neighbors.get(randomIndex);

            //calcul de la différence de fitness
            long fitnessSolution=ConfigurationUtil.getFitness(solution,weight,dist);
            long fitnessNeighbor=ConfigurationUtil.getFitness(randomNeighbor,weight,dist);
            long fitnessDifference=fitnessNeighbor-fitnessSolution;

            //application des conditions de sélection du voisin
            if(fitnessDifference<=0){
                solution=randomNeighbor;
            }else{
                float p=rdm.nextFloat();
                if(p<Math.exp(-fitnessDifference/temp)){
                    solution=randomNeighbor;
                }
            }

            //on verifie si la fitness de la nouvelle solution est meilleure
            if(fitnessNeighbor<minFitness){
                minSol=solution.clone();
                minFitness=fitnessNeighbor;
                System.out.println("(pas n"+k+") nouvelle meilleure solution trouvée!, fitness= "+minFitness);
                System.out.println("solution: "+ConfigurationUtil.ConfigToString(minSol)+"\n");

            }
            temp*=mu;
        }

        System.out.println("Recuit Simulé:\n" +
                "solution trouvée:" + Arrays.toString(minSol)+"\n" +
                "avec fitness de:"+minFitness);
        return minSol;
    }

}
