package com.codecool.DAO.DAOImplementations;

import com.codecool.DAO.DAOInterfaces.SessionDAO;
import com.codecool.DAO.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDAOImplementation implements SessionDAO {

    @Override
    public boolean isCurrent(String sessionID) {
        boolean sessionIsCurrent = false;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select exists(select 1 from sessions where " +
                    "session_id = ?)" + " as result;");
            pstmt.setString(1, sessionID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                sessionIsCurrent = rs.getBoolean("result");
            }
            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return  sessionIsCurrent;
    }

    @Override
    public int getUserIDFromSessionID(String sessionID) {
        int userID = 0;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select user_id from sessions where " +
                    "session_id = ?;");
            pstmt.setString(1, sessionID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                userID = rs.getInt("user_id");
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

    @Override
    public void add(String sessionID, int userID) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try{
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("insert into sessions (user_id, session_id)" +
                    " values(?, ?);");
            pstmt.setInt(1, userID);
            pstmt.setString(2, sessionID);
            pstmt.executeUpdate();
            pstmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
    }

    @Override
    public void delete(String sessionID) {
        com.codecool.DAO.DatabaseConnection databaseConnection = new com.codecool.DAO.DatabaseConnection();
        try{
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("delete from sessions where session_id = ?;");
            pstmt.setString(1, sessionID);
            pstmt.executeUpdate();
            pstmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
    }
}
