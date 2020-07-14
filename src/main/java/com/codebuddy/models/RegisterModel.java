package com.codebuddy.models;

import com.codebuddy.SqliteConnection;
import com.codebuddy.controllers.Container;
import com.codebuddy.controllers.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterModel {

    Connection connection;
    private static String userEmailName;
    private static String password;
    private static String role;

    public RegisterModel() {
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
    public boolean checkIfUserExists (String email){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM users WHERE email = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
//                String role = resultSet.getString("role");
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
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public boolean registerUser(String email, String password, String userType){
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO users (email, password,role) VALUES(?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, userType);
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

    public boolean updateUser(String email, String password, String userType){
        PreparedStatement preparedStatement = null;
        String query = "UPDATE  users SET password = ?,role = ? WHERE email = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, userType);
            preparedStatement.setString(3, email);
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

    public ArrayList<Users> getAllUsers(){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Users> usersList = new ArrayList<>();
        Users users = new Users();
        String query = "SELECT * FROM users";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userEmailName = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                usersList.add(users.produce(userEmailName,role));
            }
//            return containersList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                return usersList;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void getUser(String name){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Users> usersList = new ArrayList<>();
        Users users = new Users();
        String query = "SELECT * FROM users WHERE email = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userEmailName = resultSet.getString("email");
                password = resultSet.getString("password");
                role = resultSet.getString("role");
            }
//            return containersList;
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

    public boolean deleteContainerDetails(String name) {
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM users WHERE email = ?";
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
    public List<String> getAllUserNames(){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> userList = new ArrayList<>();
        String query = "SELECT * FROM users ";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("email");
                userList.add(name);
            }
//            return containersList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                return userList;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static void setUserEmailName(String userEmailName) {
        RegisterModel.userEmailName = userEmailName;
    }

    public static String getUserEmailName() {
        return userEmailName;
    }

    public static void setPassword(String password) {
        RegisterModel.password = password;
    }

    public static String getPassword() {
        return password;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        RegisterModel.role = role;
    }
}
