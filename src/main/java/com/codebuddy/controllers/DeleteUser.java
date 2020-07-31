package com.codebuddy.controllers;

import com.codebuddy.models.RegisterModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeleteUser implements Initializable {
    @FXML
    TextField username_email;
    @FXML
    TableView<Users> userTableView;
    @FXML
    TableColumn<Users, String> username;
    @FXML
    TableColumn<Users, String> role;

    RegisterModel registerModel = new RegisterModel();
    Connection connection = null;

    public static ObservableList<Users> users_table = FXCollections.observableArrayList();

    public void Delete(ActionEvent event){
        try {
            if (registerModel.checkIfUserExists(username_email.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Do you want to delete user? ", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = alert.showAndWait();
                if (!result.isPresent()) {
                    hideSource(event);
                } else if (result.get() == ButtonType.YES) {
                    registerModel.deleteContainerDetails(username_email.getText());
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Deleted user ", ButtonType.OK);
                    alert1.showAndWait();
                    userTableView.getItems().clear();
                    userTableView.refresh();
                    updateTable(connection);
                    System.out.println(users_table);
                    userTableView.setItems(users_table);
                } else if (result.get() == ButtonType.NO) {
                    hideSource(event);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The user cannot be deleted", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTable(Connection connection1) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
            String query = "SELECT * FROM users";
            try {
                preparedStatement = connection1.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                        users_table.add(new Users(resultSet.getString("email"),
                                resultSet.getString("role")
                        ));

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    preparedStatement.close();
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    private void hideSource(javafx.event.ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setCellValueFactory(new PropertyValueFactory<>("UsernameEmail"));
        role.setCellValueFactory(new PropertyValueFactory<>("Role"));
        if (registerModel.idDBConnected()){
            connection = registerModel.connection();
            List<String> list1 = registerModel.getAllUserNames();
            TextFields.bindAutoCompletion(username_email, list1);
            users_table.clear();
            userTableView.getItems().clear();
            userTableView.refresh();
            updateTable(connection);
            userTableView.setItems(users_table);
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Database not initialized", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
