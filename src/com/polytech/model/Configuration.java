package com.polytech.model;

import java.util.Arrays;

public class Configuration {
    public int getN() {
        return n;
    }

    int n;

    public int[] getConfiguration() {
        return configuration;
    }

    int[] configuration;

    public Configuration(int[] configuration) {
        this.n = configuration.length;
        this.configuration = configuration;
    }

    /**
     * default configuration: equipment i on emplacement i
     * @param n dimension
     */
    public Configuration(int n) {
        this.configuration=new int[n];
        for(int i=0;i<n;++i){
            configuration[i]= i;
        }
    }

    public int getEmplacementOfEquipment(int equipment){
        assert (equipment>=0 && equipment<n);
        return configuration[equipment];
    }

    int getFitness(int[][] weight,int[][] dist){
        assert (this.n==weight.length && this.n==dist.length);
        int result=0;
        for(int i=0;i<n;++i){
            for(int j=i+1;j<n;++j){
                int distance=dist[this.getEmplacementOfEquipment(i)][this.getEmplacementOfEquipment(j)];
                result+=weight[i][j]*distance;
            }
        }
        return result;
    }


    @Override
    public String toString() {
        return "Configuration{" +
                "n=" + n +
                ", configuration=" + Arrays.toString(configuration) +
                '}';
    }
}
