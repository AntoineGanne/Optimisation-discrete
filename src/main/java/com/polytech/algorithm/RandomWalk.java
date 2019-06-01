package com.polytech.algorithm;

import com.polytech.landscape.BasicPermutation;
import com.polytech.landscape.ElementaryOperation;
import com.polytech.landscape.Landscape;
import com.polytech.landscape.Permutation;
import com.polytech.logger.FitnessLogger;
import com.polytech.model.ProblemModel;
import com.polytech.util.ConfigurationUtil;
import com.sun.istack.internal.NotNull;

import java.util.List;
import java.util.Random;

public class RandomWalk implements GenericAlgorithm<int[],ProblemModel> {
    int numberOfSteps=1000;
    Landscape<int[],Permutation> landscape=new BasicPermutation();

    FitnessLogger fitnessLogger =new FitnessLogger("./resultats/fitnessRandom.txt");

    public RandomWalk(@NotNull String landscapeName, int nbStepsInput) throws Exception {
        assert (landscapeName!=null && !landscapeName.isEmpty());
        landscape=Landscape.getLandscape(landscapeName);
        numberOfSteps=nbStepsInput;
    }

    @Override
    public int[] resolve(ProblemModel model) {
        Random rdm=new Random();
        int n=model.getN();
        final int[][] dist = model.getDist();
        final int[][] weight = model.getWeight();
        int[] initialSolution=ConfigurationUtil.randomConfiguration(model.getN());
        int[] bestSolution=initialSolution.clone();
        int[] solution=initialSolution.clone();
        long fitness= ConfigurationUtil.getFitness(solution,weight,dist);
        long bestfitness=fitness;

        writeBaseInfosOnLoggers(model.getName());

        for(int i=0;i<numberOfSteps;++i){
            fitnessLogger.writeLineFitness(i,solution,fitness);
            solution=landscape.getRandomNeighbor(solution);
            fitness = ConfigurationUtil.getFitness(solution, weight, dist);
            if(fitness<bestfitness){
                bestfitness=fitness;
                bestSolution=solution.clone();
            }
        }
        return bestSolution;
    }

    private void writeBaseInfosOnLoggers(String nameProblemModel){
        StringBuilder introductionBuilder = new StringBuilder();
        introductionBuilder.append("Algorithme de marche aléatoire sur \t"+nameProblemModel+" \n");
        introductionBuilder.append("nbMaxSteps="+numberOfSteps+"\n");
        fitnessLogger.writeLine(introductionBuilder.toString());
        fitnessLogger.writeEntete();
    }
}
