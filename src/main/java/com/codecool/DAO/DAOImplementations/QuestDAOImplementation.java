package com.codecool.DAO.DAOImplementations;

import com.codecool.DAO.DAOInterfaces.QuestDAO;
import com.codecool.DAO.DatabaseConnection;
import com.codecool.model.Quest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestDAOImplementation implements QuestDAO {

    @Override
    public List<Quest> getAllQuests(){
        DatabaseConnection databaseConnection;
        List<Quest> quests = new ArrayList<>();
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select q.id, q.name, description, reward, q_cat.category" +
                    " as category from quests q join quests_categories q_cat on q_cat.id = q.category;");
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Quest quest = new Quest(rs.getInt("id"), rs.getString("name"), rs.getString("description")
                        , rs.getInt("reward"), rs.getString("category"));
                quests.add(quest);
            }
            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return quests;
    }

    @Override
    public List<Quest> getStudentsCompletedQuests(int userID){
        DatabaseConnection databaseConnection;
        List<Quest> completedQuests = new ArrayList<>();
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select q.id, q.name, q.description, q.reward," +
                    " q_cat.category as category" +
                    " from quests_statuses q_s" +
                    " join quests q on q_s.quest_id = q.id" +
                    " join quests_categories q_cat on q_cat.id = q.category" +
                    " where q_s.student_id = (select id from students where user_id = ?);");
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Quest quest = new Quest(rs.getInt("id"), rs.getString("name"), rs.getString("description")
                        , rs.getInt("reward"), rs.getString("category"));
                completedQuests.add(quest);
            }
            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return completedQuests;
    }

    @Override
    public Quest getByID(int questID){
        DatabaseConnection databaseConnection;
        Quest quest = null;
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select q.id, q.name, description, reward, q_cat.category" +
                    " as category from quests q join quests_categories q_cat on q_cat.id = q.category where q.id = ?;");
            pstmt.setInt(1, questID);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                quest = new Quest(rs.getInt("id"), rs.getString("name"), rs.getString("description")
                        , rs.getInt("reward"), rs.getString("category"));
            }
            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return quest;
    }

    @Override
    public void delete(int questID){
        DatabaseConnection databaseConnection;
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("delete from quests where id = ?;");
            pstmt.setInt(1, questID);
            pstmt.executeUpdate();
            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
    }

    @Override
    public void update( int questID, String name, String description, int reward, int category){
        DatabaseConnection databaseConnection;
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("update quests set name = ?, description = ?," +
                    " reward = ?, category = ? where id = ?;");
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, reward);
            pstmt.setInt(4, category);
            pstmt.setInt(5, questID);

            pstmt.executeUpdate();
            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
    }

    @Override
    public void add(String name, String description, int reward, int category){
        DatabaseConnection databaseConnection;
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("insert into quests (id, name, description," +
                    " reward, category) values ((SELECT setval('quests_id_seq', max(id)) from quests)+1 ,?, ?, ?, ?);");
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, reward);
            pstmt.setInt(4, category);

            pstmt.executeUpdate();
            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
    }

    @Override
    public void markAsDone(int userID, int questID){
        DatabaseConnection databaseConnection;
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("insert into quests_statuses" +
                    " (id, student_id, quest_id, reward)" +
                    " values((SELECT setval('quests_statuses_id_seq', max(id)) from quests_statuses)+1," +
                    " (select id from students where user_id = ?), ?, (select reward from quests where id = ?));");

            pstmt.setInt(1, userID);
            pstmt.setInt(2, questID);
            pstmt.setInt(3, questID);

            pstmt.executeUpdate();
            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
    }
}
