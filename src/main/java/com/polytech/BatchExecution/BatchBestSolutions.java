package com.polytech.BatchExecution;

import com.polytech.algorithm.GenericAlgorithm;
import com.polytech.algorithm.MethodeTabou;
import com.polytech.algorithm.RandomWalk;
import com.polytech.algorithm.RecuitSimule;
import com.polytech.landscape.BasicPermutation;
import com.polytech.logger.BatchLogger;
import com.polytech.model.ProblemModel;
import com.polytech.util.ConfigurationUtil;

import java.util.ArrayList;

public class BatchBestSolutions {
    public static void main(String[] args){
        BatchBestSolutions batchExecutionTime =new BatchBestSolutions("./resultats/BestSolutions.txt");
        try {
            batchExecutionTime.execute("meilleures solutions trouvées par les differents algorithmes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    BatchLogger batchLogger;
    static final String[] instances = {"12","15","17","20","25","30","35","40","50","60","80","100"};

    // nombre de test pris pour faire une moyenne
    private final int NB_MEAN=5;

    public BatchBestSolutions(String filePath) {
        batchLogger =new BatchLogger(filePath);
    }

    public void execute (String testName) throws Exception {
        batchLogger.writeLine(testName);
        batchLogger.writeLine(new String[]{"n \\ méthode "," ","marche aléatoire"," "," "," ","Recuit simulé"," "," "," ","Méthode Tabou"," "," "});
        batchLogger.writeLine(new String[]{" ","meilleure fitness","meilleure solution","itération","meilleure fitness moyenne","meilleure fitness","meilleure solution","itération","meilleure fitness moyenne","meilleure fitness","meilleure solution","itération","meilleure fitness moyenne"});


        ArrayList<String> line;
        GenericAlgorithm<int[],ProblemModel>  algorithm;
        long fitnessMoyenne=Long.MAX_VALUE;
        long bestFitness=Long.MAX_VALUE;
        int[] bestSolution=new int[]{};
        int pasBestSolution=0;

        long startTime,endTime,duration;
        for(String n:instances){
            System.out.println("\n\nN:"+n+"\n\n");

            ProblemModel model=new ProblemModel("./data/tai"+n+".txt");
            line=new ArrayList<>();
            line.add(n);

            // random walk
            for(int k=0;k<NB_MEAN;++k) {
                algorithm = new RandomWalk(BasicPermutation.NAME, 1000);
                int[] solution = algorithm.resolve(model);
                long fitness = algorithm.getBestFitness();
                int pas = algorithm.getStepOfBestSolution();

                if (k == 0) {
                    fitnessMoyenne = fitness;
                    bestFitness = fitness;
                    bestSolution = solution;
                    pasBestSolution = pas;
                } else {
                    long currentMean = fitnessMoyenne;
                    fitnessMoyenne = (currentMean * (k - 1) + fitness) / k;
                }

                if (fitness <= bestFitness) {
                    bestFitness = fitness;
                    bestSolution = solution;
                    pasBestSolution = pas;
                }
            }
            line.add(String.valueOf(bestFitness));
            line.add(ConfigurationUtil.ConfigToString(bestSolution));
            line.add(String.valueOf(pasBestSolution));
            line.add(String.valueOf(fitnessMoyenne));

            // recuit simulé
            for(int k=0;k<NB_MEAN;++k) {
                algorithm = new RecuitSimule(BasicPermutation.NAME, 1000, 0.99, 0.9);
                int[] solution = algorithm.resolve(model);
                long fitness = algorithm.getBestFitness();
                int pas = algorithm.getStepOfBestSolution();

                if (k == 0) {
                    fitnessMoyenne = fitness;
                    bestFitness = fitness;
                    bestSolution = solution;
                    pasBestSolution = pas;
                } else {
                    long currentMean = fitnessMoyenne;
                    fitnessMoyenne = (currentMean * (k - 1) + fitness) / k;
                }

                if (fitness <= bestFitness) {
                    bestFitness = fitness;
                    bestSolution = solution;
                    pasBestSolution = pas;
                }
            }
            line.add(String.valueOf(bestFitness));
            line.add(ConfigurationUtil.ConfigToString(bestSolution));
            line.add(String.valueOf(pasBestSolution));
            line.add(String.valueOf(fitnessMoyenne));

            // méthode tabou
            for(int k=0;k<NB_MEAN;++k) {
                algorithm = new MethodeTabou(BasicPermutation.NAME, Integer.valueOf(n) / 2, 1000);
                int[] solution = algorithm.resolve(model);
                long fitness = algorithm.getBestFitness();
                int pas = algorithm.getStepOfBestSolution();

                if (k == 0) {
                    fitnessMoyenne = fitness;
                    bestFitness = fitness;
                    bestSolution = solution;
                    pasBestSolution = pas;
                } else {
                    long currentMean = fitnessMoyenne;
                    fitnessMoyenne = (currentMean * (k - 1) + fitness) / k;
                }

                if (fitness <= bestFitness) {
                    bestFitness = fitness;
                    bestSolution = solution;
                    pasBestSolution = pas;
                }
            }
            line.add(String.valueOf(bestFitness));
            line.add(ConfigurationUtil.ConfigToString(bestSolution));
            line.add(String.valueOf(pasBestSolution));
            line.add(String.valueOf(fitnessMoyenne));


            batchLogger.writeLine(line);

        }
    }

}
