package com.codebuddy.controllers;

import com.codebuddy.LoggerInterface;
import com.codebuddy.models.CargoDetailsModel;
import com.codebuddy.models.CompanyProfileModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CargoDetails implements Initializable {

    @FXML
    private TextField referenceTextField;
    @FXML
    private DatePicker date;
    @FXML
    private TextArea remarksTextArea;
    @FXML
    private TextField vesselTextfield;
    @FXML
    private TextField voyageTextField;
    @FXML
    private DatePicker dueOn;
    @FXML
    private TextField portOfShipmentTextField;
    @FXML
    private TextField portOfLoadingTextField;
    @FXML
    private TextField portOfDischargeTextfield;
    @FXML
    private TextField serialNumberTextfield;
    @FXML
    private TextField numberOfShipmentsTextfield;
    @FXML
    private TextField containerStatusTextField;
    @FXML
    private TextField DangerousCargoTextfield;
    @FXML
    private TextField liquorCigTextfield;
    @FXML
    private TextField sercuCargoTextField;
    @FXML
    private TextField transshipmentCargoTextField;
    @FXML
    private TextField personalCargoTextField;
    @FXML
    private Label fileNumberLabel;
    @FXML
    private Label systemReferenceLabel;

    //Error Labels
    @FXML
    private Label referneceErrorLabel;
    @FXML
    private Label containerStatusError;
    @FXML
    private Label dateError;
    @FXML
    private Label dangerousCargoListError;
    @FXML
    private Label vesselError;
    @FXML
    private Label liqCigError;
    @FXML
    private Label voyageError;
    @FXML
    private Label securityCargoError;
    @FXML
    private Label dueOnError;
    @FXML
    private Label transshipmentCargoError;
    @FXML
    private Label portOfShipmentError;
    @FXML
    private Label personalCargoError;
    @FXML
    private Label portOfLoadingError;
    @FXML
    private Label portOfDischargeError;
    @FXML
    private Label serialNumberError;
    @FXML
    private Label NumShipmentsError;
    @FXML
    private Label remarksError;

    public CargoDetails() {
    }

    private final String pattern = "yyyy-MM-dd";
    final BooleanProperty firstTime = new SimpleBooleanProperty(true);

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

    public CargoDetailsModel cargoDetailsModel = new CargoDetailsModel();
    LoggerInterface logger = new LoggerInterface();

    public void addCargoDetails(ActionEvent event) {
        if (referenceTextField.getText().trim().isEmpty()) {
            referneceErrorLabel.setText("* reference field empty");
        } else {
            referneceErrorLabel.setText("");
        }

        if (containerStatusTextField.getText().trim().isEmpty()) {
            containerStatusError.setText("* container status field empty");
        } else {
            containerStatusError.setText("");
        }

        if (date.getValue() == null) {
            dateError.setText("* date field empty");
        } else {
            dateError.setText("");
        }

        if (DangerousCargoTextfield.getText().trim().isEmpty()) {
            dangerousCargoListError.setText("* dangerous cargo field empty");
        } else {
            dangerousCargoListError.setText("");
        }
        if (vesselTextfield.getText().trim().isEmpty()) {
            vesselError.setText("* vessel field empty");
        } else {
            vesselError.setText("");
        }

        if (liquorCigTextfield.getText().trim().isEmpty()) {
            liqCigError.setText("* liquor cigarette field empty");
        } else {
            liqCigError.setText("");
        }

        if (voyageTextField.getText().trim().isEmpty()) {
            voyageError.setText("* voyage field empty");
        } else {
            voyageError.setText("");
        }

        if (sercuCargoTextField.getText().trim().isEmpty()) {
            securityCargoError.setText("* security cargo declaration field empty");
        } else {
            securityCargoError.setText("");
        }

        if (dueOn.getValue() == null) {
            dueOnError.setText("* due on field empty");
        } else {
            dueOnError.setText("");
        }

        if (transshipmentCargoTextField.getText().trim().isEmpty()) {
            transshipmentCargoError.setText("* transshipment cargo field empty");
        } else {
            transshipmentCargoError.setText("");
        }

        if (portOfShipmentTextField.getText().trim().isEmpty()) {
            portOfShipmentError.setText("* port of shipment field empty");
        } else {
            portOfShipmentError.setText("");
        }

        if (personalCargoTextField.getText().trim().isEmpty()) {
            personalCargoError.setText("* personal cargo field empty");
        } else {
            personalCargoError.setText("");
        }

        if (portOfLoadingTextField.getText().trim().isEmpty()) {
            portOfLoadingError.setText("* port of loading field empty");
        } else {
            portOfLoadingError.setText("");
        }

        if (remarksTextArea.getText().trim().isEmpty()) {
            remarksError.setText("* remarks field empty");
        } else {
            remarksError.setText("");
        }

        if (portOfDischargeTextfield.getText().trim().isEmpty()) {
            portOfDischargeError.setText("* port of discharge field empty");
        } else {
            portOfDischargeError.setText("");
        }

        if (serialNumberTextfield.getText().trim().isEmpty()) {
            serialNumberError.setText("* serial number field empty");
        } else {
            serialNumberError.setText("");
        }

        if (numberOfShipmentsTextfield.getText().trim().isEmpty()) {
            NumShipmentsError.setText("* number of shipments field empty");
        } else {
            NumShipmentsError.setText("");
        }
        if (!referenceTextField.getText().trim().isEmpty()
                && !containerStatusTextField.getText().trim().isEmpty()
                && date.getValue() != null
                && !DangerousCargoTextfield.getText().trim().isEmpty()
                && !vesselTextfield.getText().trim().isEmpty()
                && !liquorCigTextfield.getText().trim().isEmpty()
                && !voyageTextField.getText().trim().isEmpty()
                && !sercuCargoTextField.getText().trim().isEmpty()
                && dueOn.getValue() != null
                && !transshipmentCargoTextField.getText().trim().isEmpty()
                & !portOfShipmentTextField.getText().trim().isEmpty()
                & !personalCargoTextField.getText().trim().isEmpty()
                & !portOfLoadingTextField.getText().trim().isEmpty()
                & !remarksTextArea.getText().trim().isEmpty()
                & !portOfDischargeTextfield.getText().trim().isEmpty()
                & !serialNumberTextfield.getText().trim().isEmpty()
                & !numberOfShipmentsTextfield.getText().trim().isEmpty()
        ) {
            try {
                if (!cargoDetailsModel.isExistCargoDetails(referenceTextField.getText())) {
                    boolean check = cargoDetailsModel.addCargoDetails(
                            systemReferenceLabel.getText(),
                            fileNumberLabel.getText(),
                            referenceTextField.getText(),
                            date.getValue().toString(),
                            vesselTextfield.getText(),
                            voyageTextField.getText(),
                            dueOn.getValue().toString(),
                            portOfShipmentTextField.getText(),
                            portOfLoadingTextField.getText(),
                            portOfDischargeTextfield.getText(),
                            serialNumberTextfield.getText(),
                            numberOfShipmentsTextfield.getText(),
                            containerStatusTextField.getText(),
                            DangerousCargoTextfield.getText(),
                            liquorCigTextfield.getText(),
                            sercuCargoTextField.getText(),
                            transshipmentCargoTextField.getText(),
                            personalCargoTextField.getText(),
                            remarksTextArea.getText()
                    );
                    if (check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "The cargo details have been inserted", ButtonType.OK);
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "The cargo details cannot be inserted", ButtonType.OK);
                        alert.showAndWait();
                    }
                } else {
                    boolean check = cargoDetailsModel.updateCargoDetails(
                            referenceTextField.getText(),
                            date.getValue().toString(),
                            vesselTextfield.getText(),
                            voyageTextField.getText(),
                            dueOn.getValue().toString(),
                            portOfShipmentTextField.getText(),
                            portOfLoadingTextField.getText(),
                            portOfDischargeTextfield.getText(),
                            serialNumberTextfield.getText(),
                            numberOfShipmentsTextfield.getText(),
                            containerStatusTextField.getText(),
                            DangerousCargoTextfield.getText(),
                            liquorCigTextfield.getText(),
                            sercuCargoTextField.getText(),
                            transshipmentCargoTextField.getText(),
                            personalCargoTextField.getText(),
                            remarksTextArea.getText()
                    );
                    if (check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "The cargo details have been updated", ButtonType.OK);
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "The cargo details cannot be updated", ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

        public void deleteCompanyProfile(ActionEvent event) {
            if (cargoDetailsModel.isExistCargoDetails(referenceTextField.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Do you want to delete? ", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = alert.showAndWait();
                if (!result.isPresent()) {
                    hideSource(event);
                }
                else if (result.get() == ButtonType.YES) {
                    cargoDetailsModel.deleteCompanyDetails(referenceTextField.getText());
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Deleted Cargo Details ", ButtonType.OK);
                    alert1.showAndWait();
                    referenceTextField.setText("");
                    setNull();
                }
                else if (result.get() == ButtonType.NO) {
                    hideSource(event);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The cargo details cannot be deleted", ButtonType.OK);
                alert.showAndWait();
                setNull();
            }

        }
    private void hideSource(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public String systemReference(){
        String id = CargoDetailsModel.getID();
        String year = String.valueOf(Year.now().getValue());

        return "CMS/"+year+"/"+id;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (cargoDetailsModel.idDBConnected()) {
            logger.log("INFO", "Company.initialize.Database Connected");
            try {
                List<String> list1 = cargoDetailsModel.getAllCargoDetails();
                TextFields.bindAutoCompletion(referenceTextField, list1);
                fileNumberLabel.setText(cargoDetailsModel.getFileNumber());
                systemReferenceLabel.setText(systemReference());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            referenceTextField.textProperty().addListener((observable, oldValue, newValue) -> {

                if (newValue.equals("")) {
                    setNull();
                } else if (cargoDetailsModel.isExistCargoDetails(newValue)) {
                    CargoDetailsModel.getCargoDetails(newValue);
                    fileNumberLabel.setText(CargoDetailsModel.getFile_number());
                    systemReferenceLabel.setText(CargoDetailsModel.getSystem_reference());
                    LocalDate dater = (LocalDate) converter.fromString(CargoDetailsModel.getDate());
                    date.setConverter(converter);
                    date.setValue(dater);
                    vesselTextfield.setText(CargoDetailsModel.getVessel());
                    voyageTextField.setText(CargoDetailsModel.getVoyage());
                    LocalDate due_on = (LocalDate) converter.fromString(CargoDetailsModel.getDue_on());
                    dueOn.setConverter(converter);
                    dueOn.setValue(due_on);
                    portOfShipmentTextField.setText(CargoDetailsModel.getPort_shipment());
                    portOfLoadingTextField.setText(CargoDetailsModel.getPort_loading());
                    portOfDischargeTextfield.setText(CargoDetailsModel.getPort_discharge());
                    serialNumberTextfield.setText(CargoDetailsModel.getSerial_number());
                    numberOfShipmentsTextfield.setText(CargoDetailsModel.getNum_shipments());
                    containerStatusTextField.setText(CargoDetailsModel.getContainer_status());
                    DangerousCargoTextfield.setText(CargoDetailsModel.getDangerous_cargo_list());
                    liquorCigTextfield.setText(CargoDetailsModel.getLiquor_cigerettes());
                    sercuCargoTextField.setText(CargoDetailsModel.getSecurity_cargo_declaration());
                    transshipmentCargoTextField.setText(CargoDetailsModel.getTransshipment_cargo());
                    personalCargoTextField.setText(CargoDetailsModel.getPersonal_cargo());
                    remarksTextArea.setText(CargoDetailsModel.getRemarks());
                    referneceErrorLabel.setText("");
                    containerStatusError.setText("");
                    dateError.setText("");
                    dangerousCargoListError.setText("");
                    vesselError.setText("");
                    liqCigError.setText("");
                    voyageError.setText("");
                    securityCargoError.setText("");
                    dueOnError.setText("");
                    transshipmentCargoError.setText("");
                    portOfShipmentError.setText("");
                    personalCargoError.setText("");
                    portOfLoadingError.setText("");
                    remarksError.setText("");
                    portOfDischargeError.setText("");
                    serialNumberError.setText("");
                    NumShipmentsError.setText("");
                }else {
                    setNull();
                }
            });


        } else {
            logger.log("SEVERE", "Company.initialize.Database not Connected");
        }
    }

    private void setNull() {
        try {
            fileNumberLabel.setText(cargoDetailsModel.getFileNumber());
            systemReferenceLabel.setText(systemReference());
            date.setValue(null);
            vesselTextfield.setText("");
            voyageTextField.setText("");
            dueOn.setValue(null);
            portOfShipmentTextField.setText("");
            portOfLoadingTextField.setText("");
            portOfDischargeTextfield.setText("");
            serialNumberTextfield.setText("");
            numberOfShipmentsTextfield.setText("");
            containerStatusTextField.setText("");
            DangerousCargoTextfield.setText("");
            liquorCigTextfield.setText("");
            sercuCargoTextField.setText("");
            transshipmentCargoTextField.setText("");
            personalCargoTextField.setText("");
            remarksTextArea.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
