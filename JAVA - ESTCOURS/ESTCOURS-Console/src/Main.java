import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("|---------------------------------------------------------------|");
        System.out.println("|\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|");
        System.out.println("|\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|");
        System.out.println("|\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|");
        System.out.println("|\t\t\t\t\t\t\tEst Cours\t\t\t\t\t\t\t|");
        System.out.println("|\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|");
        System.out.println("|\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|");
        System.out.println("|\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|");
        System.out.println("|---------------------------------------------------------------|");

        System.out.println("Bienvenue au programme EST Cours.");


        //Role : etudiant ou professeur
        System.out.println("---------------Sélectionner votre rôle---------------:");

        System.out.println("\t 1-Admin.");
        System.out.println("\t 2-Professeur.");
        System.out.println("\t 3-Étudiant.");
        int choixRole;
        do {
            System.out.print("Entrez votre rôle :");
            choixRole = scanner.nextInt();
        } while (choixRole != 1 && choixRole != 2 && choixRole != 3);
        String choixSortir;

        switch (choixRole) {

            case 1:
                Admin admin = new Admin();
                if (!admin.adminLogin()) {
                    System.out.println("Vous ne pouvez pas entrer!");
                } else {

                    do {
                        System.out.println("");
                        System.out.println("");
                        System.out.println("---------------------Que souhaitez-vous faire------------------");
                        System.out.println("1-Afficher les étudiants.");
                        System.out.println("2-Ajouter un étudiant.");
                        System.out.println("3-Supprimer un étudiant.");
                        System.out.println("4-Afficher les professeurs.");
                        System.out.println("5-Ajouter un professeur.");
                        System.out.println("6-Supprimer un professeur.");

                        //Choisir la processus a faire
                        int choixMission;
                        // tapez 1 ou 2 ou 3
                        do {
                            System.out.print("Entrez votre choix :");
                            choixMission = scanner.nextInt();
                        } while (choixRole != 1 && choixRole != 2 && choixMission != 3 && choixRole != 4 && choixRole != 5 && choixMission != 6);

                        //switch pour les processus
                        switch (choixMission) {
                            case 1:
                                System.out.println("");
                                System.out.println("");
                                admin.afficherEtudiant();
                                break;
                            case 2:
                                System.out.println("");
                                System.out.println("");
                                admin.ajouterEtudiant();
                                break;
                            case 3:
                                System.out.println("");
                                System.out.println("");
                                admin.afficherEtudiant();
                                admin.supprimerEtudiant();
                                break;
                            case 4:
                                System.out.println("");
                                System.out.println("");
                                admin.afficherProf();
                                break;
                            case 5:
                                System.out.println("");
                                System.out.println("");
                                admin.ajouterProf();
                                break;
                            case 6:
                                System.out.println("");
                                System.out.println("");
                                admin.afficherProf();
                                admin.supprimerProf();
                                break;
                        }
                        System.out.println("Voulez vous continuer (y/n)");
                        choixSortir = scanner.next();

                    } while (!choixSortir.equals("n"));
                }


                break;
            case 2:
                Professeur professeur = new Professeur();
                System.out.println("");
                System.out.println("");

                if (!professeur.professeurLogin()) {
                    System.out.println("Vous ne pouvez pas entrer!");
                } else {

                    do {
                        System.out.println("");
                        System.out.println("");
                        System.out.println("---------------------Que souhaitez-vous faire------------------");
                        System.out.println("1-Ajouter un fichier.");
                        System.out.println("2-Supprimer un fichier.");
                        System.out.println("3-Afficher les fichiers.");

                        //Choisir la processus a faire
                        int choixMission;
                        // tapez 1 ou 2 ou 3
                        do {
                            System.out.print("Entrez votre choix :");
                            choixMission = scanner.nextInt();
                        } while (choixRole != 1 && choixRole != 2 && choixMission != 3);

                        //switch pour les processus
                        switch (choixMission) {
                            case 1:
                                System.out.println("");
                                System.out.println("");
                                professeur.ajouterCours();
                                break;
                            case 2:
                                System.out.println("");
                                System.out.println("");
                                professeur.afficherCours();
                                professeur.supprimerCours();
                                break;
                            case 3:
                                System.out.println("");
                                System.out.println("");
                                professeur.afficherCours();
                                professeur.selectCourse();
                                break;
                        }
                        System.out.println("Voulez vous continuer (y/n)");
                        choixSortir = scanner.next();

                    } while (!choixSortir.equals("n"));
                }

                break;


            case 3:
                Etudiant etudiant = new Etudiant();
                System.out.println("");
                System.out.println("");
                if (!etudiant.etudiantLogin()) {
                    System.out.println("Vous ne pouvez pas entrer!");
                } else {
                    do {
                        System.out.println("");
                        System.out.println("");
                        etudiant.afficherCours();
                        etudiant.selectCourse();
                        System.out.println("Voulez vous continuer (y/n)");
                        choixSortir = scanner.next();
                    } while (!choixSortir.equals("n"));
                }
                break;


        }


    }


}

