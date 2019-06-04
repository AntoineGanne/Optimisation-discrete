package com.polytech.algorithm;

public interface GenericAlgorithm<C,M> {


    C resolve(M model);

    public int[] getBestSolution();

    public long getBestFitness();

    public int getStepOfBestSolution();
}
