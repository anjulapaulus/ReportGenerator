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
    @FXML TextArea address1;
    @FXML TextArea address2;
    @FXML TextArea address3;
    @FXML TextArea address4;
    @FXML TextArea address5;
    @FXML TextArea address6;

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
        if (address1.getText().trim().isEmpty()) {
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
                && !address1.getText().trim().isEmpty()
                && !telephone_num.getText().trim().isEmpty()
        ) {
            if (!companyModel.isExistCompany(company_name.getText())) {
                boolean check = companyModel.addCompanyDetails(
                        company_name.getText(),
                        address1.getText(),
                        telephone_num.getText(),
                        address2.getText(),
                        address3.getText(),
                        address4.getText(),
                        address5.getText(),
                        address6.getText());
                if (check) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "The company details have been inserted", ButtonType.OK);
                    alert.showAndWait();
                    company_name.setText("");
                    address1.setText("");
                    address2.setText("");
                    address3.setText("");
                    address4.setText("");
                    address5.setText("");
                    address6.setText("");
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
                        address1.getText(),
                        telephone_num.getText(),address2.getText(),
                        address3.getText(),
                        address4.getText(),
                        address5.getText(),
                        address6.getText());

                if (check) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "The company details have been updated", ButtonType.OK);
                    alert.showAndWait();
                    company_name.setText("");
                    address1.setText("");
                    telephone_num.setText("");
                    address2.setText("");
                    address3.setText("");
                    address4.setText("");
                    address5.setText("");
                    address6.setText("");
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
                    address1.setText("");
                    telephone_num.setText("");
                    address2.setText("");
                    address3.setText("");
                    address4.setText("");
                    address5.setText("");
                    address6.setText("");
                    companyNameErrorLabel.setText("");
                    addressErrorLabel.setText("");
                    telephoneNumberErrorLabel.setText("");
                } else if (result.get() == ButtonType.NO) {
                    hideSource(event);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The company details cannot be deleted", ButtonType.OK);
                alert.showAndWait();
                company_name.setText("");
                address1.setText("");
                telephone_num.setText("");
                address2.setText("");
                address3.setText("");
                address4.setText("");
                address5.setText("");
                address6.setText("");
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
//            logger.log("INFO", "Manifest.initialize.Database Connected");
            try {
                List<String> list1 = companyModel.getAllCompanies();
                TextFields.bindAutoCompletion(company_name, list1);

                company_name.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1.equals("")) {
//                        company_name.setText("");
                        address1.setText("");
                        telephone_num.setText("");
                        companyNameErrorLabel.setText("");
                        addressErrorLabel.setText("");
                        telephoneNumberErrorLabel.setText("");
                        address2.setText("");
                        address3.setText("");
                        address4.setText("");
                        address5.setText("");
                        address6.setText("");
                    } else if (companyModel.isExistCompany(newValue1)) {
//                        System.out.println(referenceTextfield.getText());
                        companyModel.getCompanyDetails(newValue1);
                        address1.setText(CompanyModel.getCompany_address());
                        telephone_num.setText(CompanyModel.getCompany_telephone());
                        address2.setText(CompanyModel.getCompany_address2());
                        address3.setText(CompanyModel.getCompany_address3());
                        address4.setText(CompanyModel.getCompany_address4());
                        address5.setText(CompanyModel.getCompany_address5());
                        address6.setText(CompanyModel.getCompany_address6());
                        companyNameErrorLabel.setText("");
                        addressErrorLabel.setText("");
                        telephoneNumberErrorLabel.setText("");
                    }else {
//                        company_name.setText("");
                        address1.setText("");
                        telephone_num.setText("");
                        address2.setText("");
                        address3.setText("");
                        address4.setText("");
                        address5.setText("");
                        address6.setText("");
                        companyNameErrorLabel.setText("");
                        addressErrorLabel.setText("");
                        telephoneNumberErrorLabel.setText("");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Database not initialized", ButtonType.OK);
            alert.showAndWait();
        }

    }
}
