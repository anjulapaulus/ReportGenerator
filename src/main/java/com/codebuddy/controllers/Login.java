package com.codebuddy.controllers;

import com.codebuddy.LoggerInterface;
import com.codebuddy.models.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class Login implements Initializable {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorEmailLabel;
    @FXML private Label errorPasswordLabel;

    public LoginModel loginModel =  new LoginModel();
    LoggerInterface logger = new LoggerInterface();

    public void Login (ActionEvent event){

        if(emailField.getText().trim().isEmpty()){
            errorEmailLabel.setText("* Email empty");
        }else {
            errorEmailLabel.setText("");
        }

        if (passwordField.getText().trim().isEmpty()){
            errorPasswordLabel.setText("* Password empty");
        }else {
            errorPasswordLabel.setText("");
        }

        if (!emailField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty()) {
            errorEmailLabel.setText("");
            errorPasswordLabel.setText("");
            try {
                String role = loginModel.login(emailField.getText(),passwordField.getText());
                if (role != null && role.equals("user")){
                    ((Node)event.getSource()).getScene().getWindow().hide();
                    Stage primaryStage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    try {
                        Pane root = loader.load(getClass().getResource("/views/home.fxml").openStream());
                        Scene scene = new Scene(root, 600, 400);
                        primaryStage.setTitle("Cargo Maintenance System");
                        primaryStage.setResizable(false);
                        primaryStage.setScene(scene);
                        primaryStage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if (role != null && role.equals("admin")){
                    ((Node)event.getSource()).getScene().getWindow().hide();
                    Stage primaryStage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    try {
                        Pane root = loader.load(getClass().getResource("/views/admin_home.fxml").openStream());
                        Scene scene = new Scene(root, 600, 400);
                        primaryStage.setTitle("Cargo Maintenance System");
                        primaryStage.setResizable(false);
                        primaryStage.setScene(scene);
                        primaryStage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    errorPasswordLabel.setText("Username and password incorrect");
                }
            } catch (SQLException e) {
                errorPasswordLabel.setText("Username and password incorrect");
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                logger.log("SEVERE",sw.toString());
            }

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (loginModel.idDBConnected()){

        }else {
           Alert alert = new Alert(Alert.AlertType.WARNING, "Database not initialized", ButtonType.OK);
           alert.showAndWait();
        }
    }
}
