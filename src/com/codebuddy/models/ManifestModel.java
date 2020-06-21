package com.codebuddy.models;

import com.codebuddy.SqliteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManifestModel {
    private static Connection connection;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    //variables
    private static String file_number;
    private static String system_reference;
//    private static String job_reference;
    private static String vessel_voyage;
    private static String port_loading;
    private static String port_discharge;
    private static String eta;
    private static String serial_number;
    private static String master_shipper;
    private static String mbl_number;
    private static String hbl_number;
    private static String shipper;
    private static String notify_party;
    private static String consignee;
    private static String description_of_cargo;
    private static String marks_numbers;
    private static String remarks;
    private static String gross_weight;
    private static String number_packages;
    private static String net_weight;
    private static String package_type;
    private static String unit;
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
    public boolean addManifestDetails(String file_number, String system_reference, String job_reference, String vessel_voyage, String portOfLoading, String portOfDischarge, String eta, String serial_number, String masterShipper, String mbl_number, String hbl_number, String shipper, String notify_party, String consignee, String descriptionOfCargo, String marks_numbers, String remarks, String gross_weight, String number_packages,String net_weight,String package_type,String unit,String do_expiry,String cbm,String freight) throws SQLException {
        String query = "INSERT INTO manifest_details(file_number, system_reference, job_reference, vessel_voyage, portOfLoading, portOfDischarge, eta, serial_number, masterShipper, mbl_number, hbl_number, shipper, notify_party, consignee, descriptionOfCargo, marks_numbers, remarks, gross_weight, number_packages, net_weight, package_type, unit, do_expiry, cbm, freight)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, file_number);
            preparedStatement.setString(2, system_reference);
            preparedStatement.setString(3, job_reference);
            preparedStatement.setString(4, vessel_voyage);
            preparedStatement.setString(5, portOfLoading);
            preparedStatement.setString(6, portOfDischarge);
            preparedStatement.setString(7, eta);
            preparedStatement.setString(8, serial_number);
            preparedStatement.setString(9, masterShipper);
            preparedStatement.setString(10, mbl_number);
            preparedStatement.setString(11, hbl_number);
            preparedStatement.setString(12, shipper);
            preparedStatement.setString(13, notify_party);
            preparedStatement.setString(14, consignee);
            preparedStatement.setString(15, descriptionOfCargo);
            preparedStatement.setString(16, marks_numbers);
            preparedStatement.setString(17, remarks);
            preparedStatement.setString(18, gross_weight);
            preparedStatement.setString(19, number_packages);
            preparedStatement.setString(20, net_weight);
            preparedStatement.setString(21, package_type);
            preparedStatement.setString(22, unit);
            preparedStatement.setString(23, do_expiry);
            preparedStatement.setString(24, cbm);
            preparedStatement.setString(25, freight);
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

    public boolean updateManifestDetails(String job_reference, String vessel_voyage, String portOfLoading, String portOfDischarge, String eta, String serial_number, String masterShipper, String mbl_number, String hbl_number, String shipper, String notify_party, String consignee, String descriptionOfCargo, String marks_numbers, String remarks, String gross_weight, String number_packages,String net_weight,String package_type,String unit,String do_expiry,String cbm,String freight) throws SQLException {
        String query = "UPDATE manifest_details SET vessel_voyage = ?,portOfLoading  = ?, portOfDischarge = ?, eta = ?, serial_number = ?, masterShipper = ? ," +
                "mbl_number = ?,hbl_number  = ?, shipper = ?, notify_party = ?, consignee = ?, descriptionOfCargo = ?," +
                "marks_numbers = ?,remarks  = ?, gross_weight = ?, number_packages = ?, net_weight = ?, package_type = ?," +
                "unit = ?,do_expiry  = ?, cbm = ?, freight = ? " +
                "WHERE job_reference = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vessel_voyage);
            preparedStatement.setString(2, portOfLoading);
            preparedStatement.setString(3, portOfDischarge);
            preparedStatement.setString(4, eta);
            preparedStatement.setString(5, serial_number);
            preparedStatement.setString(6, masterShipper);
            preparedStatement.setString(7, mbl_number);
            preparedStatement.setString(8, hbl_number);
            preparedStatement.setString(9, shipper);
            preparedStatement.setString(10, notify_party);
            preparedStatement.setString(11, consignee);
            preparedStatement.setString(12, descriptionOfCargo);
            preparedStatement.setString(13, marks_numbers);
            preparedStatement.setString(14, remarks);
            preparedStatement.setString(15, gross_weight);
            preparedStatement.setString(16, number_packages);
            preparedStatement.setString(17, net_weight);
            preparedStatement.setString(18, package_type);
            preparedStatement.setString(19, unit);
            preparedStatement.setString(20, do_expiry);
            preparedStatement.setString(21, cbm);
            preparedStatement.setString(22, freight);
            preparedStatement.setString(23, job_reference);
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
                vessel_voyage = resultSet.getString("vessel_voyage");
                port_loading = resultSet.getString("portOfLoading");
                port_discharge = resultSet.getString("portOfDischarge");
                eta = resultSet.getString("eta");
                serial_number = resultSet.getString("serial_number");
                master_shipper = resultSet.getString("masterShipper");
                mbl_number = resultSet.getString("mbl_number");
                hbl_number = resultSet.getString("hbl_number");
                shipper = resultSet.getString("shipper");
                notify_party = resultSet.getString("notify_party");
                consignee = resultSet.getString("consignee");
                description_of_cargo = resultSet.getString("descriptionOfCargo");
                marks_numbers = resultSet.getString("marks_numbers");
                remarks = resultSet.getString("remarks");
                gross_weight = resultSet.getString("gross_weight");
                number_packages = resultSet.getString("number_packages");
                net_weight = resultSet.getString("net_weight");
                package_type = resultSet.getString("package_type");
                unit = resultSet.getString("unit");
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

    public static String getFile_number(){
        return file_number;
    }
    public static String getSystem_reference(){
        return system_reference;
    }
    public static String getVessel_voyage(){
        return vessel_voyage;
    }
    public static String getPort_loading(){
        return port_loading;
    }
    public static String getPort_discharge(){
        return port_discharge;
    }
    public static String getEta(){
        return eta;
    }
    public static String getSerial_number(){
        return serial_number;
    }
    public static String getMaster_shipper(){
        return master_shipper;
    }
    public static String getMbl_number(){
        return mbl_number;
    }
    public static String getHbl_number(){
        return hbl_number;
    }
    public static String getShipper(){
        return shipper;
    }
    public static String getNotify_party(){
        return notify_party;
    }
    public static String getConsignee(){
        return consignee;
    }
    public static String getDescription_of_cargo(){
        return description_of_cargo;
    }
    public static String getMarks_numbers(){
        return marks_numbers;
    }
    public static String getRemarks(){
        return remarks;
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
    public static String getUnit(){
        return unit;
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
