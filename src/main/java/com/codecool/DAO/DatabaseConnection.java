package com.codecool.DAO;

import com.codecool.DAO.DAOImplementations.ConnectionSettings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection() {
        String url = ConnectionSettings.url;
        String user = ConnectionSettings.user;
        String password = ConnectionSettings.password;
        connection = setConnection(url, user, password);
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
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
