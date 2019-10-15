package com.codecool.DAO.DAOImplementations;

import com.codecool.DAO.DAOInterfaces.ExperienceDAO;
import com.codecool.DAO.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExperienceDAOImplementation  implements ExperienceDAO {

    @Override
    public int getLevel(int userID) {
        int experience = 0;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select sum(q_s.reward) as totalExperience" +
                    " from quests_statuses q_s" +
                    " join students s on s.id = q_s.student_id" +
                    " where s.id = (select id from students where user_id = ?)");
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                experience = rs.getInt("totalExperience");
            }
            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return  experience;
    }
}
