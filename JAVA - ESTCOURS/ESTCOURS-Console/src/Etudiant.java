import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Etudiant {

    Scanner scanner = new Scanner(System.in);

    public void openUrl(String url) throws IOException {
        String url_open =url;
        java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
    }

    public boolean etudiantLogin() {
        try {
            //Create connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estcours?serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();


            System.out.println("----------------------Entrez les information de connexion:----------------------");
            System.out.print("Entrez votre username: ");
            String usern = scanner.nextLine();
            System.out.print("Entrez votre mot de passe: ");
            String pass = scanner.nextLine();


            //Check if username and password exist
            String sql = "SELECT * from etudiant WHERE etudiantUsername='" + usern + "' and etudiantPassword='" + pass + "';";
            ResultSet rs = statement.executeQuery(sql);


            if (rs.next()) {
                String Nom = rs.getString("etudiantNom");
                String Prenom = rs.getString("etudiantPrenom");

                final String ANSI_RESET = "\u001B[0m";
                final String ANSI_YELLOW = "\u001B[33m";

                 System.out.println(ANSI_YELLOW
                         + "Bienvenue: " + Nom + " " + Prenom
                         + ANSI_RESET);
                return true;
            } else {
                System.out.println("Username/Mot de passe incorrect.");
                return false;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Le nom du cours
    public String nomCours(int choix) {
        if(choix==1) return "java";
        if(choix==2) return "PHP";
        if(choix==3) return "CSS/JS";
        if(choix==4) return "UML";
        if(choix==5) return "Réseau";
        if(choix==6) return "Oracle";
        else{
            return "null";
        }
    }
    //Le type du cours
    public String typeCours(int choix){
        if(choix==1) return "Cours";
        if(choix==2) return "TP";
        if(choix==3) return "TD";
        else{
            return "null";
        }
    }

    public void afficherCours(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estcours?serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();
            //Choisir la matière :
            System.out.println("----------------------Choisissez une matière ----------------------");
            System.out.println("1-java.");
            System.out.println("2-PHP.");
            System.out.println("3-CSS/Js.");
            System.out.println("4-UML.");
            System.out.println("5-Réseau.");
            System.out.println("6-Oracle.");
            int choixM;
            do {
                System.out.print("Entrez votre choix (1-6): ");
                choixM = scanner.nextInt();
            } while(choixM <1 || choixM>6);


            //Choisir le type
            System.out.println("----------------------Quel est le type du fichier ----------------------");
            System.out.println("1-Cours.");
            System.out.println("2-TP.");
            System.out.println("3-TD.");
            int choixT;
            do {
                System.out.print("Entrez votre choix (1-3): ");
                choixT = scanner.nextInt();
            } while (choixT < 1 || choixT > 3);

            ResultSet resultset = statement.executeQuery("Select * from cours where CoursNom='" + nomCours(choixM) + "' and CoursType='"+ typeCours(choixT)+"';");
            System.out.println("ID\t\t\t|Nom fichier");
            while(resultset.next()){
                System.out.println(resultset.getInt("idCours")+"\t\t\t|"+resultset.getString("coursDescription"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectCourse(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estcours?serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();
            int IdCours;
            System.out.println("Entrez l'Id du fichier que vous voulez consultez: ");
            IdCours= scanner.nextInt();

            ResultSet resultset = statement.executeQuery("Select CoursUrl from cours where idCours=" + IdCours + ";");
            while(resultset.next()){
                openUrl(resultset.getString("CoursUrl"));
            }


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }








}
