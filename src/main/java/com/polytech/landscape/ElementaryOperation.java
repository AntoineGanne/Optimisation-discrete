package com.polytech.landscape;

public interface ElementaryOperation <C>{
    public C applyOperation(C configuration);

    public ElementaryOperation getInverseOperation();

    public Boolean equals(ElementaryOperation comparedPermutation);


}
