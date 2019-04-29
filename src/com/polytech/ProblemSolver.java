package com.polytech;

import com.polytech.model.Configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class ProblemSolver {
    int n;
    int[][] weight;
    int[][] dist;


    public ProblemSolver(String filePath) throws FileNotFoundException {
        Scanner scannerDoc = new Scanner(new FileReader(filePath));
        n=scannerDoc.nextInt();

        weight=new int[n][n];
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                weight[i][j]=scannerDoc.nextInt();
            }
        }

        dist=new int[n][n];
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                dist[i][j]=scannerDoc.nextInt();
            }
        }
    }

    int getFitness(Configuration conf){
        assert (this.n==conf.getN());
        int result=0;
        for(int i=0;i<n;++i){
            for(int j=i+1;j<n;++j){
                int distance=dist[conf.getEmplacementOfEquipment(i)][conf.getEmplacementOfEquipment(j)];
                result+=weight[i][j]*distance;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("ProblemSolver{\n n=");
        sb.append(n);
        sb.append("\n \n");
        sb.append("weight:\n");
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                int w=weight[i][j];
                sb.append(w);
                sb.append(w>=10? " ": "  ");
            }
            sb.append("\n");
        }

        sb.append("\n");
        sb.append("dist:\n");
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                int d=dist[i][j];
                sb.append(d);
                sb.append(d>=10? " ": "  ");
            }
            sb.append("\n");
        }

        sb.append("}");
        return sb.toString();
    }
}
