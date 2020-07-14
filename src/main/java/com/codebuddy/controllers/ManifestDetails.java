package com.codebuddy.controllers;

import com.codebuddy.LoggerInterface;
import com.codebuddy.ReportGenerator;
import com.codebuddy.models.CargoDetailsModel;
import com.codebuddy.models.CompanyModel;
import com.codebuddy.models.CompanyProfileModel;
import com.codebuddy.models.ManifestModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.controlsfx.control.textfield.TextFields;

public class ManifestDetails implements Initializable {
    @FXML
    Label fileNumberLabel;
    @FXML
    Label systemReferenceLabel;
    @FXML
    TextField referenceTextfield;
    @FXML
    TextField mblTextField;
    @FXML
    TextField masterShipperTextfield;
    @FXML
    TextField notifyPartyTextfield;
    @FXML
    TextField consigneeTextfield;
    @FXML
    TextField hblNumberTextfield;
    @FXML
    ComboBox<String> shipper_address;
    @FXML
    ComboBox<String> notify_party_address;
    @FXML
    ComboBox<String> consignee_address;
    @FXML
    TextArea descriptionTextArea;
    @FXML
    TextArea marksTextArea;
    @FXML
    TextField grossWeightTextField;
    @FXML
    TextField numberOfPackagesTextfield;
    @FXML
    TextField netWeightTextfield;
    @FXML
    TextField packageTypeTextfield;
    @FXML
    TextField cbmTextfield;
    @FXML
    ComboBox<String> freightTextfield;
    @FXML
    DatePicker doExpiryPicker;
    @FXML
    CheckBox netWeightChoiceBox;
    //Labels
    @FXML
    Label referenceLabel;
    @FXML
    Label freightErrorLabel;
    @FXML
    Label cbmErrorLabel;
    @FXML
    Label doExpiryErrorLabel;
    @FXML
    Label packageTypeErrorLabel;
    @FXML
    Label netWeightErrorLabel;
    @FXML
    Label numberOfPackagesErrorLabel;
    @FXML
    Label grossWeightErrorLabel;
    @FXML
    Label descriptionErroLabel;
    @FXML
    Label consigneeErrorLabel;
    @FXML
    Label notifyPartyErrorLabel;
    @FXML
    Label hblErrorLabel;
    @FXML
    Label mblNumberErrorLabel;
    @FXML
    Label masterShipperErrorLabel;
    @FXML
    Label marksErrorLabel;



    //Container Details
    @FXML
    TextField containerNumTextfield;
    @FXML
    TextField sealNumTextfield;
    @FXML
    ChoiceBox<String> sizeChoiceBox;
    @FXML
    TextField statusTextfield;
    @FXML
    TextField packagesTextfield;
    @FXML
    TextField cbmContTextfield;
    //Labels
    @FXML
    Label cbmConatinErrorLabel;
    @FXML
    Label packagesErrorLabel;
    @FXML
    Label statusErrorLabel;
    @FXML
    Label sizeErrorLabel;
    @FXML
    Label sealNumberErrorLabel;
    @FXML
    Label containerNumberErrorLabel;

    //Table view
    @FXML
    TableView<Container> containerTableView;
    @FXML
    TableColumn<Container, String> containerNumber;
    @FXML
    TableColumn<Container, String> sealNumber;
    @FXML
    TableColumn<Container, String> sizeList;
    @FXML
    TableColumn<Container, String> statusList;
    @FXML
    TableColumn<Container, String> packages;
    @FXML
    TableColumn<Container, String> cbmL;

    public static ObservableList<Container> container_table = FXCollections.observableArrayList();

    Connection connection = null;

    public ManifestDetails() {
    }

    private final String pattern = "yyyy-MM-dd";
    final BooleanProperty firstTime = new SimpleBooleanProperty(true);

    StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
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


    public ManifestModel manifestModel = new ManifestModel();
    public CargoDetailsModel cargoDetailsModel = new CargoDetailsModel();
    public CompanyModel companyModel = new CompanyModel();
    LoggerInterface logger = new LoggerInterface();

