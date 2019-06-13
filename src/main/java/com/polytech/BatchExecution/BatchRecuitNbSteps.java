package com.polytech.BatchExecution;

import com.polytech.algorithm.GenericAlgorithm;
import com.polytech.algorithm.MethodeTabou;
import com.polytech.algorithm.RandomWalk;
import com.polytech.algorithm.RecuitSimule;
import com.polytech.landscape.BasicPermutation;
import com.polytech.logger.BatchLogger;
import com.polytech.model.ProblemModel;

import java.util.ArrayList;

public class BatchRecuitNbSteps {
    public static void main(String[] args){
        BatchRecuitNbSteps batchRecuitNbSteps =new BatchRecuitNbSteps("./resultats/bestRecuitNbSteps.txt");
        try {
            batchRecuitNbSteps.execute("pas maximum et moyen de découverte de la meilleure solution selon nbSteps");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    BatchLogger batchLogger;
    static final String[] instances = {"12","15","17","20","25","30","35","40","50","60","80","100"};

    // nombre de test pris pour faire une moyenne
    private final int NB_MEAN=2;
    private final double mu=0.9;
    private final double p=0.5;

    public BatchRecuitNbSteps(String filePath) {
        batchLogger =new BatchLogger(filePath);
    }

    public void execute (String testName) throws Exception {
        batchLogger.writeLine(testName);
        batchLogger.writeLine(new String[]{"n","pas moyen","pas maximum"});

        ArrayList<String> line;
        GenericAlgorithm<int[],ProblemModel>  algorithm;
        long pasMax = 0;
        double pasMoyen = Double.MAX_VALUE;
        for(String n:instances){

            ProblemModel model=new ProblemModel("./data/tai"+n+".txt");
            for(int k=0;k<NB_MEAN;++k){
                algorithm=new RecuitSimule(BasicPermutation.NAME,50000,mu,p);
                algorithm.resolve(model);
                long pas=algorithm.getStepOfBestSolution();
                if(k==0){
                    pasMoyen=pas;
                    pasMax=pas;
                }
                else{
                    double currentMean=pasMoyen;
                    pasMoyen=(currentMean*(k-1)+pas)/k;
                }
                if(pas>pasMax){
                    pasMax=pas;
                }


            }
            line=new ArrayList<>();
            line.add(n);
            line.add(String.valueOf((long)pasMoyen));
            line.add(String.valueOf(pasMax));
            batchLogger.writeLine(line);

        }
    }


}
