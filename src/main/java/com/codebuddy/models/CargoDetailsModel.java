package com.codebuddy.models;

import com.codebuddy.SqliteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargoDetailsModel {
    private static Connection connection;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static String system_reference = null;
    private static String file_number = null;
    //    private static String reference = null;
    private static String date = null;
    private static String vessel = null;
    private static String voyage = null;
    private static String due_on = null;
    private static String port_shipment = null;
    private static String port_loading = null;
    private static String port_discharge = null;
    private static String serial_number = null;
    private static String num_shipments = null;
    private static String container_status = null;
    private static String dangerous_cargo_list = null;
    private static String liquor_cigerettes = null;
    private static String security_cargo_declaration = null;
    private static String transshipment_cargo = null;
    private static String personal_cargo = null;
    private static String remarks = null;

    public CargoDetailsModel() {
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

    public boolean addCargoDetails(String system_reference, String file_number, String reference, String date, String vessel, String voyage, String due_on, String port_shipment, String port_loading, String port_discharge, String serial_number) throws SQLException {
        String query = "INSERT INTO cargo_details(system_reference,file_number,reference,date,vessel,voyage,due_on,port_shipment,port_loading,port_discharge,serial_number)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, system_reference);
            preparedStatement.setString(2, file_number);
            preparedStatement.setString(3, reference);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, vessel);
            preparedStatement.setString(6, voyage);
            preparedStatement.setString(7, due_on);
            preparedStatement.setString(8, port_shipment);
            preparedStatement.setString(9, port_loading);
            preparedStatement.setString(10, port_discharge);
            preparedStatement.setString(11, serial_number);
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

    public boolean updateCargoDetails(String reference, String date, String vessel, String voyage, String due_on, String port_shipment, String port_loading, String port_discharge, String serial_number) throws SQLException {
        String query = "UPDATE cargo_details " +
                "SET date = ?, vessel = ?, voyage = ?, due_on = ?, port_shipment = ?, port_loading = ?, port_discharge = ?, " +
                "serial_number = ?" +
                " WHERE reference = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, vessel);
            preparedStatement.setString(3, voyage);
            preparedStatement.setString(4, due_on);
            preparedStatement.setString(5, port_shipment);
            preparedStatement.setString(6, port_loading);
            preparedStatement.setString(7, port_discharge);
            preparedStatement.setString(8, serial_number);
            preparedStatement.setString(9, reference);
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

    public boolean isExistCargoDetails(String reference) {
        String query = "SELECT * FROM cargo_details WHERE reference=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, reference);
            resultSet = preparedStatement.executeQuery();
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

    public static boolean deleteCompanyDetails(String reference) {
        String query = "DELETE FROM cargo_details WHERE reference = ?";
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

    public static String getID(){
        String query = "SELECT id FROM cargo_details ORDER BY id DESC LIMIT 1";
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

    public static boolean getCargoDetails(String reference) {
        String query = "SELECT * FROM cargo_details WHERE reference = ?";
//        String query = "SELECT * FROM cargo_details ORDER BY id DESC LIMIT 1";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, reference);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                file_number = resultSet.getString("file_number");
                system_reference = resultSet.getString("system_reference");
                date = resultSet.getString("date");
                vessel = resultSet.getString("vessel");
                voyage = resultSet.getString("voyage");
                due_on = resultSet.getString("due_on");
                port_shipment = resultSet.getString("port_shipment");
                port_loading = resultSet.getString("port_loading");
                port_discharge = resultSet.getString("port_discharge");
                serial_number = resultSet.getString("serial_number");
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

    public List<String> getAllCargoDetails(){
        List<String> nameList = new ArrayList<>();
        String query = "SELECT * FROM cargo_details";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("reference");
                nameList.add(name);
            }
//            return containersList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                return nameList;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static String getSystem_reference() {
        return system_reference;
    }

    public static String getFile_number() {
        return file_number;
    }

    public static String getDate() {
        return date;
    }

    public static String getVessel() {
        return vessel;
    }

    public static String getVoyage() {
        return voyage;
    }

    public static String getDue_on() {
        return due_on;
    }

    public static String getPort_shipment() {
        return port_shipment;
    }

    public static String getPort_loading() {
        return port_loading;
    }

    public static String getPort_discharge() {
        return port_discharge;
    }

    public static String getSerial_number() {
        return serial_number;
    }

}
