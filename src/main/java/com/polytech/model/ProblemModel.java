package com.polytech.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ProblemModel {
    int n;

    int[][] weight;
    int[][] dist;


    public ProblemModel(String filePath) throws FileNotFoundException {
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

    int getFitness(int[] conf){
        assert (this.n==conf.length);
        int result=0;
        for(int i=0;i<n;++i){
            for(int j=i+1;j<n;++j){
                int distance=dist[conf[i]][conf[j]];
                result+=weight[i][j]*distance;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("ProblemModel{\n n=");
        sb.append(n);
        sb.append("\n \n");
        sb.append("weight:\n");
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                int w=weight[i][j];
                sb.append(w>=10? " ": " 0");
                sb.append(w);
            }
            sb.append("\n");
        }

        sb.append("\n");
        sb.append("dist:\n");
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                int d=dist[i][j];
                sb.append(d>=10? " ": " 0");
                sb.append(d);
            }
            sb.append("\n");
        }

        sb.append("}");
        return sb.toString();
    }
    public int getN() {
        return n;
    }


    public int[][] getWeight() {
        return weight;
    }

    public int[][] getDist() {
        return dist;
    }
}