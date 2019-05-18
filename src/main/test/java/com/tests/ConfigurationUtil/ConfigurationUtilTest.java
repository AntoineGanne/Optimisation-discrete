package com.tests.ConfigurationUtil;

import com.polytech.model.ProblemModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationUtilTest {
    ProblemModel pm;
    int[] initialSolution=new int[model.getN()];
        for(int i=0;i<initialSolution.length;++i){
        initialSolution[i]=i;
    }

    @BeforeEach
    void setUp() {
        try {
            pm=new ProblemModel("./data/test.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getFitness() {
    }
}