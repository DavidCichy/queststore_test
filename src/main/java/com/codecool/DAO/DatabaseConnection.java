package com.codecool.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    Connection connection;

    public DatabaseConnection() {
        connection = setConnection("jdbc:postgresql://localhost:5432/QuestStore", "postgres", "root");
    }

    public DatabaseConnection(String db, String username,String pass) {
        connection = setConnection(db, username, pass);
    }

    public Connection setConnection() {
        return connection;
    }

    public Connection setConnection(String db, String user, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(db, user, password);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return connection;
    }
}
