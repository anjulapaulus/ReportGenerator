package com.codebuddy.models;

import com.codebuddy.SqliteConnection;
import com.codebuddy.controllers.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManifestModel {
    private static Connection connection;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    //variables
    private static String file_number;
    private static String system_reference;
//    private static String job_reference;
    private static String master_shipper;
    private static String notify_party;
    private static String consignee;
    private static String mbl_number;
    private static String hbl_number;
    private static String shipperAddress;
    private static String notify_address;
    private static String consignee_address;
    private static String description_of_cargo;
    private static String marks_numbers;
    private static String gross_weight;
    private static String number_packages;
    private static String net_weight;
    private static String package_type;
    private static String do_expiry;
    private static String cbm;
    private static String freight;

    //container variables
    private static String container_number;
    private static String seal_number;
    private static String size;
    private static String status;
    private static String packages;
    private static String cbmContainer;

    public ManifestModel() {
        connection = SqliteConnection.connection();
        if (connection == null) {
            System.exit(1);
        }
    }

    public boolean idDBConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection connection(){
        return connection;
    }

    public boolean addContainerDetails(String container_number, String seal_number,String size,String status,String packages,String cbm,String reference) throws SQLException {
        String query = "INSERT INTO container_details(job_reference, container_number, seal_number, size, status, packages, cbm)" +
                " VALUES(?,?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, reference);
            preparedStatement.setString(2, container_number);
            preparedStatement.setString(3, seal_number);
            preparedStatement.setString(4, size);
            preparedStatement.setString(5, status);
            preparedStatement.setString(6, packages);
            preparedStatement.setString(7, cbm);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            preparedStatement.close();
        }
    }

    public boolean updateContainerDetails(String container_number, String seal_number,String size,String status,String packages,String cbm,String reference) throws SQLException {
        String query = "UPDATE container_details SET container_number = ?, seal_number = ?, size = ?, status = ?, packages = ?, cbm = ? " +
                "WHERE job_reference = ? AND container_number = ? ";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, container_number);
            preparedStatement.setString(2, seal_number);
            preparedStatement.setString(3, size);
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, packages);
            preparedStatement.setString(6, cbm);
            preparedStatement.setString(7, reference);
            preparedStatement.setString(8, container_number);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            preparedStatement.close();
        }
    }

    public boolean isContainerDetailsExist(String job_reference, String container_number){
        String query = "SELECT * FROM container_details WHERE job_reference = ? AND container_number = ? ";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, job_reference);
            preparedStatement.setString(2, container_number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
            else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isContainerDetailsExistForReference(String job_reference){
        String query = "SELECT * FROM container_details WHERE job_reference = ? ";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, job_reference);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
            else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isManifestDetailsExist(String job_reference){
        String query = "SELECT * FROM manifest_details WHERE job_reference = ? ";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, job_reference);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
            else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean addManifestDetails(String file_number, String system_reference, String job_reference,  String masterShipper,String notify_party, String consignee, String mbl_number, String hbl_number, String shipperAddress, String notify_party_address, String consignee_address, String descriptionOfCargo, String marks_numbers, String gross_weight, String number_packages,String net_weight,String package_type,String do_expiry,String cbm,String freight) throws SQLException {
        String query = "INSERT INTO manifest_details(file_number, system_reference, job_reference, masterShipper,notify_party,consignee, mbl_number, hbl_number, shipper_address, notify_party_address, consignee_address, descriptionOfCargo, marks_numbers, gross_weight, number_packages, net_weight, package_type,  do_expiry, cbm, freight)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, file_number);
            preparedStatement.setString(2, system_reference);
            preparedStatement.setString(3, job_reference);
            preparedStatement.setString(4, masterShipper);
            preparedStatement.setString(5, notify_party);
            preparedStatement.setString(6, consignee);
            preparedStatement.setString(7, mbl_number);
            preparedStatement.setString(8, hbl_number);
            preparedStatement.setString(9, shipperAddress);
            preparedStatement.setString(10, notify_party_address);
            preparedStatement.setString(11, consignee_address);
            preparedStatement.setString(12, descriptionOfCargo);
            preparedStatement.setString(13, marks_numbers);
            preparedStatement.setString(14, gross_weight);
            preparedStatement.setString(15, number_packages);
            preparedStatement.setString(16, net_weight);
            preparedStatement.setString(17, package_type);
            preparedStatement.setString(18, do_expiry);
            preparedStatement.setString(19, cbm);
            preparedStatement.setString(20, freight);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            preparedStatement.close();
        }
    }

    public boolean updateManifestDetails(String job_reference, String masterShipper,String notify_party, String consignee, String mbl_number, String hbl_number, String shipperAddress, String notify_party_address, String consignee_address, String descriptionOfCargo, String marks_numbers, String gross_weight, String number_packages,String net_weight,String package_type,String do_expiry,String cbm,String freight) throws SQLException {
        String query = "UPDATE manifest_details SET masterShipper = ? ,notify_party = ?, consignee = ?," +
                "mbl_number = ?, hbl_number  = ?, shipper_address = ?, notify_party_address = ?, consignee_address = ?, descriptionOfCargo = ?," +
                "marks_numbers = ?,gross_weight = ?, number_packages = ?, net_weight = ?, package_type = ?," +
                "do_expiry  = ?, cbm = ?, freight = ? " +
                "WHERE job_reference = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, masterShipper);
            preparedStatement.setString(2, notify_party);
            preparedStatement.setString(3, consignee);
            preparedStatement.setString(4, mbl_number);
            preparedStatement.setString(5, hbl_number);
            preparedStatement.setString(6, shipperAddress);
            preparedStatement.setString(7, notify_party_address);
            preparedStatement.setString(8, consignee_address);
            preparedStatement.setString(9, descriptionOfCargo);
            preparedStatement.setString(10, marks_numbers);
            preparedStatement.setString(11, gross_weight);
            preparedStatement.setString(12, number_packages);
            preparedStatement.setString(13, net_weight);
            preparedStatement.setString(14, package_type);
            preparedStatement.setString(15, do_expiry);
            preparedStatement.setString(16, cbm);
            preparedStatement.setString(17, freight);
            preparedStatement.setString(18, job_reference);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            preparedStatement.close();
        }
    }

    public String getFileNumber() throws SQLException {
        String query = "SELECT file_number FROM company ORDER BY id DESC LIMIT 1";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String fileNumber = resultSet.getString("file_number");
                return fileNumber;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public boolean getManifestDetails(String reference){
        String query = "SELECT * FROM manifest_details WHERE job_reference = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, reference);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                file_number = resultSet.getString("file_number");
                system_reference = resultSet.getString("system_reference");
//                job_reference = resultSet.getString("job_reference");
                master_shipper = resultSet.getString("masterShipper");
                notify_party = resultSet.getString("notify_party");
                consignee = resultSet.getString("consignee");
                mbl_number = resultSet.getString("mbl_number");
                hbl_number = resultSet.getString("hbl_number");
                shipperAddress = resultSet.getString("shipper_address");
                notify_address = resultSet.getString("notify_party_address");
                consignee_address = resultSet.getString("consignee_address");
                description_of_cargo = resultSet.getString("descriptionOfCargo");
                marks_numbers = resultSet.getString("marks_numbers");
                gross_weight = resultSet.getString("gross_weight");
                number_packages = resultSet.getString("number_packages");
                net_weight = resultSet.getString("net_weight");
                package_type = resultSet.getString("package_type");
                do_expiry = resultSet.getString("do_expiry");
                cbm = resultSet.getString("cbm");
                freight = resultSet.getString("freight");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean getContainerDetails(String reference, String containerNumber){
        String query = "SELECT * FROM container_details WHERE job_reference = ? AND container_number = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, reference);
            preparedStatement.setString(2, containerNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                container_number = resultSet.getString("container_number");
                seal_number = resultSet.getString("seal_number");
                size = resultSet.getString("size");
                status = resultSet.getString("status");
                packages = resultSet.getString("packages");
                cbmContainer = resultSet.getString("cbm");

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public ArrayList<Container> getContainers(String reference){
        ArrayList<Container> containersList = new ArrayList<>();
        Container container = new Container();
        String query = "SELECT * FROM container_details WHERE job_reference = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, reference);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String container_number = resultSet.getString("container_number");
                String seal_number = resultSet.getString("seal_number");
                String size = resultSet.getString("size");
                String status = resultSet.getString("status");
                String packages = resultSet.getString("packages");
                String cbmContainer = resultSet.getString("cbm");
                containersList.add(container.produce(container_number,seal_number,size,status,packages,cbmContainer));
            }
//            return containersList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                return containersList;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    public List<String> getAddressParty(){
        List<String> addressList = new ArrayList<>();
        String query = "SELECT * FROM manifest_details";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String masterShipper = resultSet.getString("masterShipper");
                String notify_party = resultSet.getString("notify_party");
                String consignee = resultSet.getString("consignee");
                if (!addressList.contains(masterShipper)){
//                    System.out.println(masterShipper);
                    addressList.add(masterShipper);
                }
                if (!addressList.contains(notify_party)){
//                    System.out.println(notify_party);
                    addressList.add(notify_party);
                }
                if (!addressList.contains(consignee)){
//                    System.out.println(consignee);
                    addressList.add(consignee);
                }
                if (!addressList.contains(masterShipper) && !addressList.contains(notify_party)){
                    addressList.add(masterShipper);
                    addressList.add(notify_party);
//                    System.out.println(masterShipper);
//                    System.out.println(notify_party);
                }
                if (!addressList.contains(masterShipper) && !addressList.contains(consignee)){
                    addressList.add(masterShipper);
                    addressList.add(consignee);
//                    System.out.println(masterShipper);
//                    System.out.println(consignee);
                }
                if (!addressList.contains(notify_party) && !addressList.contains(consignee)){
                    addressList.add(notify_party);
                    addressList.add(consignee);
                }
                if (!addressList.contains(masterShipper) && !addressList.contains(notify_party) && !addressList.contains(consignee)){
                    addressList.add(masterShipper);
                    addressList.add(notify_party);
                    addressList.add(consignee);
//                    System.out.println(masterShipper);
//                    System.out.println(notify_party);
//                    System.out.println(consignee);
                }
            }
//            return containersList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                return addressList;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public String getAddress(String party){
        List<String> nameList = new ArrayList<>();
        List<String> addressList = new ArrayList<>();
        String query = "SELECT * FROM manifest_details";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String masterShipper = resultSet.getString("masterShipper");
                String notify_party = resultSet.getString("notify_party");
                String consignee = resultSet.getString("consignee");
                String shipper_address = resultSet.getString("shipper_address");
                String notify_party_address = resultSet.getString("notify_party_address");
                String consignee_address = resultSet.getString("consignee_address");
                if (!nameList.contains(masterShipper)){
//                    System.out.println(masterShipper);
                    nameList.add(masterShipper);
                    addressList.add(shipper_address);
                }
                if (!nameList.contains(notify_party)){
//                    System.out.println(notify_party);
                    nameList.add(notify_party);
                    addressList.add(notify_party_address);
                }
                if (!nameList.contains(consignee)){
//                    System.out.println(consignee);
                    nameList.add(consignee);
                    addressList.add(consignee_address);
                }
                if (!nameList.contains(masterShipper) && !nameList.contains(notify_party)){
                    nameList.add(masterShipper);
                    nameList.add(notify_party);
                    addressList.add(shipper_address);
                    addressList.add(notify_party_address);
//                    System.out.println(masterShipper);
//                    System.out.println(notify_party);
                }
                if (!nameList.contains(masterShipper) && !nameList.contains(consignee)){
                    nameList.add(masterShipper);
                    nameList.add(consignee);
                    addressList.add(shipper_address);
                    addressList.add(consignee_address);
//                    System.out.println(masterShipper);
//                    System.out.println(consignee);
                }
                if (!nameList.contains(notify_party) && !nameList.contains(consignee)){
                    nameList.add(notify_party);
                    nameList.add(consignee);
                    addressList.add(notify_party_address);
                    addressList.add(consignee_address);
                }
                if (!nameList.contains(masterShipper) && !nameList.contains(notify_party) && !nameList.contains(consignee)){
                    nameList.add(masterShipper);
                    nameList.add(notify_party);
                    nameList.add(consignee);
                    addressList.add(shipper_address);
                    addressList.add(notify_party_address);
                    addressList.add(consignee_address);
//                    System.out.println(masterShipper);
//                    System.out.println(notify_party);
//                    System.out.println(consignee);
                }
            }
//            return containersList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                if (nameList.contains(party)){
                    int index = nameList.indexOf(party);
                    String address = addressList.get(index);
                    return address;
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    public static String getID(){
        String query = "SELECT id FROM manifest_details ORDER BY id DESC LIMIT 1";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString("id");
                int idi = Integer.parseInt(id);
                idi += 1;
                return String.valueOf(idi);
            } else {
                return String.valueOf(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean deleteContainerDetails(String container_number, String job_reference) {
        String query = "DELETE FROM container_details WHERE container_number = ? AND job_reference = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, container_number);
            preparedStatement.setString(2, job_reference);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                preparedStatement.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static boolean deleteManifestDetails(String reference) {
        String query = "DELETE FROM manifest_details WHERE job_reference = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, reference);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                preparedStatement.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

    }

    public List<String> getAllManifests(){
        List<String> addressList = new ArrayList<>();
        String query = "SELECT * FROM manifest_details";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("job_reference");
                addressList.add(name);
            }
//            return containersList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                return addressList;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public List<String> getAllContainers(String reference){
        List<String> containerList = new ArrayList<>();
        String query = "SELECT * FROM container_details WHERE job_reference = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,reference);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("container_number");
                containerList.add(name);
            }
//            return containersList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                return containerList;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static String getFile_number(){
        return file_number;
    }
    public static String getSystem_reference(){
        return system_reference;
    }
    public static String getMaster_shipper(){
        return master_shipper;
    }
    public static String getNotify_party(){
        return notify_party;
    }
    public static String getConsignee(){
        return consignee;
    }
    public static String getMbl_number(){
        return mbl_number;
    }
    public static String getHbl_number(){
        return hbl_number;
    }
    public static String getShipperAddress(){
        return shipperAddress;
    }
    public static String getNotify_address() {
        return notify_address;
    }

    public static String getConsignee_address() {
        return consignee_address;
    }
    public static String getDescription_of_cargo(){
        return description_of_cargo;
    }
    public static String getMarks_numbers(){
        return marks_numbers;
    }
    public static String getGross_weight(){
        return gross_weight;
    }
    public static String getNumber_packages(){
        return number_packages;
    }
    public static String getNet_weight(){
        return net_weight;
    }
    public static String getPackage_type(){
        return package_type;
    }
    public static String getDo_expiry(){
        return do_expiry;
    }
    public static String getCbm(){
        return cbm;
    }
    public static String getFreight(){
        return freight;
    }

    public static String getContainer_number(){
        return container_number;
    }
    public static String getSeal_number(){
        return seal_number;
    }
    public static String getSize(){
        return size;
    }
    public static String getStatus(){
        return status;
    }
    public static String getPackages(){
        return packages;
    }
    public static String getCbmContainer(){
        return cbmContainer;
    }
}
