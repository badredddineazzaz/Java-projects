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

public class ProfesseurController extends adminController implements Initializable  {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/estcours";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    @FXML
    private ComboBox<String> typeCombo;
    @FXML
    private ComboBox<String> matiereCombo;

    @FXML
    private TableView matiereTable;

    @FXML
    private Label coursCount;

    @FXML
    private Label errorMatiere;

    @FXML
    private Label etudiantCount;



    @FXML
    private TextField matiereLinkField;

    @FXML
    private TextField matiereNameField;





    @FXML
    private Button start;

    @FXML
    private Label succesMatiere;

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
        setUserName();
        coursNumber();
        etudsNumber();
    }




    @FXML
    void addStatsProf(ActionEvent event) {

    addStats();
    }

    public void showData() {

    }

    @FXML
    void addMatiere(ActionEvent event) {
        String element = matiereCombo.getSelectionModel().getSelectedItem();
        String type = typeCombo.getSelectionModel().getSelectedItem();
        String matiereName = matiereNameField.getText();
        String matiereLink = matiereLinkField.getText();

        try {
            // étape 1: charger la classe de driver
            Class.forName("com.mysql.jdbc.Driver");
            // étape 2: créer l'objet de connexion
            if (element.isBlank() || type.isBlank() || matiereName.isEmpty() | matiereLink.isEmpty()) {
                errorMatiere.setText("Tous les champs sont obligatoires.");
            } else {
                errorMatiere.setText("");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/estcours", "root", "root");
                String sql = "insert into cours (CoursNom,coursDescription,CoursType,CoursUrl) values(?,?,?,?)";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, element);
                statement.setString(2, matiereName);
                statement.setString(3, type);
                statement.setString(4, matiereLink);

                statement.executeUpdate();

                errorMatiere.setText("L'élement est bien inséré.");


                matiereLinkField.clear();
                matiereNameField.clear();

                matiereTable.getItems().clear();
                matiereTable.getColumns().clear();
                fetRowList();
                fetColumnList();
                con.close();
            }
        } catch (SQLException | ClassNotFoundException Q) {
            errorMatiere.setText("Erreur!");
            Q.printStackTrace();
        }

    }




    @FXML
    void refreshTable(ActionEvent event) {
        fetColumnList();
        fetRowList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCombo.getItems().addAll("Cours", "TP","TD");
        matiereCombo.getItems().addAll("Java","PHP","CSS/JS","UML","Reseau","Oracle");
        fetColumnList();
        fetRowList();
        addStats();
    }



    private ObservableList<ObservableList> data;
    String SQL = "SELECT * from cours";


    //only fetch columns
    private void fetColumnList() {

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

               matiereTable.getColumns().removeAll(col);
                matiereTable.getColumns().addAll(col);
            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }

    //fetches rows and data from the list
    private void fetRowList() {
        data = FXCollections.observableArrayList();
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
                data.add(row);

            }
            matiereTable.setItems(data);

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
