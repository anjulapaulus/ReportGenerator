package com.codebuddy.models;

import com.codebuddy.SqliteConnection;
import sun.jvm.hotspot.ui.tree.CStringTreeNodeAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyModel {
    private static Connection connection;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;


    private static String company_name;
    private static String company_address;
    private static String company_telephone;
    private static String company_address2;
    private static String company_address3;
    private static String company_address4;
    private static String company_address5;
    private static String company_address6;

    public CompanyModel() {
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

    public boolean addCompanyDetails(String name, String address, String telephone,String address_2,String address_3,String address_4,String address_5,String address_6) {
        String query = "INSERT INTO company_details(company_name,company_address,company_telephone,address_2,address_3,address_4,address_5,address_6)" +
                " VALUES(?,?,?,?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, telephone);
            preparedStatement.setString(4, address_2);
            preparedStatement.setString(5, address_3);
            preparedStatement.setString(6, address_4);
            preparedStatement.setString(7, address_5);
            preparedStatement.setString(8, address_6);

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
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public boolean updateCompanyDetails(String name, String address, String telephone,String address_2,String address_3,String address_4,String address_5,String address_6) {
        String query = "UPDATE company_details " +
                "SET company_address = ?, company_telephone = ?, address_2 = ?,address_3 = ?,address_4 = ?,address_5 = ?,address_6 = ?" +
                " WHERE company_name = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, address);
            preparedStatement.setString(2, telephone);
            preparedStatement.setString(3, address_2);
            preparedStatement.setString(4, address_3);
            preparedStatement.setString(5, address_4);
            preparedStatement.setString(6, address_5);
            preparedStatement.setString(7, address_6);
            preparedStatement.setString(8, name);
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
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public boolean isExistCompany(String name) {
        String query = "SELECT * FROM company_details WHERE company_name = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
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


    public static boolean deleteCompanyDetails(String name) {
        String query = "DELETE FROM company_details WHERE company_name = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
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

    public List<String> getAllCompanies(){
        List<String> nameList = new ArrayList<>();
        String query = "SELECT * FROM company_details";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("company_name");
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
    public List<String> getAllCompaniesAddresses(String name) {
        List<String> addressList = new ArrayList<>();
        String query = "SELECT * FROM company_details WHERE company_name = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String address1 = resultSet.getString("company_address");
                String address2 = resultSet.getString("address_2");
                String address3 = resultSet.getString("address_3");
                String address4 = resultSet.getString("address_4");
                String address5 = resultSet.getString("address_5");
                String address6 = resultSet.getString("address_6");
                addressList.add(address1);
                if (address2 != null){
                    addressList.add(address2);
                }
                if (address3 != null){
                    addressList.add(address3);
                }
                if (address4 != null){
                    addressList.add(address4);
                }
                if (address5 != null){
                    addressList.add(address5);
                }
                if (address6 != null){
                    addressList.add(address6);
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

    public boolean getCompanyDetails(String name){
        String query = "SELECT * FROM company_details WHERE company_name = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                company_name = resultSet.getString("company_name");
                company_address = resultSet.getString("company_address");
                company_telephone = resultSet.getString("company_telephone");
                company_address2 = resultSet.getString("address_2");
                company_address3 = resultSet.getString("address_3");
                company_address4 = resultSet.getString("address_4");
                company_address5 = resultSet.getString("address_5");
                company_address6 = resultSet.getString("address_6");
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
    public static String getCompany_name(){
        return company_name;
    }
    public static String getCompany_address(){
        return company_address;
    }
    public static String getCompany_telephone(){
        return company_telephone;
    }

    public static String getCompany_address2() {
        return company_address2;
    }

    public static String getCompany_address3() {
        return company_address3;
    }

    public static String getCompany_address4() {
        return company_address4;
    }

    public static String getCompany_address5() {
        return company_address5;
    }

    public static String getCompany_address6() {
        return company_address6;
    }
}

