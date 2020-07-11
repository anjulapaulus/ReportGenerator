package com.codebuddy.controllers;

import com.codebuddy.LoggerInterface;
import com.codebuddy.models.CompanyModel;
import com.codebuddy.models.ManifestModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Company implements Initializable {
    @FXML TextField company_name;
    @FXML TextField telephone_num;
    @FXML TextArea address;

    //Error Labels
    @FXML Label addressErrorLabel;
    @FXML Label telephoneNumberErrorLabel;
    @FXML Label companyNameErrorLabel;

    public CompanyModel companyModel = new CompanyModel();
    LoggerInterface logger = new LoggerInterface();

    public void addOrUpdateCompany(ActionEvent event) {
        if (company_name.getText().trim().isEmpty()) {
            companyNameErrorLabel.setText("* company name field empty");
        } else {
            companyNameErrorLabel.setText("");
        }
        if (address.getText().trim().isEmpty()) {
            addressErrorLabel.setText("* company address field empty");
        } else {
            addressErrorLabel.setText("");
        }
        if (telephone_num.getText().trim().isEmpty()) {
            telephoneNumberErrorLabel.setText("* telephone number field empty");
        } else {
            telephoneNumberErrorLabel.setText("");
        }

        if (!company_name.getText().trim().isEmpty()
                && !address.getText().trim().isEmpty()
                && !telephone_num.getText().trim().isEmpty()
        ) {
            if (!companyModel.isExistCompany(company_name.getText())) {
                boolean check = companyModel.addCompanyDetails(
                        company_name.getText(),
                        address.getText(),
                        telephone_num.getText());
                if (check) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "The company details have been inserted", ButtonType.OK);
                    alert.showAndWait();
                    company_name.setText("");
                    address.setText("");
                    telephone_num.setText("");
                    companyNameErrorLabel.setText("");
                    addressErrorLabel.setText("");
                    telephone_num.setText("");

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "The company details cannot be inserted", ButtonType.OK);
                    alert.showAndWait();
                }
            } else {
                boolean check = companyModel.updateCompanyDetails(
                        company_name.getText(),
                        address.getText(),
                        telephone_num.getText());

                if (check) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "The company details have been updated", ButtonType.OK);
                    alert.showAndWait();
                    company_name.setText("");
                    address.setText("");
                    telephone_num.setText("");
                    companyNameErrorLabel.setText("");
                    addressErrorLabel.setText("");
                    telephone_num.setText("");

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "The company details cannot be updated", ButtonType.OK);
                    alert.showAndWait();
                }
            }

        }
    }
    public void deleteCompanyDetails(ActionEvent event){
        try {
            if (companyModel.isExistCompany(company_name.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Do you want to delete? ", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = alert.showAndWait();
                if (!result.isPresent()) {
                    hideSource(event);
                } else if (result.get() == ButtonType.YES) {
                    companyModel.deleteCompanyDetails(company_name.getText());
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Deleted company details ", ButtonType.OK);
                    alert1.showAndWait();
                    company_name.setText("");
                    address.setText("");
                    telephone_num.setText("");
                    companyNameErrorLabel.setText("");
                    addressErrorLabel.setText("");
                    telephone_num.setText("");
                } else if (result.get() == ButtonType.NO) {
                    hideSource(event);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The company details cannot be deleted", ButtonType.OK);
                alert.showAndWait();
                company_name.setText("");
                address.setText("");
                telephone_num.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void hideSource(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (companyModel.idDBConnected()) {
            logger.log("INFO", "Manifest.initialize.Database Connected");
            try {
                List<String> list1 = companyModel.getAllCompanies();
                TextFields.bindAutoCompletion(company_name, list1);

                company_name.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1.equals("")) {
//                        company_name.setText("");
                        address.setText("");
                        telephone_num.setText("");
                        companyNameErrorLabel.setText("");
                        addressErrorLabel.setText("");
                        telephone_num.setText("");
                    } else if (companyModel.isExistCompany(newValue1)) {
//                        System.out.println(referenceTextfield.getText());
                        companyModel.getCompanyDetails(newValue1);
                        address.setText(CompanyModel.getCompany_address());
                        telephone_num.setText(CompanyModel.getCompany_telephone());
                        companyNameErrorLabel.setText("");
                        addressErrorLabel.setText("");
                        telephoneNumberErrorLabel.setText("");
                    }else {
//                        company_name.setText("");
                        address.setText("");
                        telephone_num.setText("");
                        companyNameErrorLabel.setText("");
                        addressErrorLabel.setText("");
                        telephone_num.setText("");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
