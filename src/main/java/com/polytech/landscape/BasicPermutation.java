package com.polytech.landscape;

import com.polytech.algorithm.RandomWalk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BasicPermutation implements Landscape<int[], Permutation> {
    public  static final String NAME="BasicPermutation";
    @Override
    public List<int[]> getNeighbors(final int[] configuration) {
        ArrayList<int[]> neighbors=new ArrayList<>();
        for(int i=0;i<configuration.length;++i){
            for(int j=i+1;j<configuration.length;++j){
                Permutation operation=new Permutation(i,j);
                neighbors.add(operation.applyOperation(configuration));
            }
        }
        return neighbors;
    }

    @Override
    public int[] getRandomNeighbor(int[] configuration) {
        int n=configuration.length;
        ElementaryOperation randomOperation =new Permutation(n);
        return ((Permutation) randomOperation).applyOperation(configuration);
    }

    @Override
    public List<Permutation> getElementaryOperations(int n) {
        ArrayList<Permutation> permutations=new ArrayList<>();
        for(int i=0;i<n;++i){
            for(int j=i+1;j<n;++j){
                permutations.add(new Permutation(i,j));
            }
        }
        return permutations;
    }
}
