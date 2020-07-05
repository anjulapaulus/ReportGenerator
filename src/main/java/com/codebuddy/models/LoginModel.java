package com.codebuddy.models;

import com.codebuddy.SqliteConnection;
import javafx.util.Pair;

import java.sql.*;

public class LoginModel {

    Connection connection;

    public LoginModel() {
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

    public String login(String email, String password) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String role = resultSet.getString("role");
                return role;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
    }
}
