import java.sql.*;

public class Professeur extends Etudiant {



    public boolean professeurLogin() {
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
            String sql = "SELECT * from professeur WHERE profUsername='" + usern + "' and profPassword='" + pass + "';";
            ResultSet rs = statement.executeQuery(sql);


            if (rs.next()) {
                String Nom = rs.getString("profNom");
                String Prenom = rs.getString("profPrenom");

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
            System.out.println("Erreur de connexion à la base de données!");
            System.out.println("Assurez-vous que MySql est lancé.");
            System.out.println("Assurez-vous que la base de données 'estcours' est créée.");
        }
        return false;
    }

    public void ajouterCours() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estcours?serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();

            //Choisir la matière :
            System.out.println("----------------------Ou voulez-vous inserer le cours ----------------------");
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
            System.out.println("----------------------Quel est le type du cours ----------------------");
            System.out.println("1-Cours.");
            System.out.println("2-TP.");
            System.out.println("3-TD.");
            int choixT;
            do {
                System.out.print("Entrez votre choix (1-3): ");
                choixT = scanner.nextInt();
            } while (choixT < 1 || choixT > 3);


            //le nom du fichier
            System.out.println("----------------------Les infos du fichier à ajouter----------------------");
            //Url du fichier
            System.out.println("l'URL du fichier: ");
            String url = scanner.nextLine();
            url = scanner.nextLine();

            System.out.println("Le nom du fichier: ");
            String desc = scanner.nextLine();





            statement.executeUpdate("insert into cours(CoursNom,coursDescription,CoursType,CoursUrl) values('" + nomCours(choixM)+"','" + desc + "','"+ typeCours(choixT)+"','"+ url+"');" );
            System.out.println("Le fichier est bien ajouté!");

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void supprimerCours(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estcours?serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();
            int IdCours;
            System.out.println("Entrez l'Id du fichier que vous voulez supprimer: ");
            IdCours= scanner.nextInt();

            int result = statement.executeUpdate("delete  from cours where idCours=" + IdCours + ";");

            System.out.println("Vous avez bien supprimé le fichier d'Id:" + IdCours);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
