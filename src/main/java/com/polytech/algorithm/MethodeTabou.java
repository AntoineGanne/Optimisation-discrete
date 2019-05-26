package com.polytech.algorithm;

import com.polytech.landscape.BasicPermutation;
import com.polytech.landscape.ElementaryOperation;
import com.polytech.landscape.Landscape;
import com.polytech.landscape.Permutation;
import com.polytech.logger.FileLogger;
import com.polytech.model.ProblemModel;
import com.polytech.util.ConfigurationUtil;
import com.sun.istack.internal.NotNull;

import javax.xml.bind.Element;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MethodeTabou implements GenericAlgorithm<int[],ProblemModel> {
    private static final int SIZE_TABOUS=3;
    private static final int NB_STEPS=50;
    private Landscape<int[],Permutation> landscape=new BasicPermutation();
//    private static String filePath="./resultats/fitness.txt";
//    private BufferedWriter bufferedWriter;
    private FileLogger fitnessLogger=new FileLogger("./resultats/fitness.txt");

    public MethodeTabou(@NotNull String landscapeName ) throws Exception {
        assert (landscapeName!=null && !landscapeName.isEmpty());
        switch (landscapeName){
            case BasicPermutation.NAME:
                landscape=new BasicPermutation();
                break;
            default:
                throw new Exception("landscape unknown");
        }
    }


    @Override
    public int[] resolve(ProblemModel model) {
        int[][] dist = model.getDist();
        int[][] weight = model.getWeight();
        int[] initialSolution=new int[model.getN()];
        for(int i=0;i<initialSolution.length;++i){
            initialSolution[i]=i+1;
        }
        return resolve(initialSolution,weight,dist);
    }

    private int[] resolve(int[] initialSolution,int[][] weight,int[][] dist){
        //initialisation
        Random rdm=new Random();
        final int n=initialSolution.length;
        List<Permutation> allOperations = landscape.getElementaryOperations(n);
        int[] bestSolution=initialSolution.clone();
        long bestFitness=ConfigurationUtil.getFitness(bestSolution,weight,dist);
        Queue<ElementaryOperation<int[]>> tabous =new LinkedList<>();
        int[] solution=initialSolution.clone();

        //iteration
        for(int i=0;i<NB_STEPS;++i){
            //select of a random neighbor;
            ArrayList<ElementaryOperation> unauthorizedOperations=new ArrayList<>(tabous);

            ElementaryOperation<int[]> bestNeighborOperation= getBestOperation(solution,unauthorizedOperations,weight,dist);
            int[] bestNeighbor=bestNeighborOperation.applyOperation(solution);
            long fitnessNeighbor=ConfigurationUtil.getFitness(bestNeighbor,weight,dist);
            long fitnessDifference=fitnessNeighbor-ConfigurationUtil.getFitness(solution,weight,dist);
            if(fitnessDifference>=0){
                tabous.add(bestNeighborOperation);
                if(tabous.size()>SIZE_TABOUS){
                    tabous.remove();
                }
            }
            if(fitnessNeighbor<bestFitness){
                bestFitness=fitnessNeighbor;
                bestSolution=bestNeighbor.clone();
            }

            solution=bestNeighbor.clone();
            fitnessLogger.write(fitnessNeighbor+"\r");
        }


        return bestSolution;
    }

    private ElementaryOperation<int[]> getBestOperation(@NotNull final int[] configuration, ArrayList<ElementaryOperation> tabouOperations, int[][] weight, int[][] dist) {
        List<Permutation> allOperations = landscape.getElementaryOperations(configuration.length);

        assert (allOperations.size()>=1);
        ElementaryOperation<int[]> bestOperation =allOperations.get(0);
        long bestFitness=Long.MAX_VALUE;

        for(ElementaryOperation<int[]> operation:allOperations){
            if(!ConfigurationUtil.listContainsOperation(tabouOperations,operation)){
                int[] neighbor=operation.applyOperation(configuration);
                long fitness=ConfigurationUtil.getFitness(neighbor,weight,dist);
                if(fitness<bestFitness){
                    bestFitness=fitness;
                    bestOperation=operation;
                }
            }
        }
        return bestOperation;
    }
}
