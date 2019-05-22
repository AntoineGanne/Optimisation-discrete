package com.polytech.algorithm;

import com.polytech.landscape.BasicPermutation;
import com.polytech.landscape.Landscape;
import com.polytech.landscape.Permutation;
import com.polytech.model.ProblemModel;
import com.polytech.util.ConfigurationUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MethodeTabou implements GenericAlgorithm<int[],ProblemModel> {
    private static final int SIZE_TABOUS=5;
    private static final int NB_STEPS=1000;
    private Landscape<int[],Permutation> landscape=new BasicPermutation();
    private static String filePath="./resultats/fitness.txt";
    private BufferedWriter bufferedWriter;

    public MethodeTabou() {

    }


    @Override
    public int[] resolve(ProblemModel model) {
        int[][] dist = model.getDist();
        int[][] weight = model.getWeight();
        int[] initialSolution=new int[model.getN()];
        for(int i=0;i<initialSolution.length;++i){
            initialSolution[i]=i;
        }
        return resolve(initialSolution,weight,dist);
    }

    private int[] resolve(int[] initialSolution,int[][] weight,int[][] dist){
        Random rdm=new Random();
        StringBuilder stringBuilderFitness=new StringBuilder();
        int[] bestSolution=initialSolution.clone();
        long bestFitness=ConfigurationUtil.getFitness(bestSolution,weight,dist);
        final Queue<int[]> tabous =new LinkedList<>();

        int[] solution=initialSolution.clone();
        for(int i=0;i<NB_STEPS;++i){
            List<int[]> neighbors = landscape.getNeighbors(solution);
            neighbors.removeIf(tabous::contains);
            int [] bestNeighbor= getBestNeighbor(neighbors,weight,dist);
            long fitnessNeighbor=ConfigurationUtil.getFitness(bestNeighbor,weight,dist);
            long fitnessDifference=fitnessNeighbor-ConfigurationUtil.getFitness(solution,weight,dist);
            if(fitnessDifference>=0){
                tabous.add(solution.clone());
                if(tabous.size()>SIZE_TABOUS){
                    tabous.remove();
                }
            }
            if(fitnessNeighbor<bestFitness){
                bestFitness=fitnessNeighbor;
                bestSolution=bestNeighbor.clone();
            }

            solution=bestNeighbor.clone();
            stringBuilderFitness.append(fitnessNeighbor).append(" \r\n");
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(stringBuilderFitness.toString());
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        return bestSolution;
    }

    private int[] getBestNeighbor(List<int[]> neighbors,int[][] weight,int[][] dist) {
        int[] bestNeighbor=neighbors.get(0);
        long bestFitness=ConfigurationUtil.getFitness(bestNeighbor,weight,dist);
        for(int i=1;i<neighbors.size();++i){
            int[] configuration=neighbors.get(i);
            long fitness=ConfigurationUtil.getFitness(configuration,weight,dist);
            if(fitness<bestFitness){
                bestFitness=fitness;
                bestNeighbor=configuration.clone();
            }
        }
        return bestNeighbor;
    }
}
