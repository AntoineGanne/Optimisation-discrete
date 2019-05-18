package com.polytech.landscape;

import java.util.ArrayList;
import java.util.List;

public class BasicPermutation implements Landscape<int[]> {
    public List<int[]> getNeighbors(final int[] configuration) {
        ArrayList<int[]> neighbors=new ArrayList<>();
        for(int i=0;i<configuration.length;++i){
            for(int j=i+1;j<configuration.length;++j){
                int[] neighbor=configuration.clone();
                int buffer=configuration[i];
                neighbor[i]=neighbor[j];
                neighbor[j]=buffer;
                neighbors.add(neighbor.clone());
            }
        }
        return neighbors;
    }
}
