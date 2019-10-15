package com.codecool.DAO.DAOImplementations;

import com.codecool.DAO.DAOInterfaces.ArtifactDAO;
import com.codecool.DAO.DatabaseConnection;
import com.codecool.model.Artifact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDAOImplementation implements ArtifactDAO {

    @Override
    public List<Artifact> getAll() {
        DatabaseConnection databaseConnection;

        List<Artifact> artifacts = new ArrayList<>();
        try {
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT art.id, art.name, description, price, " +
                    "art_cat.category as category FROM artifacts art JOIN artifacts_categories art_cat" +
                    " ON art_cat.id = art.category");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Artifact artifact = new Artifact(rs.getInt("id"), rs.getString("name"), rs.getString("description")
                        , rs.getInt("price"), rs.getString("category"));
                artifacts.add(artifact);

            }
            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return artifacts;
    }

    @Override
    public Artifact getByID(int artifactID){
        DatabaseConnection databaseConnection;
        Artifact artifact = null;
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select a.id, a.name, description, price," +
                    " a_cat.category as category \n" +
                    " from artifacts a join artifacts_categories a_cat on a_cat.id = a.category\n" +
                    " where a.id = ?;");
            pstmt.setInt(1, artifactID);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                artifact = new Artifact(rs.getInt("id"), rs.getString("name"), rs.getString("description")
                        , rs.getInt("price"), rs.getString("category"));
            }
            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return artifact;
    }

    @Override
    public void delete(int artifactID){
        DatabaseConnection databaseConnection;
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("delete from artifacts where id = ?;");
            pstmt.setInt(1, artifactID);
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
    public void update( int artifactID, String name, String description, int price, int category){
        DatabaseConnection databaseConnection;
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("update artifacts set name = ?, description = ?," +
                    " price = ?, category = ? where id = ?;");
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, price);
            pstmt.setInt(4, category);
            pstmt.setInt(5, artifactID);

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
    public void add(String name, String description, int price, int category){
        DatabaseConnection databaseConnection;
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("insert into artifacts (id, name, description," +
                    " price, category) values ((SELECT setval('artifacts_id_seq', max(id)) from artifacts)+1 ,?, ?, " +
                    "?, ?);");
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, price);
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
    public void buy(int userID, int artifactID){
        DatabaseConnection databaseConnection;
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("insert into artifacts_statuses" +
                    " (id, student_id, artifact_id, is_used, buy_price)" +
                    " values ((SELECT setval('artifacts_statuses_id_seq', max(id)) from artifacts_statuses)+1," +
                    " (select id from students" +
                    " where user_id = ?), ?, FALSE," +
                    " (select price from artifacts where id = ?));");

            pstmt.setInt(1, userID);
            pstmt.setInt(2, artifactID);
            pstmt.setInt(3, artifactID);

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
    public List<Artifact> getBought(int userID){
        DatabaseConnection databaseConnection;
        List<Artifact> boughtArtifacts = new ArrayList<>();
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select a_s.artifact_id as id, a.name," +
                    " a.description, a_s.is_used, a_s.buy_price as price, a_cat.category as category" +
                    " from artifacts_statuses a_s" +
                    " join artifacts a on a.id = a_s.artifact_id" +
                    " join artifacts_categories a_cat on a_cat.id = a.category" +
                    " join students s on s.id = a_s.student_id" +
                    " where a_s.student_id = (select id from students where user_id = ?);");

            pstmt.setInt(1, userID);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Artifact artifact = new Artifact(rs.getInt("id"), rs.getString("name"), rs.getString("description")
                        , rs.getBoolean("is_used"),rs.getInt("price"), rs.getString("category"));
                boughtArtifacts.add(artifact);
            }

            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return  boughtArtifacts;
    }

    @Override
    public void use(int userID, int artifactID){
        DatabaseConnection databaseConnection;
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("update artifacts_statuses" +
                    " set is_used = TRUE" +
                    " where student_id = (select id from students where user_id = ?)" +
                    " and artifact_id = ?");

            pstmt.setInt(1, userID);
            pstmt.setInt(2, artifactID);
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
