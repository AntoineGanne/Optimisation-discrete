package com.polytech.landscape;

import java.util.List;

public interface Landscape<C,K> {
    /**
     * return all neighbors of given configuration
     */
    List<C> getNeighbors(C configuration);

    C getRandomNeighbor(C configuration);

    /**
     * return all the possible elementary operations
     */
    List<K> getElementaryOperations(int n);

    static Landscape getLandscape(String landscapeName) throws Exception {
        Landscape l;
        switch (landscapeName){
            case BasicPermutation.NAME:
                l=new BasicPermutation();
                break;
            default:
                throw new Exception("landscape unknown");
        }
        return l;
    }
}
