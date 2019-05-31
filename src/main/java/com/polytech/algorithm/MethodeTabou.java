package com.polytech.algorithm;

import com.polytech.landscape.BasicPermutation;
import com.polytech.landscape.ElementaryOperation;
import com.polytech.landscape.Landscape;
import com.polytech.landscape.Permutation;
import com.polytech.logger.FileLogger;
import com.polytech.logger.FitnessLogger;
import com.polytech.model.ProblemModel;
import com.polytech.util.ConfigurationUtil;
import com.sun.istack.internal.NotNull;

import java.util.*;

public class MethodeTabou implements GenericAlgorithm<int[],ProblemModel> {
    private int sizeTabou =1;
    private int nbSteps =100;
    private Landscape landscape=new BasicPermutation();
    private FitnessLogger fitnessLogger=new FitnessLogger("./resultats/fitnessTabou.txt");

    public MethodeTabou(@NotNull String landscapeName,int tabouLength, int nbStepsInput) throws Exception {
        assert (landscapeName!=null && !landscapeName.isEmpty());
        landscape=Landscape.getLandscape(landscapeName);
        sizeTabou=tabouLength;
        nbSteps=nbStepsInput;

    }


    @Override
    public int[] resolve(ProblemModel model) {


        int[][] dist = model.getDist();
        int[][] weight = model.getWeight();
        int[] initialSolution=new int[model.getN()];
        for(int i=0;i<initialSolution.length;++i){
            initialSolution[i]=i+1;
        }
        writeBaseInfosOnLoggers(model.getName());
        return resolve(initialSolution,weight,dist);
    }

    private int[] resolve(int[] initialSolution,int[][] weight,int[][] dist){
        //initialisation
        Random rdm=new Random();
        final int n=initialSolution.length;
        int[] bestSolution=initialSolution.clone();
        long bestFitness=ConfigurationUtil.getFitness(bestSolution,weight,dist);

        assert (sizeTabou>=0 && sizeTabou<=initialSolution.length);
        Queue<ElementaryOperation<int[]>> tabous =new LinkedList<>();
        int[] solution=initialSolution.clone();


        //iteration
        for(int k = 0; k< nbSteps; ++k){
            //select of a random neighbor;
            ArrayList<ElementaryOperation> unauthorizedOperations=new ArrayList<>(tabous);

            ElementaryOperation<int[]> bestNeighborOperation= getBestOperation(solution,unauthorizedOperations,weight,dist);
            int[] bestNeighbor=bestNeighborOperation.applyOperation(solution);
            long fitnessNeighbor=ConfigurationUtil.getFitness(bestNeighbor,weight,dist);
            long fitnessSolution=ConfigurationUtil.getFitness(solution,weight,dist);
            long fitnessDifference=fitnessNeighbor-fitnessSolution;
            if(fitnessDifference>=0){
                tabous.add(bestNeighborOperation);
                if(tabous.size()> sizeTabou){
                    tabous.remove();
                }
            }
            if(fitnessNeighbor<bestFitness){
                bestFitness=fitnessNeighbor;
                bestSolution=bestNeighbor.clone();
            }
            fitnessLogger.writeLineFitnessTabou(k,solution,fitnessSolution,unauthorizedOperations);

            solution=bestNeighbor.clone();
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

    private void writeBaseInfosOnLoggers(String nameProblemModel){
        StringBuilder introductionBuilder = new StringBuilder();
        introductionBuilder.append("Algorithme de Recuit Simulé sur \t"+nameProblemModel+" \n");
        introductionBuilder.append("landscape:"+landscape.getClass()+"\n");
        introductionBuilder.append("taille tabou="+sizeTabou+"\t nbMaxSteps="+nbSteps+"\n");
        fitnessLogger.writeLine(introductionBuilder.toString());
        fitnessLogger.writeEnteteTabou();
    }
}