    public void addORUpdateContainerDetails(ActionEvent event) {
        if (referenceTextfield.getText().trim().isEmpty()) {
            referenceLabel.setText("* reference field empty");
        } else {
            referenceLabel.setText("");
        }
        if (containerNumTextfield.getText().trim().isEmpty()) {
            containerNumberErrorLabel.setText("* container number empty");
        } else {
            containerNumberErrorLabel.setText("");
        }
        if (sealNumTextfield.getText().trim().isEmpty()) {
            sealNumberErrorLabel.setText("* seal number empty");
        } else {
            sealNumberErrorLabel.setText("");
        }
//        if (sizeTextfield.getText().trim().isEmpty()) {
//            sizeErrorLabel.setText("* size empty");
//        } else {
//            sizeErrorLabel.setText("");
//        }
        if (statusTextfield.getText().trim().isEmpty()) {
            statusErrorLabel.setText("* status empty");
        } else {
            statusErrorLabel.setText("");
        }
        if (packagesTextfield.getText().trim().isEmpty()) {
            packagesErrorLabel.setText("* packages empty");
        } else {
            packagesErrorLabel.setText("");
        }
        if (cbmContTextfield.getText().trim().isEmpty()) {
            cbmConatinErrorLabel.setText("* cbm empty");
        } else {
            cbmConatinErrorLabel.setText("");
        }

        if (!referenceTextfield.getText().trim().isEmpty()
                && !containerNumTextfield.getText().trim().isEmpty()
                && !sealNumTextfield.getText().trim().isEmpty()
//                && !sizeTextfield.getText().trim().isEmpty()
                && !statusTextfield.getText().trim().isEmpty()
                && !packagesTextfield.getText().trim().isEmpty()
                && !cbmContTextfield.getText().trim().isEmpty()
        ) {
            try {
                if (!manifestModel.isContainerDetailsExist(referenceTextfield.getText(), containerNumTextfield.getText())) {
                    boolean check = manifestModel.addContainerDetails(
                            containerNumTextfield.getText(),
                            sealNumTextfield.getText(),
                            sizeChoiceBox.getValue().toString(),
                            statusTextfield.getText(),
                            packagesTextfield.getText(),
                            cbmContTextfield.getText(),
                            referenceTextfield.getText());
                    if (check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "The container details have been inserted", ButtonType.OK);
                        alert.showAndWait();
                        setNullContainerTextFields();
                        setNullContainerLabels();
                        containerNumTextfield.setText("");
                        containerTableView.getItems().clear();
                        containerTableView.refresh();
                        updateTable(referenceTextfield.getText(), connection);
                        containerTableView.setItems(container_table);

                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "The container details cannot be inserted", ButtonType.OK);
                        alert.showAndWait();
                    }
                } else {
                    boolean check = manifestModel.updateContainerDetails(
                            containerNumTextfield.getText(),
                            sealNumTextfield.getText(),
                            sizeChoiceBox.getValue().toString(),
                            statusTextfield.getText(),
                            packagesTextfield.getText(),
                            cbmContTextfield.getText(),
                            referenceTextfield.getText());

                    if (check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "The container details have been updated", ButtonType.OK);
                        alert.showAndWait();
                        setNullContainerTextFields();
                        setNullContainerLabels();
                        containerNumTextfield.setText("");
                        containerTableView.getItems().clear();
                        containerTableView.refresh();
                        updateTable(referenceTextfield.getText(), connection);
                        containerTableView.setItems(container_table);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "The container details cannot be updated", ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void addORUpdateManifestDetails(ActionEvent event) {
        if (referenceTextfield.getText().trim().isEmpty()) {
            referenceLabel.setText("* reference field empty");
        } else {
            referenceLabel.setText("");
        }

        if (mblTextField.getText().trim().isEmpty()) {
            mblNumberErrorLabel.setText("* mbl number field empty");
        } else {
            mblNumberErrorLabel.setText("");
        }
        if (hblNumberTextfield.getText().trim().isEmpty()) {
            hblErrorLabel.setText("* hbl number field empty");
        } else {
            hblErrorLabel.setText("");
        }
        if (masterShipperTextfield.getText().trim().isEmpty()) {
            masterShipperErrorLabel.setText("* master shipper name field empty");
        } else {
            masterShipperErrorLabel.setText("");
        }
        if (notifyPartyTextfield.getText().trim().isEmpty()) {
            notifyPartyErrorLabel.setText("* notify party name field empty");
        } else {
            notifyPartyErrorLabel.setText("");
        }
        if (consigneeTextfield.getText().trim().isEmpty()) {
            consigneeErrorLabel.setText("* consignee name field empty");
        } else {
            consigneeErrorLabel.setText("");
        }

        if (descriptionTextArea.getText().trim().isEmpty()) {
            descriptionErroLabel.setText("* description of cargo field empty");
        } else {
            descriptionErroLabel.setText("");
        }
        if (marksTextArea.getText().trim().isEmpty()) {
            marksErrorLabel.setText("* marks and numbers field empty");
        } else {
            marksErrorLabel.setText("");
        }
        if (grossWeightTextField.getText().trim().isEmpty()) {
            grossWeightErrorLabel.setText("* field empty");
        } else {
            grossWeightErrorLabel.setText("");
        }
        if (netWeightTextfield.getText().trim().isEmpty()) {
            netWeightErrorLabel.setText("* field empty");
        } else {
            netWeightErrorLabel.setText("");
        }
        if (cbmTextfield.getText().trim().isEmpty()) {
            cbmErrorLabel.setText("* cbm field empty");
        } else {
            cbmErrorLabel.setText("");
        }
        if (numberOfPackagesTextfield.getText().trim().isEmpty()) {
            numberOfPackagesErrorLabel.setText("* number of packages field empty");
        } else {
            numberOfPackagesErrorLabel.setText("");
        }
        if (packageTypeTextfield.getText().trim().isEmpty()) {
            packageTypeErrorLabel.setText("* package type field empty");
        } else {
            packageTypeErrorLabel.setText("");
        }

        if (doExpiryPicker.getValue() == null) {
            doExpiryErrorLabel.setText("* d/o expiry field empty");
        } else {
            doExpiryErrorLabel.setText("");
        }

        //FIXME: Do check to find containers for reference

        if (!referenceTextfield.getText().trim().isEmpty()
                && !masterShipperTextfield.getText().trim().isEmpty()
                && !notifyPartyTextfield.getText().trim().isEmpty()
                && !consigneeTextfield.getText().trim().isEmpty()
                && !mblTextField.getText().trim().isEmpty()
                && !hblNumberTextfield.getText().trim().isEmpty()
                && !descriptionTextArea.getText().trim().isEmpty()
                && !marksTextArea.getText().trim().isEmpty()
                && !grossWeightTextField.getText().trim().isEmpty()
                && !netWeightTextfield.getText().trim().isEmpty()
                && !cbmTextfield.getText().trim().isEmpty()
                && !numberOfPackagesTextfield.getText().trim().isEmpty()
                && !packageTypeTextfield.getText().trim().isEmpty()
                && doExpiryPicker.getValue() != null
        ) {
            if (!manifestModel.isManifestDetailsExist(referenceTextfield.getText())) {
                try {
                    boolean check = manifestModel.addManifestDetails(
                            fileNumberLabel.getText(),
                            systemReferenceLabel.getText(),
                            referenceTextfield.getText(),
                            masterShipperTextfield.getText(),
                            notifyPartyTextfield.getText(),
                            consigneeTextfield.getText(),
                            mblTextField.getText(),
                            hblNumberTextfield.getText(),
                            shipper_address.getValue().toString(),
                            notify_party_address.getValue().toString(),
                            consignee_address.getValue().toString(),
                            descriptionTextArea.getText(),
                            marksTextArea.getText(),
                            grossWeightTextField.getText(),
                            numberOfPackagesTextfield.getText(),
                            netWeightTextfield.getText(),
                            packageTypeTextfield.getText(),
                            doExpiryPicker.getValue().toString(),
                            cbmTextfield.getText(),
                            freightTextfield.getValue().toString()
                    );
                    if (check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "The manifest details have been inserted", ButtonType.OK);
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "The manifest details cannot be inserted", ButtonType.OK);
                        alert.showAndWait();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    boolean check = manifestModel.updateManifestDetails(
                            referenceTextfield.getText(),
                            masterShipperTextfield.getText(),
                            notifyPartyTextfield.getText(),
                            consigneeTextfield.getText(),
                            mblTextField.getText(),
                            hblNumberTextfield.getText(),
                            shipper_address.getValue().toString(),
                            notify_party_address.getValue().toString(),
                            consignee_address.getValue().toString(),
                            descriptionTextArea.getText(),
                            marksTextArea.getText(),
                            grossWeightTextField.getText(),
                            numberOfPackagesTextfield.getText(),
                            netWeightTextfield.getText(),
                            packageTypeTextfield.getText(),
                            doExpiryPicker.getValue().toString(),
                            cbmTextfield.getText(),
                            freightTextfield.getValue().toString()
                    );
                    if (check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "The manifests details have been updated", ButtonType.OK);
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "The manifests details cannot be updated", ButtonType.OK);
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void deleteContainerDetails(ActionEvent event) {
        try {
            if (manifestModel.isContainerDetailsExist(referenceTextfield.getText(), containerNumTextfield.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Do you want to delete? ", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = alert.showAndWait();
                if (!result.isPresent()) {
                    hideSource(event);
                } else if (result.get() == ButtonType.YES) {
                    manifestModel.deleteContainerDetails(containerNumTextfield.getText(), referenceTextfield.getText());
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Deleted container details ", ButtonType.OK);
                    alert1.showAndWait();
                    containerNumTextfield.setText("");
                    setNullContainerTextFields();
                    setNullContainerLabels();
                    containerTableView.getItems().clear();
                    containerTableView.refresh();
                    updateTable(referenceTextfield.getText(), connection);
                    containerTableView.setItems(container_table);
                } else if (result.get() == ButtonType.NO) {
                    hideSource(event);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The container details cannot be deleted", ButtonType.OK);
                alert.showAndWait();
                setNullContainerTextFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteManifestDetails(ActionEvent event) {
        try {
            if (manifestModel.isManifestDetailsExist(referenceTextfield.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Do you want to delete? ", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = alert.showAndWait();
                if (!result.isPresent()) {
                    hideSource(event);
                } else if (result.get() == ButtonType.YES) {
                    manifestModel.deleteManifestDetails(referenceTextfield.getText());
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Deleted manifest details ", ButtonType.OK);
                    alert1.showAndWait();
                    setNullManifestTextFields();
                    setNullContainerTextFields();
                } else if (result.get() == ButtonType.NO) {
                    hideSource(event);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The manifest details cannot be deleted", ButtonType.OK);
                alert.showAndWait();
                setNullManifestTextFields();
                setNullContainerTextFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateManifest(ActionEvent event) {
        if (referenceTextfield.getText().trim().isEmpty()) {
            referenceLabel.setText("* reference field empty");
        } else {
            referenceLabel.setText("");
        }

        if (mblTextField.getText().trim().isEmpty()) {
            mblNumberErrorLabel.setText("* mbl number field empty");
        } else {
            mblNumberErrorLabel.setText("");
        }
        if (hblNumberTextfield.getText().trim().isEmpty()) {
            hblErrorLabel.setText("* hbl number field empty");
        } else {
            hblErrorLabel.setText("");
        }
        if (masterShipperTextfield.getText().trim().isEmpty()) {
            masterShipperErrorLabel.setText("* master shipper name field empty");
        } else {
            masterShipperErrorLabel.setText("");
        }
        if (notifyPartyTextfield.getText().trim().isEmpty()) {
            notifyPartyErrorLabel.setText("* notify party name field empty");
        } else {
            notifyPartyErrorLabel.setText("");
        }
        if (consigneeTextfield.getText().trim().isEmpty()) {
            consigneeErrorLabel.setText("* consignee name field empty");
        } else {
            consigneeErrorLabel.setText("");
        }

        if (descriptionTextArea.getText().trim().isEmpty()) {
            descriptionErroLabel.setText("* description of cargo field empty");
        } else {
            descriptionErroLabel.setText("");
        }
        if (marksTextArea.getText().trim().isEmpty()) {
            marksErrorLabel.setText("* marks and numbers field empty");
        } else {
            marksErrorLabel.setText("");
        }

        if (grossWeightTextField.getText().trim().isEmpty()) {
            grossWeightErrorLabel.setText("* field empty");
        } else {
            grossWeightErrorLabel.setText("");
        }
        if (netWeightTextfield.getText().trim().isEmpty()) {
            netWeightErrorLabel.setText("* field empty");
        } else {
            netWeightErrorLabel.setText("");
        }

        if (cbmTextfield.getText().trim().isEmpty()) {
            cbmErrorLabel.setText("* cbm field empty");
        } else {
            cbmErrorLabel.setText("");
        }
        if (numberOfPackagesTextfield.getText().trim().isEmpty()) {
            numberOfPackagesErrorLabel.setText("* number of packages field empty");
        } else {
            numberOfPackagesErrorLabel.setText("");
        }
        if (packageTypeTextfield.getText().trim().isEmpty()) {
            packageTypeErrorLabel.setText("* package type field empty");
        } else {
            packageTypeErrorLabel.setText("");
        }

        if (doExpiryPicker.getValue() == null) {
            doExpiryErrorLabel.setText("* d/o expiry field empty");
        } else {
            doExpiryErrorLabel.setText("");
        }

        if (!referenceTextfield.getText().trim().isEmpty()
                && !masterShipperTextfield.getText().trim().isEmpty()
                && !notifyPartyTextfield.getText().trim().isEmpty()
                && !consigneeTextfield.getText().trim().isEmpty()
                && !mblTextField.getText().trim().isEmpty()
                && !hblNumberTextfield.getText().trim().isEmpty()
                && !descriptionTextArea.getText().trim().isEmpty()
                && !marksTextArea.getText().trim().isEmpty()
                && !grossWeightTextField.getText().trim().isEmpty()
                && !netWeightTextfield.getText().trim().isEmpty()
                && !cbmTextfield.getText().trim().isEmpty()
                && !numberOfPackagesTextfield.getText().trim().isEmpty()
                && !packageTypeTextfield.getText().trim().isEmpty()
                && doExpiryPicker.getValue() != null
        ) {
            CompanyProfileModel companyProfileModel = new CompanyProfileModel();
            companyProfileModel.getLatestCompanyDetails();
            ReportGenerator reportGenerator = new ReportGenerator();
            CargoDetailsModel.getCargoDetails(referenceTextfield.getText());
            LocalDate dateEta = converter.fromString(CargoDetailsModel.getDue_on());
            reportGenerator.GenerateManifestReport(
                    referenceTextfield.getText(),
                    CargoDetailsModel.getVessel() + " " + CargoDetailsModel.getVoyage(),
                    dateEta.toString(),
                    CargoDetailsModel.getPort_loading(),
                    CargoDetailsModel.getPort_discharge(),
                    CargoDetailsModel.getSerial_number(),
                    masterShipperTextfield.getText(),
                    notifyPartyTextfield.getText(),
                    consigneeTextfield.getText(),
                    fileNumberLabel.getText(),
                    companyProfileModel.getCompany_name(),
                    companyProfileModel.getCompany_address(),
                    companyProfileModel.getTelephone(),
                    hblNumberTextfield.getText(),
                    shipper_address.getValue(),
                    notify_party_address.getValue(),
                    consignee_address.getValue(),
                    marksTextArea.getText(),
                    descriptionTextArea.getText(),
                    grossWeightTextField.getText(),
                    netWeightTextfield.getText(),
                    cbmTextfield.getText(),
                    numberOfPackagesTextfield.getText(),
                    mblTextField.getText(),
                    freightTextfield.getValue()
            );
        }
    }

    public void GenerateDO(ActionEvent event) {
        if (referenceTextfield.getText().trim().isEmpty()) {
            referenceLabel.setText("* reference field empty");
        } else {
            referenceLabel.setText("");
        }

        if (mblTextField.getText().trim().isEmpty()) {
            mblNumberErrorLabel.setText("* mbl number field empty");
        } else {
            mblNumberErrorLabel.setText("");
        }
        if (hblNumberTextfield.getText().trim().isEmpty()) {
            hblErrorLabel.setText("* hbl number field empty");
        } else {
            hblErrorLabel.setText("");
        }
        if (masterShipperTextfield.getText().trim().isEmpty()) {
            masterShipperErrorLabel.setText("* master shipper name field empty");
        } else {
            masterShipperErrorLabel.setText("");
        }
        if (notifyPartyTextfield.getText().trim().isEmpty()) {
            notifyPartyErrorLabel.setText("* notify party name field empty");
        } else {
            notifyPartyErrorLabel.setText("");
        }
        if (consigneeTextfield.getText().trim().isEmpty()) {
            consigneeErrorLabel.setText("* consignee name field empty");
        } else {
            consigneeErrorLabel.setText("");
        }

        if (descriptionTextArea.getText().trim().isEmpty()) {
            descriptionErroLabel.setText("* description of cargo field empty");
        } else {
            descriptionErroLabel.setText("");
        }
        if (marksTextArea.getText().trim().isEmpty()) {
            marksErrorLabel.setText("* marks and numbers field empty");
        } else {
            marksErrorLabel.setText("");
        }
        if (grossWeightTextField.getText().trim().isEmpty()) {
            grossWeightErrorLabel.setText("* field empty");
        } else {
            grossWeightErrorLabel.setText("");
        }
        if (netWeightTextfield.getText().trim().isEmpty()) {
            netWeightErrorLabel.setText("* field empty");
        } else {
            netWeightErrorLabel.setText("");
        }
        if (cbmTextfield.getText().trim().isEmpty()) {
            cbmErrorLabel.setText("* field empty");
        } else {
            cbmErrorLabel.setText("");
        }
        if (numberOfPackagesTextfield.getText().trim().isEmpty()) {
            numberOfPackagesErrorLabel.setText("* number of packages field empty");
        } else {
            numberOfPackagesErrorLabel.setText("");
        }
        if (packageTypeTextfield.getText().trim().isEmpty()) {
            packageTypeErrorLabel.setText("* package type field empty");
        } else {
            packageTypeErrorLabel.setText("");
        }
        if (doExpiryPicker.getValue() == null) {
            doExpiryErrorLabel.setText("* d/o expiry field empty");
        } else {
            doExpiryErrorLabel.setText("");
        }

        //FIXME: Do check to find containers for reference

        if (!referenceTextfield.getText().trim().isEmpty()
                && !masterShipperTextfield.getText().trim().isEmpty()
                && !notifyPartyTextfield.getText().trim().isEmpty()
                && !consigneeTextfield.getText().trim().isEmpty()
                && !mblTextField.getText().trim().isEmpty()
                && !hblNumberTextfield.getText().trim().isEmpty()
                && !descriptionTextArea.getText().trim().isEmpty()
                && !marksTextArea.getText().trim().isEmpty()
                && !grossWeightTextField.getText().trim().isEmpty()
                && !netWeightTextfield.getText().trim().isEmpty()
                && !cbmTextfield.getText().trim().isEmpty()
                && !numberOfPackagesTextfield.getText().trim().isEmpty()
                && !packageTypeTextfield.getText().trim().isEmpty()
                && doExpiryPicker.getValue() != null
        ) {
            CompanyModel companyModel = new CompanyModel();
            CompanyProfileModel companyProfileModel = new CompanyProfileModel();
            CompanyProfileModel.getLatestCompanyDetails();
            CargoDetailsModel.getCargoDetails(referenceTextfield.getText());
            ReportGenerator reportGenerator = new ReportGenerator();
            LocalDate dateEta = converter.fromString(CargoDetailsModel.getDue_on());
            boolean isSelected = netWeightChoiceBox.isSelected();
            reportGenerator.GenerateDO(
                    referenceTextfield.getText(),
                    CargoDetailsModel.getVessel(),
                    CargoDetailsModel.getVoyage(),
                    dateEta.toString(),
                    CargoDetailsModel.getPort_loading(),
                    CargoDetailsModel.getPort_discharge(),
                    CargoDetailsModel.getSerial_number(),
                    masterShipperTextfield.getText(),
                    notifyPartyTextfield.getText(),
                    consigneeTextfield.getText(),
                    fileNumberLabel.getText(),
                    CompanyProfileModel.getCompany_name(),
                    CompanyProfileModel.getCompany_address(),
                    CompanyProfileModel.getTelephone(),
                    hblNumberTextfield.getText(),
                    shipper_address.getValue(),
                    notify_party_address.getValue(),
                    consignee_address.getValue(),
                    marksTextArea.getText(),
                    descriptionTextArea.getText(),
                    grossWeightTextField.getText(),
                    netWeightTextfield.getText(),
                    cbmTextfield.getText(),
                    numberOfPackagesTextfield.getText(),
                    mblTextField.getText(),
                    doExpiryPicker.getValue().toString(),
                    packageTypeTextfield.getText(),
                    freightTextfield.getValue().toString(),
                    isSelected
            );
        }
    }

    public String systemReference() {
        String id = ManifestModel.getID();
        String year = String.valueOf(Year.now().getValue());

        return "CMS/" + year + "/" + id;
    }


    public void updateTable(String reference, Connection connection1) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        if (manifestModel.isContainerDetailsExistForReference(reference)) {
            String query = "SELECT * FROM container_details WHERE job_reference = ?";
            try {
                preparedStatement = connection1.prepareStatement(query);
                preparedStatement.setString(1, reference);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    container_table.add(new Container(resultSet.getString("container_number"),
                            resultSet.getString("seal_number"), resultSet.getString("size"), resultSet.getString("status"),
                            resultSet.getString("packages"), resultSet.getString("cbm")));
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
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (manifestModel.idDBConnected()) {
            connection = manifestModel.connection();
//            logger.log("INFO", "Manifest.initialize.Database Connected");
            try {
                List<String> list1 = companyModel.getAllCompanies();
                TextFields.bindAutoCompletion(masterShipperTextfield, list1);
                TextFields.bindAutoCompletion(notifyPartyTextfield, list1);
                TextFields.bindAutoCompletion(consigneeTextfield, list1);
                List<String> list2 = manifestModel.getAllManifests();
                TextFields.bindAutoCompletion(referenceTextfield, list2);
                container_table.clear();
                fileNumberLabel.setText(manifestModel.getFileNumber());
                systemReferenceLabel.setText(systemReference());
                containerNumber.setCellValueFactory(new PropertyValueFactory<>("ContainerNumber"));
                sealNumber.setCellValueFactory(new PropertyValueFactory<>("SealNumber"));
                sizeList.setCellValueFactory(new PropertyValueFactory<>("Size"));
                statusList.setCellValueFactory(new PropertyValueFactory<>("Status"));
                packages.setCellValueFactory(new PropertyValueFactory<>("Packages"));
                cbmL.setCellValueFactory(new PropertyValueFactory<>("Cbm"));
                sizeChoiceBox.getItems().addAll("20", "40", "OVER 40");
                sizeChoiceBox.setValue("20");
                freightTextfield.getItems().addAll("PREPAID", "COLLECT");
                freightTextfield.setValue("PREPAID");
                referenceTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
                    List<String> list3 = manifestModel.getAllContainers(newValue);
                    TextFields.bindAutoCompletion(containerNumTextfield, list3);
                    if (newValue.equals("")) {
                        setNullManifestLabels();
                        setNullManifestTextFields();
                        setNullContainerLabels();
                        setNullContainerTextFields();
                        containerTableView.getItems().clear();
                        containerTableView.refresh();
                    } else if (manifestModel.isManifestDetailsExist(newValue)) {
                        manifestModel.getManifestDetails(newValue);
                        fileNumberLabel.setText(ManifestModel.getFile_number());
                        systemReferenceLabel.setText(ManifestModel.getSystem_reference());
                        mblTextField.setText(ManifestModel.getMbl_number());
                        masterShipperTextfield.setText(ManifestModel.getMaster_shipper());
                        notifyPartyTextfield.setText(ManifestModel.getNotify_party());
                        consigneeTextfield.setText(ManifestModel.getConsignee());
                        hblNumberTextfield.setText(ManifestModel.getHbl_number());
                        shipper_address.setValue(ManifestModel.getShipperAddress());
                        notify_party_address.setValue(ManifestModel.getNotify_address());
                        consignee_address.setValue(ManifestModel.getConsignee_address());
                        descriptionTextArea.setText(ManifestModel.getDescription_of_cargo());
                        marksTextArea.setText(ManifestModel.getMarks_numbers());
                        grossWeightTextField.setText(ManifestModel.getGross_weight());
                        numberOfPackagesTextfield.setText(ManifestModel.getNumber_packages());
                        netWeightTextfield.setText(ManifestModel.getNet_weight());
                        packageTypeTextfield.setText(ManifestModel.getPackage_type());
                        cbmTextfield.setText(ManifestModel.getCbm());
                        freightTextfield.setValue(ManifestModel.getFreight());
                        LocalDate doExpiry = converter.fromString(ManifestModel.getDo_expiry());
                        doExpiryPicker.setConverter(converter);
                        doExpiryPicker.setValue(doExpiry);
                        containerTableView.getItems().clear();
                        containerTableView.refresh();
                        updateTable(newValue, connection);
                        containerTableView.setItems(container_table);
                    } else if (cargoDetailsModel.isExistCargoDetails(newValue)) {
                        CargoDetailsModel.getCargoDetails(newValue);
                        containerTableView.getItems().clear();
                        containerTableView.refresh();
                        updateTable(newValue, connection);
                        containerTableView.setItems(container_table);
//                        setNullContainerTextFields();
//                        setNullContainerTextFields();
//                        setNullManifestTextFields();
//                        setNullManifestLabels();
                    } else {
                        containerTableView.getItems().clear();
                        containerTableView.refresh();
                        updateTable(newValue, connection);
                        containerTableView.setItems(container_table);
                        setNullContainerTextFields();
                        setNullContainerLabels();
                        setNullManifestTextFields();
                        setNullManifestLabels();
                    }
                });
                containerNumTextfield.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1.equals("")) {
                        setNullContainerLabels();
                        setNullContainerTextFields();
                    } else if (manifestModel.isContainerDetailsExist(referenceTextfield.getText(), newValue1)) {
//                        System.out.println(referenceTextfield.getText());
                        manifestModel.getContainerDetails(referenceTextfield.getText(), newValue1);
                        containerNumTextfield.setText(ManifestModel.getContainer_number());
                        sealNumTextfield.setText(ManifestModel.getSeal_number());
                        sizeChoiceBox.setValue(ManifestModel.getSize());
                        statusTextfield.setText(ManifestModel.getStatus());
                        packagesTextfield.setText(ManifestModel.getPackages());
                        cbmContTextfield.setText(ManifestModel.getCbmContainer());
                        setNullContainerLabels();
                    } else {
                        setNullContainerLabels();
                        setNullContainerTextFields();
                    }
                });
                masterShipperTextfield.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1.equals("")) {
                        shipper_address.setValue("");
                    }else if (companyModel.isExistCompany(newValue1)) {
//                        System.out.println(referenceTextfield.getText());
                        List<String> address1 = companyModel.getAllCompaniesAddresses(newValue1);
                        shipper_address.getItems().addAll(address1);
                        shipper_address.setValue(address1.get(0));
                    }else {
                        shipper_address.setValue("");

                    }
                });
                notifyPartyTextfield.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1.equals("")) {
                        notify_party_address.setValue("");
                    } else if (companyModel.isExistCompany(newValue1)) {
//                        System.out.println(referenceTextfield.getText());
                        List<String> address2 = companyModel.getAllCompaniesAddresses(newValue1);
                        notify_party_address.getItems().addAll(address2);
                        notify_party_address.setValue(address2.get(0));
                    } else {
                        notify_party_address.setValue("");

                    }
                });

                consigneeTextfield.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1.equals("")) {
                        consignee_address.setValue("");
                    } else if (companyModel.isExistCompany(newValue1)) {
                        List<String> address3 = companyModel.getAllCompaniesAddresses(newValue1);
                        consignee_address.getItems().addAll(address3);
                        consignee_address.setValue(address3.get(0));
                    } else {
                        consignee_address.setValue("");
                    }
                });


            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
//            logger.log("SEVERE", "Manifest.initialize.Database not Connected");
        }
    }

    private void hideSource(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    private void setNullManifestLabels() {
        referenceLabel.setText("");
        cbmErrorLabel.setText("");
        doExpiryErrorLabel.setText("");
        packageTypeErrorLabel.setText("");
        netWeightErrorLabel.setText("");
        numberOfPackagesErrorLabel.setText("");
        grossWeightErrorLabel.setText("");
        descriptionErroLabel.setText("");
        consigneeErrorLabel.setText("");
        notifyPartyErrorLabel.setText("");
        hblErrorLabel.setText("");
        mblNumberErrorLabel.setText("");
        masterShipperErrorLabel.setText("");
        marksErrorLabel.setText("");
        freightErrorLabel.setText("");
    }

    private void setNullContainerLabels() {
        cbmConatinErrorLabel.setText("");
        packagesErrorLabel.setText("");
        statusErrorLabel.setText("");
        sizeErrorLabel.setText("");
        sealNumberErrorLabel.setText("");
        containerNumberErrorLabel.setText("");
    }

    private void setNullContainerTextFields() {
//        containerNumTextfield.setText("");
        sealNumTextfield.setText("");
        statusTextfield.setText("");
        packagesTextfield.setText("");
        cbmContTextfield.setText("");
    }

    private void setNullManifestTextFields() {
        try {
            fileNumberLabel.setText(manifestModel.getFileNumber());
            systemReferenceLabel.setText(systemReference());
            mblTextField.setText("");
            masterShipperTextfield.setText("");
            consigneeTextfield.setText("");
            notifyPartyTextfield.setText("");
            hblNumberTextfield.setText("");
            descriptionTextArea.setText("");
            marksTextArea.setText("");
            grossWeightTextField.setText("");
            numberOfPackagesTextfield.setText("");
            netWeightTextfield.setText("");
            packageTypeTextfield.setText("");
            cbmTextfield.setText("");
            doExpiryPicker.setValue(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
