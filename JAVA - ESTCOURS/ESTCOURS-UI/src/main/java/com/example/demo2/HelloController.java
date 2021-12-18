package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

public class HelloController {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/estcours";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";



    protected String UserName;
    protected String Password;

    public String getUserName() {
        UserName = String.valueOf(username.getText());
        return UserName;
    }

    public String getPassword() {
        Password= String.valueOf(password.getText());
        return Password;
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void login(ActionEvent event) throws IOException, SQLException {
        String user = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());

        if(radioAdmin.isSelected()){
            if(adminLogin(user,pass)){
                root = FXMLLoader.load(getClass().getResource("adminPanel.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        }
        if(radioProf.isSelected()){
            profLogin(user,pass);
            root = FXMLLoader.load(getClass().getResource("profeseeurPanel.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        if(radioEtudiant.isSelected()){
            etudiantLogin(user,pass);
            root = FXMLLoader.load(getClass().getResource("etudiantPanel.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

    }

    @FXML
    private Label invalidData;

    @FXML
    private RadioButton radioAdmin;

    @FXML
    private RadioButton radioEtudiant;

    @FXML
    private RadioButton radioProf;

    @FXML
    private TextField username;

    @FXML
    private TextField password;


    public boolean adminLogin(String username, String password) throws SQLException{
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            String sqlQuery = "SELECT * FROM admin WHERE adminUsername = ? and adminPassword = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery) ;
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                invalidData.setText("Bonjour");
                String userName=resultSet.getString("adminUsername");
                String Password = resultSet.getString("adminPassword");
                System.out.println(userName);
                return true;


            }else{
                invalidData.setText("user and pass incorrect");
                return false;
            }


        } catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }
        return false;

    }

    public void profLogin(String username, String password) throws SQLException{
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            String sqlQuery = "SELECT * FROM professeur WHERE profUsername = ? and profPassword = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery) ;
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                invalidData.setText("Bonjour");
            }else{
                invalidData.setText("user and pass incorrect");
            }

        } catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }

    }

    public void etudiantLogin(String username, String password) throws SQLException{
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            String sqlQuery = "SELECT * FROM etudiant WHERE etudiantUsername = ? and etudiantPassword = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery) ;
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                invalidData.setText("Bonjour");
            }else{
                invalidData.setText("user and pass incorrect");
            }


        } catch (SQLException e) {
            // print SQL exception information
            e.printStackTrace();
        }

    }






    }