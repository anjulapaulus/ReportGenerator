package com.codebuddy.controllers;

import com.codebuddy.LoggerInterface;
import com.codebuddy.models.LoginModel;
import com.codebuddy.models.ManifestModel;
import com.codebuddy.models.RegisterModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Register implements Initializable {
    @FXML
    private TextField userNameOrEmail;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private ChoiceBox userTypeCombo;

    //Error Label
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label confirmPassordErrorLabel;
    @FXML
    private Label userTypeError;

    public RegisterModel registerModel =  new RegisterModel();
    LoggerInterface logger = new LoggerInterface();

    public void Register (ActionEvent event){
        if(userNameOrEmail.getText().trim().isEmpty()){
            emailErrorLabel.setText("* email/username empty");
        }else {
            emailErrorLabel.setText("");
        }

        if (password.getText().trim().isEmpty()){
            passwordErrorLabel.setText("* password empty");
        }else {
            passwordErrorLabel.setText("");
        }

        if (confirmPassword.getText().trim().isEmpty()){
            confirmPassordErrorLabel.setText("* confirm Password empty");
        }else {
            confirmPassordErrorLabel.setText("");
        }

        if (!password.getText().equals(confirmPassword.getText())){
            confirmPassordErrorLabel.setText("* password does not match ");
        }else {
            confirmPassordErrorLabel.setText("");
        }

        if (!userNameOrEmail.getText().trim().isEmpty()
                && !password.getText().trim().isEmpty()
                && !confirmPassword.getText().trim().isEmpty()
                && password.getText().equals(confirmPassword.getText())
        ) {
            emailErrorLabel.setText("");
            passwordErrorLabel.setText("");
            confirmPassordErrorLabel.setText("");
            try {
                if (!registerModel.checkIfUserExists(userNameOrEmail.getText())) {
                    boolean check = registerModel.registerUser(
                            userNameOrEmail.getText(),
                            password.getText(),
                            userTypeCombo.getValue().toString()
                    );
                    if (check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "The user has been registered", ButtonType.OK);
                        alert.showAndWait();
                        userNameOrEmail.setText("");
                        password.setText("");
                        confirmPassword.setText("");
                        emailErrorLabel.setText("");
                        passwordErrorLabel.setText("");
                        confirmPassordErrorLabel.setText("");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "The user cannot be registered", ButtonType.OK);
                        alert.showAndWait();
                    }
                } else {
                    boolean check = registerModel.updateUser(
                            userNameOrEmail.getText(),
                            password.getText(),
                            userTypeCombo.getValue().toString()
                    );
                    if (check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "The user has been updated", ButtonType.OK);
                        alert.showAndWait();
                        userNameOrEmail.setText("");
                        password.setText("");
                        confirmPassword.setText("");
                        emailErrorLabel.setText("");
                        passwordErrorLabel.setText("");
                        confirmPassordErrorLabel.setText("");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "The user cannot be updated", ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (registerModel.idDBConnected()){
//            logger.log("INFO","Register.initialize.Database Connected");
            List<String> list1 = registerModel.getAllUserNames();
            TextFields.bindAutoCompletion(userNameOrEmail, list1);
            userTypeCombo.getItems().addAll("admin","user");
            userTypeCombo.setValue("user");
            userNameOrEmail.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                if (newValue1.equals("")) {
                    password.setText("");
                    confirmPassword.setText("");
                    emailErrorLabel.setText("");
                    passwordErrorLabel.setText("");
                    confirmPassordErrorLabel.setText("");
                    userTypeCombo.setValue("user");
                } else if (registerModel.checkIfUserExists(newValue1)) {
//                        System.out.println(referenceTextfield.getText());
                    registerModel.getUser(newValue1);
                    password.setText(RegisterModel.getPassword());
                    confirmPassword.setText(RegisterModel.getPassword());
                    userTypeCombo.setValue(RegisterModel.getRole());
                    emailErrorLabel.setText("");
                    passwordErrorLabel.setText("");
                    confirmPassordErrorLabel.setText("");
                } else {
                    password.setText("");
                    confirmPassword.setText("");
                    emailErrorLabel.setText("");
                    passwordErrorLabel.setText("");
                    confirmPassordErrorLabel.setText("");
                    userTypeCombo.setValue("user");
                }
            });
        }else {
//            logger.log("SEVERE","Register.initialize.Database not Connected");
        }
    }
}
