package com.polytech.util;

public class ConfigurationUtil {
    /**
     * calculate fitness of the given configuration with the given weights and distances
     * @param configuration the number must be between 1 and n (configutation.length)
     * @param weight assumed symetrical
     * @param dist   assumed symetrical
     * @return
     */
    public static long getFitness(int [] configuration,int[][] weight,int[][] dist){
        int n=configuration.length;
        assert (n==weight.length && n==dist.length);
        int result=0;
        for(int i=0;i<n;++i){
            for(int j=i+1;j<n;++j){
                int distance=dist[configuration[i]-1][configuration[j]-1];
                result+=weight[i][j]*distance;
            }
        }
        return result*2;
    }

    public static String ConfigToString(int[] configuration){
        StringBuilder sb=new StringBuilder();
        sb.append("Config{ ");
        for(int i=0;i<configuration.length;++i){
            sb.append(configuration[i]);
            if(i<configuration.length-1){
                sb.append(", ");
            }
        }
        sb.append(" )");
        return sb.toString();
    }
}
