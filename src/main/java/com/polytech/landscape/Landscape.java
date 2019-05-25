package com.polytech.landscape;

import java.util.List;

public interface Landscape<C,K> {
    /**
     * return all the neighbors of given configuration
     */
    List<C> getNeighbors(C configuration);

    /**
     * return all the possible elementary operations
     */
    List<K> getElementaryOperations(int n);
}
