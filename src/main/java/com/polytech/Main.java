package com.polytech;

import com.polytech.algorithm.GenericAlgorithm;
import com.polytech.algorithm.MethodeTabou;
import com.polytech.algorithm.RandomWalk;
import com.polytech.algorithm.RecuitSimule;
import com.polytech.landscape.BasicPermutation;
import com.polytech.model.ProblemModel;
import com.polytech.util.ConfigurationUtil;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ProblemModel ps;
        try {
            ps=new ProblemModel("./data/test.txt");
            System.out.println(ps.toString());
        } catch (FileNotFoundException e) {
            System.out.println("fichier non trouvé");
            return;
        }

        BasicPermutation landscape=new BasicPermutation();
        int[] conf={1,2,3};
        List<int[]> neighbors = landscape.getNeighbors(conf);
        System.out.println("neighbors of {1,2,3}:\n");
        for (int[] n:
                neighbors) {
            System.out.print(Arrays.toString(n)+" , ");
        }
        System.out.println("\n -------------");

        GenericAlgorithm<int[],ProblemModel> algo= null;
        try {
//            algo = new RecuitSimule(BasicPermutation.NAME,30,0.8,0.7);
//            algo = new MethodeTabou(BasicPermutation.NAME,1,30);
            algo=new RandomWalk(BasicPermutation.NAME,30);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        int[] resolve = algo.resolve(ps);
        System.out.println("meilleure solution:");
        System.out.println(ConfigurationUtil.ConfigToString(resolve));
        System.out.println("fitness: "+ConfigurationUtil.getFitness(resolve,ps.getWeight(),ps.getDist()));




    }
}
