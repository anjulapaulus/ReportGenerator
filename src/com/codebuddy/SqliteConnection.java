package com.codebuddy;

import java.sql.Connection;
import java.sql.*;

public class SqliteConnection {
    public static Connection connection() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:cmsDB.db");
            return conn;
        } catch (Exception e) {
            return null;
        }
    }
}
