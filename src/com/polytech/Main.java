package com.polytech;

import com.polytech.algorithm.GenericAlgorithm;
import com.polytech.algorithm.RecuitSimule;
import com.polytech.landscape.BasicPermutation;
import com.polytech.model.ProblemModel;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            ProblemModel ps=new ProblemModel("./data/tai12.txt");
            System.out.println(ps.toString());

            BasicPermutation landscape=new BasicPermutation();
            int[] conf={0,1,2,3};
            List<int[]> neighbors = landscape.getNeighbors(conf);
            System.out.println("neighbors of {0,1,2,3}:\n");
            for (int[] n:
                 neighbors) {
                System.out.print(Arrays.toString(n)+" , ");
            }
            System.out.println("\n -------------");

            GenericAlgorithm<int[],ProblemModel> recuit=new RecuitSimule();
            int[] resolve = recuit.resolve(ps);

        } catch (FileNotFoundException e) {
            System.out.println("fichier non trouvé");
        }

    }
}
