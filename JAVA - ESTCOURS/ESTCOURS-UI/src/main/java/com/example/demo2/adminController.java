package com.example.demo2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class adminController extends HelloController implements Initializable {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/estcours";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addStats();
        fetRowList(profTable,"professeur",data);
        fetColumnList(profTable,"professeur",data);
        fetRowList(etudiantTable,"etudiant",etudiantData);
        fetColumnList(etudiantTable,"etudiant",etudiantData);

            }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void disconnect(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }



    @FXML
    private Label coursCount;

    @FXML
    private Label etudiantCount;

    @FXML
    private Label profCount;

    @FXML
    private Label userName;

    @FXML
    private TextField profNameField;

    @FXML
    private TextField profPasswordField;

    @FXML
    private TextField profPnameField;

    @FXML
    private TextField profUsernameField;
    @FXML
    private Label errorProf;
    @FXML
    private Label succesProf;

    //les champs d'étudiant

    @FXML
    private TableView etudiantTable;
    @FXML
    private Label succesEtudiant;

    @FXML
    private Label errorEtudiant;

    @FXML
    private TextField etudiantNameField;

    @FXML
    private TextField etudiantPasswordField1;

    @FXML
    private TextField etudiantPnameField;

    @FXML
    private TextField etudiantUsernameField;

    @FXML
    private TableView profTable;




    public void setUserName() {
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try {

            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            String SELECT_QUERY = "SELECT * FROM admin WHERE adminUsername = ? and adminPassword = ?";

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
            preparedStatement.setString(1, "admin");
            preparedStatement.setString(2, "admin");

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nom = resultSet.getString("adminNom");
                String prenom = resultSet.getString("adminPrenom");
                userName.setText(nom + " " + prenom + ".");
                System.out.println(nom + " " + prenom + ".");
            }
        } catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }

    }

    public void coursNumber() {
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            String SELECT_QUERY = "SELECT COUNT(idCours) FROM cours;";

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String cours = resultSet.getString("COUNT(idCours)");
                coursCount.setText(cours);
            }
        } catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }

    }

    public void profsNumber() {
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            String SELECT_QUERY = "SELECT COUNT(idProf) FROM professeur;";

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String prof = resultSet.getString("COUNT(idProf)");
                profCount.setText(prof);
            }
        } catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }

    }

    public void etudsNumber() {
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            String SELECT_QUERY = "SELECT COUNT(idEtudiant) FROM etudiant;";

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String etudiants = resultSet.getString("COUNT(idEtudiant)");
                etudiantCount.setText(etudiants);
                System.out.println(etudiants);
            }
        } catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }

    }

    public void addStats() {
        System.out.println(userName);

        setUserName();
        coursNumber();
        profsNumber();
        etudsNumber();
    }


    @FXML
    void addProf(ActionEvent event) {
        try {

            // étape 1: charger la classe de driver
            Class.forName("com.mysql.jdbc.Driver");
            // étape 2: créer l'objet de connexion

            String nom = profNameField.getText();
            String prenom = profPnameField.getText();
            String username = profUsernameField.getText();
            String password = profPasswordField.getText();

            if (nom.isEmpty() || prenom.isEmpty() || username.isEmpty() | password.isEmpty()) {
                errorProf.setText("Tous les champs sont obligatoires.");
            } else {
                errorProf.setText("");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/estcours", "root", "root");
                String sql = "insert into professeur (profNom,profPrenom,profUsername,profPassword) values(?,?,?,?)";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, nom);
                statement.setString(2, prenom);
                statement.setString(3, username);
                statement.setString(4, password);

                statement.executeUpdate();

                succesProf.setText("L'élement est bien inséré.");

                profNameField.clear();
                profPnameField.clear();
                profPasswordField.clear();
                profUsernameField.clear();

                profTable.getItems().clear();
                profTable.getColumns().clear();
                fetRowList(profTable,"professeur",data);
                fetColumnList(profTable,"professeur",data);
                con.close();
            }
        } catch (SQLException | ClassNotFoundException Q) {
            errorProf.setText("Erreur!");
            Q.printStackTrace();
        }

    }


    @FXML
    void addEtudiant(ActionEvent event) {
        try {
            // étape 1: charger la classe de driver
            Class.forName("com.mysql.jdbc.Driver");
            // étape 2: créer l'objet de connexion

            String nom = etudiantNameField.getText();
            String prenom = etudiantPnameField.getText();
            String username = etudiantUsernameField.getText();
            String password = etudiantPasswordField1.getText();

            if (nom.isEmpty() || prenom.isEmpty() || username.isEmpty() | password.isEmpty()) {
                errorEtudiant.setText("Tous les champs sont obligatoires.");
            } else {
                errorEtudiant.setText("");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/estcours", "root", "root");
                String sql = "insert into etudiant (etudiantNom,etudiantPrenom,etudiantUsername,etudiantPassword) values(?,?,?,?)";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, nom);
                statement.setString(2, prenom);
                statement.setString(3, username);
                statement.setString(4, password);

                statement.executeUpdate();

                succesEtudiant.setText("L'élement est bien inséré.");

                etudiantNameField.clear();
                etudiantPnameField.clear();
                etudiantUsernameField.clear();
                etudiantPasswordField1.clear();

                etudiantTable.getItems().clear();
                etudiantTable.getColumns().clear();
                fetRowList(etudiantTable,"etudiant",etudiantData);
                fetColumnList(etudiantTable,"etudiant",etudiantData);
                con.close();
            }
        } catch (SQLException | ClassNotFoundException Q) {
            errorProf.setText("Erreur!");
            Q.printStackTrace();
        }


    }

    @FXML
    void refreshEtudiant(ActionEvent event) throws SQLException {
        etudiantTable.getItems().clear();
        etudiantTable.getColumns().clear();
        fetRowList(etudiantTable,"etudiant",etudiantData);
        fetColumnList(etudiantTable,"etudiant",etudiantData);
    }
    @FXML
    void refreshProfTable(ActionEvent event) throws SQLException {
        profTable.getItems().clear();
        profTable.getColumns().clear();
        fetRowList(profTable,"professeur",data);
        fetColumnList(profTable,"professeur",data);
    }



    private ObservableList<ObservableList> data;
    private ObservableList<ObservableList> etudiantData;




    //only fetch columns
    private void fetColumnList(TableView tableViewName,String tableName, ObservableList dataName) {
        String SQL = "SELECT * from "+ tableName;
        try {
             Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/estcours", "root", "root");
            ResultSet rs = con.createStatement().executeQuery(SQL);

            //SQL FOR SELECTING ALL OF CUSTOMER
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableViewName.getColumns().removeAll(col);
                tableViewName.getColumns().addAll(col);

                System.out.println("Column [" + i + "] ");

            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }

    //fetches rows and data from the list
    private void fetRowList(TableView tableViewName,String tableName, ObservableList dataName) {
        String SQL = "SELECT * from "+ tableName;

        dataName = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/estcours", "root", "root");
            rs = con.createStatement().executeQuery(SQL);

            while (rs.next()) {
                //Iterate Row
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                dataName.add(row);

            }
            tableViewName.setItems(dataName);

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }





    @FXML
    void deleteSelectedRow(ActionEvent event) {

        int index = profTable.getSelectionModel().getSelectedIndex();

        System.out.println(index);

    }


}




