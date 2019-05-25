package com.polytech.landscape;

public class Permutation implements ElementaryOperation<int[]> {
    int a;
    int b;

    public Permutation(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int[] applyOperation(int[] configurationInput) {
        int[] configuration=configurationInput.clone();
        int buffer=configuration[a];
        configuration[a]=configuration[b];
        configuration[b]=buffer;
        return configuration;
    }

    @Override
    /**
     * return the inverse operation for the tabou method.
     * In this case, it's the same operation tbh
     */
    public ElementaryOperation getInverseOperation() {
        return new Permutation(b,a);
    }
}