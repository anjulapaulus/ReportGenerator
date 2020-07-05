package com.codebuddy.controllers;

import com.codebuddy.LoggerInterface;
import com.codebuddy.ReportGenerator;
import com.codebuddy.models.CargoDetailsModel;
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
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ManifestDetails implements Initializable {
    @FXML Label fileNumberLabel;
    @FXML Label systemReferenceLabel;
    @FXML TextField referenceTextfield;
    @FXML TextField vesselVoyageTextfield;
    @FXML TextField portOfDischargeTextfield;
    @FXML TextField mblTextField;
    @FXML TextField masterShipperTextfield;
    @FXML TextField portOfLoadingTextfield;
    @FXML TextField serialNumberTextfield;
    @FXML TextField hblNumberTextfield;
    @FXML DatePicker etaPicker;
    @FXML TextArea shipperTextArea;
    @FXML TextArea notifyPartyTextarea;
    @FXML TextArea consigneeTextArea;
    @FXML TextArea descriptionTextArea;
    @FXML TextArea marksTextArea;
    @FXML TextArea remarksTextarea;
    @FXML TextField grossWeightTextField;
    @FXML TextField numberOfPackagesTextfield;
    @FXML TextField netWeightTextfield;
    @FXML TextField packageTypeTextfield;
    @FXML TextField unitTextfield;
    @FXML TextField cbmTextfield;
    @FXML TextField freightTextfield;
    @FXML DatePicker doExpiryPicker;
    //Labels
    @FXML Label referenceLabel;
    @FXML Label freightErrorLabel;
    @FXML Label cbmErrorLabel;
    @FXML Label doExpiryErrorLabel;
    @FXML Label unitErrorLabel;
    @FXML Label packageTypeErrorLabel;
    @FXML Label netWeightErrorLabel;
    @FXML Label numberOfPackagesErrorLabel;
    @FXML Label grossWeightErrorLabel;
    @FXML Label remarksErrorLabel;
    @FXML Label descriptionErroLabel;
    @FXML Label consigneeErrorLabel;
    @FXML Label notifyPartyErrorLabel;
    @FXML Label shipperErrorLabel;
    @FXML Label hblErrorLabel;
    @FXML Label mblNumberErrorLabel;
    @FXML Label masterShipperErrorLabel;
    @FXML Label serialNumberErrorLabel;
    @FXML Label etaErrorLabel;
    @FXML Label marksErrorLabel;
    @FXML Label vesselVoyageErrorLabel;
    @FXML Label portOfLoadingErrorLabel;
    @FXML Label portOfDischargeErrorLabel;



    //Container Details
    @FXML TextField containerNumTextfield;
    @FXML TextField sealNumTextfield;
    @FXML TextField sizeTextfield;
    @FXML TextField statusTextfield;
    @FXML TextField packagesTextfield;
    @FXML TextField cbmContTextfield;
    //Labels
    @FXML Label cbmConatinErrorLabel;
    @FXML Label packagesErrorLabel;
    @FXML Label statusErrorLabel;
    @FXML Label sizeErrorLabel;
    @FXML Label sealNumberErrorLabel;
    @FXML Label containerNumberErrorLabel;

    //Table view
    @FXML TableView<Container> containerTableView;
    @FXML TableColumn<Container,String> containerNumber;
    @FXML TableColumn<Container,String> sealNumber;
    @FXML TableColumn<Container,String> sizeList;
    @FXML TableColumn<Container,String> statusList;
    @FXML TableColumn<Container,String> packages;
    @FXML TableColumn<Container,String> cbmL;

    public static ObservableList<Container> container_table = FXCollections.observableArrayList();

    Connection connection = null;
    public ManifestDetails(){}

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

    public ManifestModel manifestModel = new ManifestModel();
    public CargoDetailsModel cargoDetailsModel =  new CargoDetailsModel();
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
        if (sizeTextfield.getText().trim().isEmpty()) {
            sizeErrorLabel.setText("* size empty");
        } else {
            sizeErrorLabel.setText("");
        }
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
                && !sizeTextfield.getText().trim().isEmpty()
                && !statusTextfield.getText().trim().isEmpty()
                && !packagesTextfield.getText().trim().isEmpty()
                && !cbmContTextfield.getText().trim().isEmpty()
        ) {
            try {
                if (!manifestModel.isContainerDetailsExist(referenceTextfield.getText(),containerNumTextfield.getText())) {
                    boolean check = manifestModel.addContainerDetails(
                            containerNumTextfield.getText(),
                            sealNumTextfield.getText(),
                            sizeTextfield.getText(),
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
                        updateTable(referenceTextfield.getText(),connection);
                        containerTableView.setItems(container_table);

                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "The container details cannot be inserted", ButtonType.OK);
                        alert.showAndWait();
                    }
                } else {
                    boolean check = manifestModel.updateContainerDetails(
                            containerNumTextfield.getText(),
                            sealNumTextfield.getText(),
                            sizeTextfield.getText(),
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
                        updateTable(referenceTextfield.getText(),connection);
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

    public void addORUpdateManifestDetails(ActionEvent event){
        if (referenceTextfield.getText().trim().isEmpty()) {
            referenceLabel.setText("* reference field empty");
        } else {
            referenceLabel.setText("");
        }
        if (vesselVoyageTextfield.getText().trim().isEmpty()) {
            vesselVoyageErrorLabel.setText("* vessel voyage field empty");
        } else {
            vesselVoyageErrorLabel.setText("");
        }
        if (portOfLoadingTextfield.getText().trim().isEmpty()) {
            portOfLoadingErrorLabel.setText("* port of loading field empty");
        } else {
            portOfLoadingErrorLabel.setText("");
        }
        if (portOfDischargeTextfield.getText().trim().isEmpty()) {
            portOfDischargeErrorLabel.setText("* port of discharge field empty");
        } else {
            portOfDischargeErrorLabel.setText("");
        }
        if (etaPicker.getValue() == null) {
            etaErrorLabel.setText("* eta field empty");
        } else {
            etaErrorLabel.setText("");
        }
        if (serialNumberTextfield.getText().trim().isEmpty()) {
            serialNumberErrorLabel.setText("* serial number field empty");
        } else {
            serialNumberErrorLabel.setText("");
        }
        if (masterShipperTextfield.getText().trim().isEmpty()) {
            masterShipperErrorLabel.setText("* master shipper field empty");
        } else {
            masterShipperErrorLabel.setText("");
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

        if (shipperTextArea.getText().trim().isEmpty()) {
            shipperErrorLabel.setText("* shipper field empty");
        } else {
            shipperErrorLabel.setText("");
        }
        if (notifyPartyTextarea.getText().trim().isEmpty()) {
            notifyPartyErrorLabel.setText("* notify party field empty");
        } else {
            notifyPartyErrorLabel.setText("");
        }
        if (consigneeTextArea.getText().trim().isEmpty()) {
            consigneeErrorLabel.setText("* consignee field empty");
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
        if (remarksTextarea.getText().trim().isEmpty()) {
            remarksErrorLabel.setText("* remarks field empty");
        } else {
            remarksErrorLabel.setText("");
        }
        if (grossWeightTextField.getText().trim().isEmpty()) {
            grossWeightErrorLabel.setText("* gross weight field empty");
        } else {
            grossWeightErrorLabel.setText("");
        }
        if (netWeightTextfield.getText().trim().isEmpty()) {
            netWeightErrorLabel.setText("* net weight field empty");
        } else {
            netWeightErrorLabel.setText("");
        }
        if (unitTextfield.getText().trim().isEmpty()) {
            unitErrorLabel.setText("* unit field empty");
        } else {
            unitErrorLabel.setText("");
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
        if (freightTextfield.getText().trim().isEmpty()) {
            freightErrorLabel.setText("* freight field empty");
        } else {
            freightErrorLabel.setText("");
        }

        if (doExpiryPicker.getValue() == null) {
            doExpiryErrorLabel.setText("* d/o expiry field empty");
        } else {
            doExpiryErrorLabel.setText("");
        }

        //FIXME: Do check to find containers for reference

        if (!referenceTextfield.getText().trim().isEmpty()
                && !vesselVoyageTextfield.getText().trim().isEmpty()
                && !portOfLoadingTextfield.getText().trim().isEmpty()
                && !portOfDischargeTextfield.getText().trim().isEmpty()
                && etaPicker.getValue() != null
                && !serialNumberTextfield.getText().trim().isEmpty()
                && !masterShipperTextfield.getText().trim().isEmpty()
                && !mblTextField.getText().trim().isEmpty()
                && !hblNumberTextfield.getText().trim().isEmpty()
                && !shipperTextArea.getText().trim().isEmpty()
                && !notifyPartyTextarea.getText().trim().isEmpty()
                && !consigneeTextArea.getText().trim().isEmpty()
                && !descriptionTextArea.getText().trim().isEmpty()
                && !marksTextArea.getText().trim().isEmpty()
                && !remarksTextarea.getText().trim().isEmpty()
                && !grossWeightTextField.getText().trim().isEmpty()
                && !netWeightTextfield.getText().trim().isEmpty()
                && !unitTextfield.getText().trim().isEmpty()
                && !cbmTextfield.getText().trim().isEmpty()
                && !numberOfPackagesTextfield.getText().trim().isEmpty()
                && !packageTypeTextfield.getText().trim().isEmpty()
                && !freightTextfield.getText().trim().isEmpty()
                & doExpiryPicker.getValue() != null
        ) {
            if (!manifestModel.isManifestDetailsExist(referenceTextfield.getText())){
                try {
                    boolean check = manifestModel.addManifestDetails(
                            fileNumberLabel.getText(),
                            systemReferenceLabel.getText(),
                            referenceTextfield.getText(),
                            vesselVoyageTextfield.getText(),
                            portOfLoadingTextfield.getText(),
                            portOfDischargeTextfield.getText(),
                            etaPicker.getValue().toString(),
                            serialNumberTextfield.getText(),
                            masterShipperTextfield.getText(),
                            mblTextField.getText(),
                            hblNumberTextfield.getText(),
                            shipperTextArea.getText(),
                            notifyPartyTextarea.getText(),
                            consigneeTextArea.getText(),
                            descriptionTextArea.getText(),
                            marksTextArea.getText(),
                            remarksTextarea.getText(),
                            grossWeightTextField.getText(),
                            numberOfPackagesTextfield.getText(),
                            netWeightTextfield.getText(),
                            packageTypeTextfield.getText(),
                            unitTextfield.getText(),
                            doExpiryPicker.getValue().toString(),
                            cbmTextfield.getText(),
                            freightTextfield.getText()
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
            }else {
                try {
                    boolean check = manifestModel.updateManifestDetails(
                            referenceTextfield.getText(),
                            vesselVoyageTextfield.getText(),
                            portOfLoadingTextfield.getText(),
                            portOfDischargeTextfield.getText(),
                            etaPicker.getValue().toString(),
                            serialNumberTextfield.getText(),
                            masterShipperTextfield.getText(),
                            mblTextField.getText(),
                            hblNumberTextfield.getText(),
                            shipperTextArea.getText(),
                            notifyPartyTextarea.getText(),
                            consigneeTextArea.getText(),
                            descriptionTextArea.getText(),
                            marksTextArea.getText(),
                            remarksTextarea.getText(),
                            grossWeightTextField.getText(),
                            numberOfPackagesTextfield.getText(),
                            netWeightTextfield.getText(),
                            packageTypeTextfield.getText(),
                            unitTextfield.getText(),
                            doExpiryPicker.getValue().toString(),
                            cbmTextfield.getText(),
                            freightTextfield.getText()
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

    public void deleteContainerDetails(ActionEvent event){
        try {
            if (manifestModel.isContainerDetailsExist(referenceTextfield.getText(),containerNumTextfield.getText())) {
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
                    updateTable(referenceTextfield.getText() ,connection);
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

    public void deleteManifestDetails(ActionEvent event){
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

    public void generateManifest(ActionEvent event){
        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.GenerateManifestReport();
    }

    public String systemReference(){
        String id = ManifestModel.getID();
        String year = String.valueOf(Year.now().getValue());

        return "CMS/"+year+"/"+id;
    }

    public void insertToTable(String containerNum, String sealNum,String size,String status, String packages, String cbm){
         Container container = new Container(containerNum,sealNum,size,status,packages,cbm);
         containerTableView.getItems().add(container);
    }

//    public List<List<String>> getDataFromTable(){
//        Container container = new Container();
//        List<List<String>> arrList = new ArrayList<>();
//
//        for (int i = 0; i < containerTableView.getItems().size(); i++){
//            container = containerTableView.getItems().get(i);
//            arrList.add(new ArrayList<>());
//            arrList.get(i).add(container.containerNumber);
//            arrList.get(i).add(container.sealNumber);
//            arrList.get(i).add(container.size);
//            arrList.get(i).add(container.status);
//            arrList.get(i).add(container.packages);
//            arrList.get(i).add(container.cbm);
//        }

//        for (int i = 0; i < arrList.size(); i++){
//            for (int j = 0; j < arrList.get(i).size(); j++){
//                System.out.println(arrList.get(i).get(j));
//            }
//        }
//        return arrList;
//    }

    public void updateTable(String reference, Connection connection1){
        PreparedStatement preparedStatement=null;
        ResultSet resultSet = null;
        if (manifestModel.isContainerDetailsExistForReference(reference)){
//            manifestModel.getContainerDetailsForReference(reference);
//            container_table.add(new Container(ManifestModel.getContainer_number(),
//                    ManifestModel.getSeal_number(), ManifestModel.getSize(), ManifestModel.getStatus(),
//                    ManifestModel.getPackages(), ManifestModel.getCbmContainer()));
//                containerTableView.setItems(container_table);
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
            connection =  manifestModel.connection();
            logger.log("INFO", "Manifest.initialize.Database Connected");
            try {
                fileNumberLabel.setText(manifestModel.getFileNumber());
                systemReferenceLabel.setText(systemReference());
                containerNumber.setCellValueFactory(new PropertyValueFactory<>("ContainerNumber"));
                sealNumber.setCellValueFactory(new PropertyValueFactory<>("SealNumber"));
                sizeList.setCellValueFactory(new PropertyValueFactory<>("Size"));
                statusList.setCellValueFactory(new PropertyValueFactory<>("Status"));
                packages.setCellValueFactory(new PropertyValueFactory<>("Packages"));
                cbmL.setCellValueFactory(new PropertyValueFactory<>("Cbm"));
                referenceTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
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
                        vesselVoyageTextfield.setText(ManifestModel.getVessel_voyage());
                        portOfDischargeTextfield.setText(ManifestModel.getPort_discharge());
                        mblTextField.setText(ManifestModel.getMbl_number());
                        masterShipperTextfield.setText(ManifestModel.getMaster_shipper());
                        portOfLoadingTextfield.setText(ManifestModel.getPort_loading());
                        serialNumberTextfield.setText(ManifestModel.getSerial_number());
                        hblNumberTextfield.setText(ManifestModel.getHbl_number());
                        LocalDate dateEta = (LocalDate) converter.fromString(ManifestModel.getEta());
                        etaPicker.setConverter(converter);
                        etaPicker.setValue(dateEta);
                        shipperTextArea.setText(ManifestModel.getShipper());
                        notifyPartyTextarea.setText(ManifestModel.getNotify_party());
                        consigneeTextArea.setText(ManifestModel.getConsignee());
                        descriptionTextArea.setText(ManifestModel.getDescription_of_cargo());
                        marksTextArea.setText(ManifestModel.getMarks_numbers());
                        remarksTextarea.setText(ManifestModel.getRemarks());
                        grossWeightTextField.setText(ManifestModel.getGross_weight());
                        numberOfPackagesTextfield.setText(ManifestModel.getNumber_packages());
                        netWeightTextfield.setText(ManifestModel.getNet_weight());
                        packageTypeTextfield.setText(ManifestModel.getPackage_type());
                        unitTextfield.setText(ManifestModel.getUnit());
                        cbmTextfield.setText(ManifestModel.getCbm());
                        freightTextfield.setText(ManifestModel.getFreight());
                        LocalDate doExpiry = (LocalDate) converter.fromString(ManifestModel.getDo_expiry());
                        doExpiryPicker.setConverter(converter);
                        doExpiryPicker.setValue(doExpiry);
                        containerTableView.getItems().clear();
                        containerTableView.refresh();
                        updateTable(newValue,connection);
                        containerTableView.setItems(container_table);
                    }else if (cargoDetailsModel.isExistCargoDetails(newValue)) {
                        CargoDetailsModel.getCargoDetails(newValue);
                        vesselVoyageTextfield.setText(CargoDetailsModel.getVessel()+"-"+CargoDetailsModel.getVoyage());
                        portOfDischargeTextfield.setText(CargoDetailsModel.getPort_discharge());
                        portOfLoadingTextfield.setText(CargoDetailsModel.getPort_loading());
                        serialNumberTextfield.setText(CargoDetailsModel.getSerial_number());
                        LocalDate dateEta = (LocalDate) converter.fromString(CargoDetailsModel.getDue_on());
                        etaPicker.setConverter(converter);
                        etaPicker.setValue(dateEta);
                        containerTableView.getItems().clear();
                        containerTableView.refresh();
                        updateTable(newValue,connection);
                        containerTableView.setItems(container_table);
//                        setNullContainerTextFields();
//                        setNullContainerTextFields();
//                        setNullManifestTextFields();
//                        setNullManifestLabels();
                    }else {
                        containerTableView.getItems().clear();
                        containerTableView.refresh();
                        updateTable(newValue,connection);
                        containerTableView.setItems(container_table);
                        setNullContainerTextFields();
                        setNullContainerTextFields();
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
                        manifestModel.getContainerDetails(referenceTextfield.getText(),newValue1);
                        containerNumTextfield.setText(ManifestModel.getContainer_number());
                        sealNumTextfield.setText(ManifestModel.getSeal_number());
                        sizeTextfield.setText(ManifestModel.getSize());
                        statusTextfield.setText(ManifestModel.getStatus());
                        packagesTextfield.setText(ManifestModel.getPackages());
                        cbmContTextfield.setText(ManifestModel.getCbmContainer());
                        setNullContainerLabels();
                    }else {
                        setNullContainerLabels();
                        setNullContainerTextFields();
                    }
                });
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            logger.log("SEVERE", "Manifest.initialize.Database not Connected");
        }
    }

    private void hideSource(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    private void setNullManifestLabels(){
        cbmErrorLabel.setText("");
        doExpiryErrorLabel.setText("");
        unitErrorLabel.setText("");
        packageTypeErrorLabel.setText("");
        netWeightErrorLabel.setText("");
        numberOfPackagesErrorLabel.setText("");
        grossWeightErrorLabel.setText("");
        remarksErrorLabel.setText("");
        descriptionErroLabel.setText("");
        consigneeErrorLabel.setText("");
        notifyPartyErrorLabel.setText("");
        shipperErrorLabel.setText("");
        hblErrorLabel.setText("");
        mblNumberErrorLabel.setText("");
        masterShipperErrorLabel.setText("");
        serialNumberErrorLabel.setText("");
        etaErrorLabel.setText("");
        marksErrorLabel.setText("");
        vesselVoyageErrorLabel.setText("");
        portOfLoadingErrorLabel.setText("");
        portOfDischargeErrorLabel.setText("");
    }

    private void setNullContainerLabels(){
        cbmConatinErrorLabel.setText("");
        packagesErrorLabel.setText("");
        statusErrorLabel.setText("");
        sizeErrorLabel.setText("");
        sealNumberErrorLabel.setText("");
        containerNumberErrorLabel.setText("");
    }

    private void setNullContainerTextFields(){
//        containerNumTextfield.setText("");
        sealNumTextfield.setText("");
        sizeTextfield.setText("");
        statusTextfield.setText("");
        packagesTextfield.setText("");
        cbmContTextfield.setText("");
    }

    private void setNullManifestTextFields(){
        try {
            fileNumberLabel.setText(manifestModel.getFileNumber());
            systemReferenceLabel.setText(systemReference());
            vesselVoyageTextfield.setText("");
            portOfDischargeTextfield.setText("");
            mblTextField.setText("");
            masterShipperTextfield.setText("");
            portOfLoadingTextfield.setText("");
            serialNumberTextfield.setText("");
            hblNumberTextfield.setText("");
            etaPicker.setValue(null);
            shipperTextArea.setText("");
            notifyPartyTextarea.setText("");
            consigneeTextArea.setText("");
            descriptionTextArea.setText("");
            marksTextArea.setText("");
            remarksTextarea.setText("");
            grossWeightTextField.setText("");
            numberOfPackagesTextfield.setText("");
            netWeightTextfield.setText("");
            packageTypeTextfield.setText("");
            unitTextfield.setText("");
            cbmTextfield.setText("");
            freightTextfield.setText("");
            doExpiryPicker.setValue(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}