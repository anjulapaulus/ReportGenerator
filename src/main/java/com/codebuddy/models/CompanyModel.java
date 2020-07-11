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

    public boolean addCompanyDetails(String name, String address, String telephone) {
        String query = "INSERT INTO company_details(company_name,company_address,company_telephone)" +
                " VALUES(?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, telephone);

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

    public boolean updateCompanyDetails(String name, String address, String telephone) {
        String query = "UPDATE company_details " +
                "SET company_address = ?, company_telephone = ?" +
                " WHERE company_name = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, address);
            preparedStatement.setString(2, telephone);
            preparedStatement.setString(3, name);
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
        List<String> addressList = new ArrayList<>();
        String query = "SELECT * FROM company_details";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("company_name");
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
}

