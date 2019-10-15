package com.codecool.DAO.DAOImplementations;

import com.codecool.DAO.DAOInterfaces.LoginDAO;
import com.codecool.DAO.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOImplementation implements LoginDAO {

    @Override
    public boolean checkIfLoginExists(String loginInput) {
        boolean loginExists = false;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select exists(select 1 from users where login = ?)" +
                    " as result;");
            pstmt.setString(1, loginInput);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                loginExists = rs.getBoolean("result");
            }
            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return  loginExists;
    }

    @Override
    public boolean checkIfPasswordMatches(String loginInput, String passwordInput) {
        boolean passwordMatches = false;
        String password = null;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select password from users where login = ?;");
            pstmt.setString(1, loginInput);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                password = rs.getString("password");
            }
            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        if(password.equals(passwordInput)){
            passwordMatches = true;
        }
        return  passwordMatches;
    }

    @Override
    public int getUserIDByLogin(String loginInput) {
        int userID = 0;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select id from users where " +
                    "login = ?;");
            pstmt.setString(1, loginInput);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                userID = rs.getInt("id");
            }
            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return  userID;
    }
}
