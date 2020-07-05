package com.codebuddy.models;

import com.codebuddy.SqliteConnection;
import com.codebuddy.controllers.CompanyProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyProfileModel {

    private static Connection connection;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static String company_name = null;
    private static String company_address = null;
    private static String telephone = null;
    private static String file_number = null;
    private static String date = null;
    private static String remarks = null;

    public CompanyProfileModel() {
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

    public boolean addCompany(String companyName, String companyAddress, String telephone, String fileNumber, String date, String remark) throws SQLException {
        String query = "INSERT INTO company(company_name,company_address,telephone,file_number,date,remarks)" +
                " VALUES(?,?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,companyName);
            preparedStatement.setString(2,companyAddress);
            preparedStatement.setString(3,telephone);
            preparedStatement.setString(4,fileNumber);
            preparedStatement.setString(5,date);
            preparedStatement.setString(6,remark);
            int result = preparedStatement.executeUpdate();
            if (result == 1){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }finally {
            preparedStatement.close();
        }

    }

    public boolean updateCompany(String companyName, String companyAddress, String telephone, String fileNumber, String date, String remark) throws SQLException {
        String query = "UPDATE company " +
                "SET company_name = ?, company_address = ?, telephone = ?, date = ?, remarks = ?, file_number= ? " +
                "WHERE id = 1";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,companyName);
            preparedStatement.setString(2,companyAddress);
            preparedStatement.setString(3,telephone);
            preparedStatement.setString(4,date);
            preparedStatement.setString(5,remark);
            preparedStatement.setString(6,fileNumber);
            int result = preparedStatement.executeUpdate();
            if (result == 1){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }finally {
            preparedStatement.close();
        }
    }

    public boolean isExistCompany() throws SQLException {
        String query = "SELECT * FROM company WHERE id = 1";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                company_name = resultSet.getString("company_name");
                System.out.println(company_name);
                if (company_name.equals("")){
                    return false;
                }else{
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return false;
    }

    public static boolean getLatestCompanyDetails() {
        String query = "SELECT * FROM company ORDER BY id DESC LIMIT 1";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                company_name = resultSet.getString("company_name");
                company_address = resultSet.getString("company_address");
                telephone = resultSet.getString("telephone");
                file_number = resultSet.getString("file_number");
                date = resultSet.getString("date");
                remarks = resultSet.getString("remarks");
                if (company_name.equals("")){
                    return false;
                }
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

//    public static boolean getCompanyDetails(String fileNumber) {
//        String query = "SELECT * FROM company WHERE file_number = ?";
//        try {
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1,fileNumber);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()){
//                company_name = resultSet.getString("company_name");
//                company_address = resultSet.getString("company_address");
//                telephone = resultSet.getString("telephone");
////                file_number = resultSet.getString("file_number");
//                date = resultSet.getString("date");
//                remarks = resultSet.getString("remarks");
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            try {
//                preparedStatement.close();
//                resultSet.close();
//                return true;
//            } catch (SQLException e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//
//    }
//
//    public static boolean deleteCompanyDetails(String fileNumber) {
//        String query = "DELETE FROM company WHERE file_number = ?";
//        try {
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1,fileNumber);
//            preparedStatement.executeUpdate();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            try {
//                preparedStatement.close();
//                return true;
//            } catch (SQLException e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//
//    }


    public static String getCompany_name(){
        return company_name;
    }
    public static String getCompany_address(){
        return company_address;
    }
    public static String getTelephone(){
        return telephone;
    }
    public static String getFile_number(){
        return file_number;
    }
    public static String getDate(){
        return date;
    }
    public static String getRemarks(){
        return remarks;
    }

}

