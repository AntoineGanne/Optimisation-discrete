package com.polytech.landscape;

import java.util.List;

public interface Landscape<C,K> {
    List<C> getNeighbors(C configuration);

    List<K> getElementaryOperations(int n);
}
