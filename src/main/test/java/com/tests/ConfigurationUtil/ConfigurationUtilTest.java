package com.tests.ConfigurationUtil;

import com.polytech.model.ProblemModel;
import com.polytech.util.ConfigurationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ConcurrentModificationException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationUtilTest {
    ProblemModel pm;
    int[] configuration;

    @BeforeEach
    void setUp() {
        try {
            pm=new ProblemModel("./data/test.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        configuration=new int[pm.getN()];
        for(int i=0;i<configuration.length;++i){
            configuration[i]=i;
        }
    }

    @Test
    void getFitness() {
        double fitness = ConfigurationUtil.getFitness(configuration, pm.getWeight(), pm.getDist());
        assertEquals(fitness,14);

    }
}