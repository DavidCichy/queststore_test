package com.codecool.DAO.DAOImplementations;

import com.codecool.DAO.DAOInterfaces.CoinDAO;
import com.codecool.DAO.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoinDAOImplementation implements CoinDAO {

    @Override
    public int getAmount(int userID, ExperienceDAOImplementation expDAO){
        DatabaseConnection databaseConnection;
        int coolcoinsAmount = 0;
        try{
            int moneySpent = 0;
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select (sum(a_s.buy_price)) as moneySpent" +
                    " from artifacts_statuses a_s" +
                    " where a_s.student_id = (select id from students where user_id = ?)");
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                moneySpent = rs.getInt("moneySpent");
            }

            coolcoinsAmount = expDAO.getLevel(userID) - moneySpent;

            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return coolcoinsAmount;
    }
}
