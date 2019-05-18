package com.polytech.algorithm;

import com.polytech.landscape.BasicPermutation;
import com.polytech.landscape.Landscape;
import com.polytech.model.ProblemModel;
import com.polytech.util.ConfigurationUtil;

import java.util.List;
import java.util.Random;

public class RandomWalk implements GenericAlgorithm<int[],ProblemModel> {
    int numberOfSteps=1000;
    Landscape<int[]> landscape=new BasicPermutation();

    @Override
    public int[] resolve(ProblemModel model) {
        Random rdm=new Random();
        final int[][] dist = model.getDist();
        final int[][] weight = model.getWeight();
        int[] initialSolution=new int[model.getN()];
        for(int i=0;i<initialSolution.length;++i){
            initialSolution[i]=i;
        }
        int[] bestSolution=initialSolution.clone();
        int[] solution=initialSolution.clone();
        double bestfitness=Double.MAX_VALUE;
        for(int i=0;i<numberOfSteps;++i){
            List<int[]> neighbors = landscape.getNeighbors(solution);
            int randomIndex=rdm.nextInt(neighbors.size());
            solution=neighbors.get(randomIndex).clone();
            double fitness = ConfigurationUtil.getFitness(solution, weight, dist);
            if(fitness<bestfitness){
                bestfitness=fitness;
                bestSolution=solution.clone();
            }
        }
        return bestSolution;
    }
}
