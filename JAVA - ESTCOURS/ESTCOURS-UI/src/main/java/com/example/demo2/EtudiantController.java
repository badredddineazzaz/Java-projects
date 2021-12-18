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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EtudiantController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField getIdFile;


    @FXML
    private TableView matiereTable;

    @FXML
    private Label username;

    @FXML
    private ComboBox typeCombo;

    @FXML
    private ComboBox matiereCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCombo.getItems().addAll("Cours", "TP","TD");
        matiereCombo.getItems().addAll("Java","PHP","CSS/JS","UML","Reseau","Oracle");
    }

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
    void searchTable(ActionEvent event) {
        matiereTable.getItems().clear();
        matiereTable.getColumns().clear();

        String element = (String) matiereCombo.getSelectionModel().getSelectedItem();
        String type = (String) typeCombo.getSelectionModel().getSelectedItem();
        searchData(type,element);
    }

    @FXML
    void openLink(ActionEvent event) {
        int id = Integer.parseInt(getIdFile.getText());



        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/estcours?serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("Select CoursUrl from cours where idCours=" + id + ";");
            while(resultset.next()){
                openUrl(resultset.getString("CoursUrl"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }


    }

    public void openUrl(String url) throws IOException {
        String url_open =url;
        java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
    }



    private ObservableList<ObservableList> data;



    //only fetch columns
    private void fetColumnList(String SQL) {

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
    private void fetRowList(String SQL) {
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

    public void searchData( String type,String matiere){
        String SQL = "SELECT * from cours WHERE CoursNom ='" + matiere + "' AND CoursType='" + type + "';" ;
        System.out.println(SQL);
        fetColumnList(SQL);
        fetRowList(SQL);


    }


}
