package com.codecool.DAO.DAOImplementations;

import com.codecool.DAO.DAOInterfaces.MentorDAO;
import com.codecool.DAO.DatabaseConnection;
import com.codecool.model.Mentor;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MentorDAOImplementation implements MentorDAO{

    @Override
    public List<Mentor> getAll(){
        DatabaseConnection databaseConnection;
        List<Mentor> mentors = new ArrayList<>();
        int index = 0;
        try {
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmtMentor = connection.prepareStatement("select m.user_id, m.name, m.surname, m" +
                    ".email," +
                    " u.login, u.password" +
                    " from mentors m" +
                    " join users u on u.id = m.user_id ");
            ResultSet rsMentor = pstmtMentor.executeQuery();

            PreparedStatement pstmtClasses = connection.prepareStatement("select m.user_id, c.type as class_type" +
                    " from classes c" +
                    " join mentors_classes m_c on m_c.class_id = c.id" +
                    " join mentors m on m.id = m_c.mentor_id" +
                    " join users u on u.id = m.user_id ");
            ResultSet rsClasses = pstmtClasses.executeQuery();

            MultiValuedMap<Integer, String> mentorsAndClasses = new ArrayListValuedHashMap<>();
            while(rsClasses.next()){
                mentorsAndClasses.put(rsClasses.getInt("user_id"), rsClasses.getString("class_type"));
            }
            Set<Integer> usersIDs = mentorsAndClasses.keySet();

            while(rsMentor.next()){
                for (Integer userID : usersIDs) {
                    if(userID.equals(rsMentor.getInt("user_id")) ){
                        Mentor mentor = new Mentor(rsMentor.getInt("user_id"), rsMentor.getString("name"), rsMentor.getString("surname")
                                , rsMentor.getString("login") ,rsMentor.getString("password")
                                , rsMentor.getString("email"), new ArrayList<>(mentorsAndClasses.get(userID)));
                        mentors.add(mentor);
                    }
                }
            }

            pstmtMentor.close();
            pstmtClasses.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return mentors;
    }

    @Override
    public List<String> getClassesById(int id){
        DatabaseConnection dbConnection;
        List<String> mentorClasses = new ArrayList<>();
        String sql = "select classes.type from classes join mentors_classes mc on classes.id = mc.class_id where mc.mentor_id = (select id from mentors where user_id="+id+");";
        try{
            dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                mentorClasses.add(resultSet.getString("type"));
            }
            connection.commit();
            connection.close();
            pstmt.close();
            resultSet.close();
        } catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }

        return mentorClasses;
    }

    @Override
    public Mentor getByID(int id){
        DatabaseConnection dbConnection;
        Mentor mentor = null;
        String sql = "select users.id, users.login, users.password, mentors.name," +
                " mentors.surname, mentors.email" +
                " from users" +
                " join mentors on mentors.user_id = users.id" +
                " where users.id = ?;";
        try{
            dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()){
                mentor = new Mentor(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        getClassesById(resultSet.getInt("id")));
                return mentor;
            }
            connection.commit();
            connection.close();
            pstmt.close();
            resultSet.close();
        } catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return null;
    }
}