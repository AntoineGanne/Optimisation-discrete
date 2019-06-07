package com.polytech.BatchExecution;

import com.polytech.algorithm.GenericAlgorithm;
import com.polytech.algorithm.MethodeTabou;
import com.polytech.algorithm.RandomWalk;
import com.polytech.algorithm.RecuitSimule;
import com.polytech.landscape.BasicPermutation;
import com.polytech.logger.BatchLogger;
import com.polytech.model.ProblemModel;

import java.util.ArrayList;

public class BatchExecutionTime {
    public static void main(String[] args){
        BatchExecutionTime batchExecutionTime =new BatchExecutionTime("./resultats/executionTimes.txt");
        try {
            batchExecutionTime.execute("Temps d'execution |ms] selon les méthodes et selon la dimension n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    BatchLogger batchLogger;
    static final String[] instances = {"12","15","17","20","25","30","35","40","50","60","80","100"};

    // nombre de test pris pour faire une moyenne
    private final int NB_MEAN=3;

    public BatchExecutionTime(String filePath) {
        batchLogger =new BatchLogger(filePath);
    }

    public void execute (String testName) throws Exception {
        batchLogger.writeLine(testName);
        batchLogger.writeLine(new String[]{"n \\ méthode+","marche aléatoire","Recuit simulé","Méthode Tabou"});

        ArrayList<String> line;
        GenericAlgorithm<int[],ProblemModel>  algorithm;
        long[] values;
        long startTime,endTime,duration;
        for(String n:instances){

            ProblemModel model=new ProblemModel("./data/tai"+n+".txt");
            values=new long[NB_MEAN];
            for(int k=0;k<NB_MEAN;++k){
                algorithm=new RandomWalk(BasicPermutation.NAME,1000);
                startTime=System.currentTimeMillis();
                algorithm.resolve(model);
                endTime=System.currentTimeMillis();
                duration=endTime-startTime;
                if(k==0){ values[0]=duration; }
                else{
                    long currentMean=values[0];
                    values[0]=(currentMean*(k-1)+duration)/k;
                }

                algorithm=new RecuitSimule(BasicPermutation.NAME,1000,0.99,0.9);
                startTime=System.currentTimeMillis();
                algorithm.resolve(model);
                endTime=System.currentTimeMillis();
                duration=endTime-startTime;
                if(k==0){ values[0]=duration; }
                else{
                    long currentMean=values[1];
                    values[1]=(currentMean*(k-1)+duration)/k;
                }

                algorithm=new MethodeTabou(BasicPermutation.NAME, Integer.valueOf(n)/2,1000);
                startTime=System.currentTimeMillis();
                algorithm.resolve(model);
                endTime=System.currentTimeMillis();
                 duration=endTime-startTime;
                if(k==0){ values[2]=duration; }
                else{
                    long currentMean=values[2];
                    values[2]=(currentMean*(k-1)+duration)/k;
                }
            }
            line=new ArrayList<>();
            line.add(n);
            for(long value:values){
                line.add(String.valueOf(value));
            }
            batchLogger.writeLine(line);

        }
    }


}
