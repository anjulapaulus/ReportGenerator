package com.codebuddy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminHome{
    public void OpenCompanyProfile (ActionEvent event) {
//        hideSource(event);
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane root = loader.load(getClass().getResource("/views/company_profile.fxml").openStream());
            Scene scene = new Scene(root, 600, 650);
            primaryStage.setTitle("Cargo Maintenance System");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void OpenCargoDetails (ActionEvent event) {
//        hideSource(event);
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane root = loader.load(getClass().getResource("/views/cargo_details.fxml").openStream());
            Scene scene = new Scene(root, 820, 550);
            primaryStage.setTitle("Cargo Maintenance System");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OpenManifest (ActionEvent event) {
//        hideSource(event);
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane root = loader.load(getClass().getResource("/views/manifest_details.fxml").openStream());
            Scene scene = new Scene(root, 1110, 700);
            primaryStage.setTitle("Cargo Maintenance System");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OpenCompanyDetails (ActionEvent event) {
//        hideSource(event);
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane root = loader.load(getClass().getResource("/views/company.fxml").openStream());
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Cargo Maintenance System");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OpenRegistration (ActionEvent event) {
//        hideSource(event);
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane root = loader.load(getClass().getResource("/views/register.fxml").openStream());
            Scene scene = new Scene(root, 300, 400);
            primaryStage.setTitle("Cargo Maintenance System");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OpenDeleteUser (ActionEvent event) {
//        hideSource(event);
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane root = loader.load(getClass().getResource("/views/delete_user.fxml").openStream());
            Scene scene = new Scene(root, 500, 400);
            primaryStage.setTitle("Cargo Maintenance System");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
