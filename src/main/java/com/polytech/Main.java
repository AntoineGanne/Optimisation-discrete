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
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ProblemModel ps= null;
        Scanner scConsole=new Scanner(System.in);
        System.out.println("Bonjour.");
        System.out.println("Veuillez choisir la taille de l'instance de Taillard {12,15,17,20,25,30,35,40,50,60,80,100}");
        boolean correctTaillard=true;

        do{
            try {
                String  nTailard = String.valueOf(scConsole.nextInt());
                ps=new ProblemModel("./data/tai"+nTailard+".txt");
//                System.out.println(ps.toString());
                correctTaillard=true;
            } catch (FileNotFoundException e) {
                System.out.println("fichier non trouvé! Veuillez rentrer une autre valeur");
                correctTaillard=false;
            }
        }while (!correctTaillard);

        BasicPermutation landscape=new BasicPermutation();
        GenericAlgorithm<int[],ProblemModel> algo= null;



        boolean okAlgo=true;
        do{
            System.out.println("\n \n Veuillez choisir l'algorithme a utiliser :");
            System.out.println("1: Marche aléatoire");
            System.out.println("2: Recuit simulé");
            System.out.println("3: Méthode Tabou");
            System.out.println("Concernant les réels, utilisz une virgule: 0,8 plutôt que 0.8");
            try{
                okAlgo=true;
                int choixAlgo= scConsole.nextInt();
                switch (choixAlgo){
                    case 1:
                        System.out.println("Veuillez entrer le nombre d'itérations nbSteps");
                        int nbSteps=scConsole.nextInt();
                        algo=new RandomWalk(BasicPermutation.NAME,nbSteps);
                        System.out.println("détail de l'execution sur ./resultats/fitnessRandom.txt");
                        break;
                    case 2:
                        algo=demandeParametrageRecuit(scConsole);
                        System.out.println("détail de l'execution sur ./resultats/fitnessRecuit.txt");
                        break;
                    case 3:
                        algo=demandeParametrageTabou(scConsole);
                        System.out.println("détail de l'execution sur ./resultats/fitnessTabou.txt");
                        break;
                    default:
                        System.out.println("Veuillez entrez 1,2 ou 3 svp");
                        okAlgo=false;
                }

            }catch (Exception e){
                System.out.println("Erreur lors de l'instanciation de l'algorithme, Veuillez réessayer.");
                okAlgo=false;
                scConsole.next();
            }
        }while (!okAlgo);
        System.out.println("\n");

        assert algo != null;
        int[] resolve = algo.resolve(ps);
      }

    public static GenericAlgorithm<int[],ProblemModel> demandeParametrageRecuit(Scanner sc) throws Exception {
        System.out.println("Veuillez entrer le paramètre mu");
        double mu=sc.nextDouble();
        System.out.println("Veuillez entrer le paramètre p");
        double p=sc.nextDouble();
        System.out.println("Veuillez entrer le nombre d'itérations nbSteps");
        int nbSteps=sc.nextInt();
        RecuitSimule recuitSimule = new RecuitSimule(BasicPermutation.NAME, nbSteps, mu, p);
        return recuitSimule;
    }

    public static GenericAlgorithm<int[],ProblemModel> demandeParametrageTabou(Scanner sc) throws Exception {
        System.out.println("Veuillez entrer le paramètre TabouLength");
        int tabouLength=sc.nextInt();
        System.out.println("Veuillez entrer le nombre d'itérations nbSteps");
        int nbSteps=sc.nextInt();
        MethodeTabou methodeTabou = new MethodeTabou(BasicPermutation.NAME,tabouLength, nbSteps);
        return methodeTabou;
    }
}
