package com.codecool.DAO.DAOImplementations;

import com.codecool.DAO.DAOInterfaces.UserDAO;
import com.codecool.DAO.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImplementation implements UserDAO {

    @Override
    public String getTypeByID(int userID) {
        String userType = null;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select ut.type as type from users\n" +
                    "left join user_types ut on ut.id = users.type\n where users.id = ?;");
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                userType = rs.getString("type");
            }
            pstmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return userType;
    }

    @Override
    public void addCredentials(String login, String password, int userType) {
        DatabaseConnection databaseConnection;
        String sql = "insert into users (id, login, password, type) values ((SELECT setval('users_id_seq', max(id)) from users)+1," +
                " ?, ?, ?);";
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setInt(3, userType);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
    }

    @Override
    public void updatePassword(int userID, String password){
        DatabaseConnection databaseConnection;
        String sql = "update users set password=? where id=?";
        try {
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setInt(2, userID);
            pstmt.executeUpdate();
            pstmt.close();
            connection.close();
        } catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
    }

    @Override
    public void delete(int userID){
        DatabaseConnection databaseConnection;
        try {
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("delete from users where user_id = ?");
            pstmt.setInt(1, userID);

            pstmt.executeUpdate();
            pstmt.close();
            connection.close();
        } catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
    }
}
