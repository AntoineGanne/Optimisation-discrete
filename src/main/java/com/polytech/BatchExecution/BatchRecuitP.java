package com.polytech.BatchExecution;

import com.polytech.algorithm.GenericAlgorithm;
import com.polytech.algorithm.RecuitSimule;
import com.polytech.landscape.BasicPermutation;
import com.polytech.logger.BatchLogger;
import com.polytech.model.ProblemModel;

import java.util.ArrayList;

public class BatchRecuitP {
    public static void main(String[] args){
        BatchRecuitP batchRecuitP =new BatchRecuitP("./resultats/BestRecuitP.txt");
        try {
            batchRecuitP.execute("fitness et pas de solution du recuit simulé selon P");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    BatchLogger batchLogger;

    // nombre de test pris pour faire une moyenne
    private final int NB_MEAN=5;
    private final int n=40;
    private final double[] paramValues=new double[]{0.0,0.25,0.5,0.75,1.0};
    public BatchRecuitP(String filePath) {
        batchLogger =new BatchLogger(filePath);
    }

    public void execute (String testName) throws Exception {

        batchLogger.writeLine(testName);
        batchLogger.writeLine("test pour n=" + n);
        batchLogger.writeLine(new String[]{"p", "fitness", "pas"});


        ArrayList<String> line;
        GenericAlgorithm<int[], ProblemModel> algorithm;
        long fitnessMoyenne = Long.MAX_VALUE;
        float pasMoyen = Float.MAX_VALUE;

        for (double value : paramValues) {
            System.out.println("\n\nvalue:" + value + "\n\n");

            ProblemModel model = new ProblemModel("./data/tai" + n + ".txt");
            line = new ArrayList<>();
            line.add(String.valueOf(value));

            for (int k = 0; k < NB_MEAN; ++k) {
                algorithm = new RecuitSimule(BasicPermutation.NAME, 50000, 0.9, value);
                algorithm.resolve(model);
                long fitness = algorithm.getBestFitness();
                int pas = algorithm.getStepOfBestSolution();

                if (k == 0) {
                    fitnessMoyenne = fitness;
                    pasMoyen = pas;
                } else {
                    long currentMean = fitnessMoyenne;
                    fitnessMoyenne = (currentMean * (k - 1) + fitness) / k;
                    float currentMeanPas = pasMoyen;
                    pasMoyen = (currentMeanPas * (k - 1) + pas) / k;

                }
            }
            line.add(String.valueOf(fitnessMoyenne));
            line.add(String.valueOf((long)pasMoyen));
            batchLogger.writeLine(line);
        }
    }

}
