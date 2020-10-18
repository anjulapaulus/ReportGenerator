package com.codebuddy;

import java.net.URL;
import java.sql.Connection;
import java.sql.*;

public class SqliteConnection {
    public static Connection connection() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite::resource:"+ SqliteConnection.class.getResource("/cmsDB.db"));
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
