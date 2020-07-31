package com.codebuddy.controllers;

import com.codebuddy.LoggerInterface;
import com.codebuddy.models.CompanyProfileModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class CompanyProfile implements Initializable {
    @FXML
    private TextField companyNameTextField;
    @FXML
    private TextArea companyAddressTextArea;
    @FXML
    private TextField telephoneNumberTextField;
    @FXML
    private TextField fileNumberTextField;
    @FXML
    private DatePicker date;
    @FXML
    private TextArea remarksTextArea;
    @FXML
    private Label companyNameError;
    @FXML
    private Label companyAddressError;
    @FXML
    private Label telephoneNumberError;
    @FXML
    private Label fileNumberError;
    @FXML
    private Label dateError;
    @FXML
    private Label remarksError;

    private final String pattern = "yyyy-MM-dd";
    final BooleanProperty firstTime = new SimpleBooleanProperty(true);

    public CompanyProfile() {
    }

    StringConverter converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern(pattern);

        @Override
        public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }

        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
    };


    public CompanyProfileModel companyModel = new CompanyProfileModel();
    LoggerInterface logger = new LoggerInterface();

    public void addUpdateCompanyProfile(ActionEvent event) {

        if (companyNameTextField.getText().trim().isEmpty()) {
            companyNameError.setText("* company name field empty");
        } else {
            companyNameError.setText("");
        }

        if (companyAddressTextArea.getText().trim().isEmpty()) {
            companyAddressError.setText("* company address field empty");
        } else {
            companyAddressError.setText("");
        }

        if (telephoneNumberTextField.getText().trim().isEmpty()) {
            telephoneNumberError.setText("* telephone number field empty");
        } else {
            telephoneNumberError.setText("");
        }
        if (fileNumberTextField.getText().trim().isEmpty()) {
            fileNumberError.setText("* file number field empty");
        } else {
            fileNumberError.setText("");
        }
        if (date.getValue() == null) {
            dateError.setText("* date field empty");
        } else {
            dateError.setText("");
        }

        if (remarksTextArea.getText().trim().isEmpty()) {
            remarksError.setText("* remarks field empty");
        } else {
            remarksError.setText("");
        }

        if (!companyNameTextField.getText().trim().isEmpty()
                && !companyAddressTextArea.getText().trim().isEmpty()
                && !telephoneNumberTextField.getText().trim().isEmpty()
                && !companyAddressTextArea.getText().trim().isEmpty()
                && !fileNumberTextField.getText().trim().isEmpty()
                && date.getValue() != null
                && !remarksTextArea.getText().trim().isEmpty()
        ) {
            try {
                if (companyModel.isExistCompany()) {
                    boolean check = companyModel.updateCompany(companyNameTextField.getText(), companyAddressTextArea.getText()
                            , telephoneNumberTextField.getText(), fileNumberTextField.getText(), date.getValue().toString(), remarksTextArea.getText());

                    if (check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "The company details have been updated", ButtonType.OK);
                        alert.showAndWait();
                        hideSource(event);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "The company details cannot be updated", ButtonType.OK);
                        alert.showAndWait();
                    }
                } else {
                    System.out.println(companyModel.isExistCompany());
                    boolean check = companyModel.addCompany(companyNameTextField.getText(), companyAddressTextArea.getText()
                            , telephoneNumberTextField.getText(), fileNumberTextField.getText(), date.getValue().toString(), remarksTextArea.getText());
                    if (check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "The company details inserted", ButtonType.OK);
                        alert.showAndWait();
                        hideSource(event);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "The company details cannot be inserted", ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


    private void hideSource(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (companyModel.idDBConnected()) {
//            logger.log("INFO", "Company.initialize.Database Connected");
            companyModel.getLatestCompanyDetails();
            companyNameTextField.setText(CompanyProfileModel.getCompany_name());
            companyAddressTextArea.setText(CompanyProfileModel.getCompany_address());
            telephoneNumberTextField.setText(CompanyProfileModel.getTelephone());
            fileNumberTextField.setText(CompanyProfileModel.getFile_number());
            LocalDate localDate = (LocalDate) converter.fromString(CompanyProfileModel.getDate());
            date.setConverter(converter);
            date.setValue(localDate);
            remarksTextArea.setText(CompanyProfileModel.getRemarks());

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Database not initialized", ButtonType.OK);
            alert.showAndWait();
        }
    }


}
