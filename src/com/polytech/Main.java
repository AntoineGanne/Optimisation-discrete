package com.polytech;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        try {
            ProblemSolver ps=new ProblemSolver("./data/tai12a.dat");
            System.out.println(ps.toString());
        } catch (FileNotFoundException e) {
            System.out.println("fichier non trouvé");
        }

    }
}
