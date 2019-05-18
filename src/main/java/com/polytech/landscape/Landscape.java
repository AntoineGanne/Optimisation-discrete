package com.polytech.landscape;

import java.util.List;

public interface Landscape<C> {
    List<C> getNeighbors(C configuration);
}
