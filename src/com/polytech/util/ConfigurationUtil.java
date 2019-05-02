package com.polytech.util;

public class ConfigurationUtil {
    public static double getFitness(int [] configuration,int[][] weight,int[][] dist){
        int n=configuration.length;
        assert (n==weight.length && n==dist.length);
        int result=0;
        for(int i=0;i<n;++i){
            for(int j=i+1;j<n;++j){
                int distance=dist[configuration[i]][configuration[j]];
                result+=weight[i][j]*distance;
            }
        }
        return result;
    }
}
