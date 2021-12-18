import java.sql.*;

public class Admin extends Professeur {
    final String RESET = "\u001B[0m";
    final String GREEN = "\u001B[32m";
    final String RED = "\u001B[31m";
    final String YELLOW ="\u001B[33m";




    public boolean adminLogin() {
        try {  //Create connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estcours?serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();


            System.out.println("----------------------Entrez les information de connexion:----------------------");
            System.out.print("Entrez votre username: ");
            String usern = scanner.nextLine();
            System.out.print("Entrez votre mot de passe: ");
            String pass = scanner.nextLine();


            //Check if username and password exist
            String sql = "SELECT * from admin WHERE adminUsername='" + usern + "' and adminPassword='" + pass + "';";
            ResultSet rs = statement.executeQuery(sql);


            if (rs.next()) {
                String Nom = rs.getString("adminNom");
                String Prenom = rs.getString("adminPrenom");


                System.out.println(YELLOW + "Bienvenue: " + Nom + " " + Prenom + RESET);
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


    public void afficherEtudiant(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estcours?serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();


            ResultSet resultset = statement.executeQuery("Select * from etudiant;");
            System.out.println("ID\t\t\t|Nom\t\t\t|Prenom\t\t\t");
            while(resultset.next()){
                System.out.println(resultset.getInt("idEtudiant")+"\t\t\t|"+resultset.getString("etudiantNom")+"\t\t\t|"+resultset.getString("etudiantPrenom"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void ajouterEtudiant(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estcours?serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();

            //Le prenom d'etudiant
            System.out.print("Prenom d'étudiant: ");
            String prenom= scanner.nextLine();

            //Le prenom d'etudiant
            System.out.print("Nom d'étudiant: ");
            String nom= scanner.nextLine();

            //Le prenom d'etudiant
            System.out.print("Nom d'utilisateur d'étudiant: ");
            String username= scanner.nextLine();

            //Le prenom d'etudiant
            System.out.print("Mot de passe d'étudiant: ");
            String password= scanner.nextLine();

            statement.executeUpdate("insert into etudiant(etudiantNom,etudiantPrenom,etudiantUsername,etudiantPassword) values('" + nom +"','" + prenom + "','"+ username+"','"+ password+"');" );

            System.out.println(GREEN+"L'etudiant a été bien ajouté." + RESET);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void supprimerEtudiant(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estcours?serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();
            int IdStudent;
            String confirmDelete;
            System.out.println("Entrez l'Id d'étudiant que vous voulez supprimer: ");
            IdStudent= scanner.nextInt();

            System.out.print("êtes-vous sûr de vouloir supprimer l'étudiant avec l'identifiant "+ IdStudent +"(y/n): ");
            confirmDelete= scanner.next();

            if(confirmDelete.equals("n")){
                System.out.println(RED+"L'étudiant n'as pas été supprimé."+RESET);
            }else{
                int result = statement.executeUpdate("delete  from etudiant where idEtudiant=" + IdStudent + ";");
                System.out.println(GREEN+"Vous avez bien supprimé l'etudiant d'Id:" + IdStudent+RESET);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void afficherProf(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estcours?serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();


            ResultSet resultset = statement.executeQuery("Select * from professeur;");
            System.out.println("ID\t\t\t|Nom\t\t\t|Prenom\t\t\t");
            while(resultset.next()){
                System.out.println(resultset.getInt("idprof")+"\t\t\t|"+resultset.getString("profNom")+"\t\t\t|"+resultset.getString("profPrenom"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void ajouterProf(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estcours?serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();

            //Le prenom du prof
            System.out.print("Prenom du professeur: ");
            String prenom= scanner.nextLine();

            //Le prenom du prof
            System.out.print("Nom du professeur: ");
            String nom= scanner.nextLine();

            //Le prenom du prof
            System.out.print("Nom d'utilisateur du professeur: ");
            String username= scanner.nextLine();

            //Le prenom du prof
            System.out.print("Mot de passe du professeur: ");
            String password= scanner.nextLine();

            statement.executeUpdate("insert into professeur(profNom,profPrenom,profUsername,profPassword) values('" + nom +"','" + prenom + "','"+ username+"','"+ password+"');" );



            System.out.println(GREEN+"Le professeur  a été bien ajouté." + RESET);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void supprimerProf(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estcours?serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();
            String confirmDelete;
            System.out.println("Entrez l'Id du professeur que vous voulez supprimer: ");
            int IdProf= scanner.nextInt();

            System.out.print("êtes-vous sûr de vouloir supprimer le prof avec l'identifiant "+ IdProf +"(y/n): ");
            confirmDelete= scanner.next();
            if(confirmDelete.equals("n")){
                System.out.println(RED+"L'étudiant n'as pas été supprimé."+RESET);
            }else{
                int result = statement.executeUpdate("delete  from professeur where idEtudiant=" + IdProf + ";");

                System.out.println(GREEN+"Vous avez bien supprimé le professeur d'Id:" + IdProf + RESET);
            }




        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
