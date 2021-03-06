package com.polytech.landscape;

import java.util.Random;

/**
 * Permutation of two elements at specified positions
 */
public class Permutation implements ElementaryOperation<int[]> {
    private int a;
    private int b;

    public Permutation(int lowestIndex, int highestIndex) {
        assert (lowestIndex>=0);
        assert (lowestIndex<highestIndex);
        this.a = lowestIndex;
        this.b = highestIndex;
    }

    /**
     * constructor of random Permutation
     * @param n
     */
    public Permutation (int n) {
        Random rdm=new Random();
        int randomA = rdm.nextInt(n-1);
        int randomB = rdm.nextInt(n-randomA-1)+1;

        this.a=randomA;
        this.b=randomA+randomB;
    }

    @Override
    public int[] applyOperation(int[] configurationInput) {
        assert (a<configurationInput.length);
        assert (b<configurationInput.length);
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
        return new Permutation(a,b);
    }

    @Override
    public Boolean equals(ElementaryOperation comparedPermutation) {
        if(!comparedPermutation.getClass().equals(Permutation.class)){
            return false;
        }else{
            Permutation permutation=(Permutation)comparedPermutation;
            return a==permutation.getA()&&b==permutation.getB();
        }
    }



    @Override
    public int hashCode() {
        int result=17;
        int prime=13;
        result=prime*result+a;
        result=prime*result+b;
        return result;
    }

    @Override
    public String toString(){
        return " ("+a+"-"+b+") ";
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }
}
