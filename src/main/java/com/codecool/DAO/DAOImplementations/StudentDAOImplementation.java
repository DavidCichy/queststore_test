package com.codecool.DAO.DAOImplementations;

import com.codecool.DAO.DAOInterfaces.StudentDAO;
import com.codecool.DAO.DatabaseConnection;
import com.codecool.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImplementation implements StudentDAO {

    @Override
    public List<Student> getAll(CoinDAOImplementation coinDAO, ExperienceDAOImplementation expDAO,
                                        ArtifactDAOImplementation artifactDAO){
        DatabaseConnection databaseConnection;
        List<Student> students = new ArrayList<>();
        try {
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select c.type as class_type, s.user_id, s.name," +
                    " s.surname, s.email, u.login, u.password" +
                    " from classes c" +
                    " join students s on s.class_id = c.id" +
                    " join users u on u.id = s.user_id");
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Student student = new Student(rs.getInt("user_id"), rs.getString("name"), rs.getString("surname")
                        ,rs.getString("login") ,rs.getString("password"), rs.getString("email"),
                        rs.getString("class_type"),
                        coinDAO.getAmount(rs.getInt("user_id"), expDAO), expDAO.getLevel(rs.getInt(
                                "user_id")), artifactDAO.getBought(rs.getInt("user_id")));
                students.add(student);
            }

            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return students;
    }

    @Override
    public Student getByID(int userID ,CoinDAOImplementation coinDAO, ExperienceDAOImplementation expDAO,
                                        ArtifactDAOImplementation artifactDAO){
        DatabaseConnection databaseConnection;
        Student student = null;
        try {
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select c.type as class_type, s.user_id, s.name," +
                    " s.surname, s.email, u.login, u.password" +
                    " from classes c" +
                    " join students s on s.class_id = c.id" +
                    " join users u on u.id = s.user_id" +
                    " where s.user_id = ?");
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                student = new Student(rs.getInt("user_id"), rs.getString("name"), rs.getString("surname")
                        ,rs.getString("login") ,rs.getString("password"), rs.getString("email"),
                        rs.getString("class_type"),
                        coinDAO.getAmount(rs.getInt("user_id"), expDAO), expDAO.getLevel(rs.getInt(
                        "user_id")), artifactDAO.getBought(rs.getInt("user_id")));
            }

            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return student;
    }

    @Override
    public void add(String firstName, String lastName, String email, int classID,String login,
                           String password, UserDAOImplementation userDAO) {
        userDAO.addCredentials(login, password, 2);
        DatabaseConnection databaseConnection;
        String sql = "insert into students (id, name, surname, email, user_id) values ((SELECT setval('mentors_id_seq', max(id)) from mentors)+1, ?, ?, ?, (select MAX(id) from users));";
        try {
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            System.out.println(statement);
            statement.executeUpdate();
            statement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
    }

    @Override
    public void delete(int userID, UserDAOImplementation userDAO) {
        userDAO.delete(userID);
        DatabaseConnection databaseConnection;
        String sql = "delete from students where user_id = ?;";
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
    }

    @Override
    public void update(int user_id, String name, String surname, String email){
        DatabaseConnection databaseConnection;
        String sql = "update students set name=?, surname=?, email=? where user_id=?;";
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, email);
            statement.setInt(4, user_id);
            statement.executeUpdate();
            statement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
    }

    @Override
    public void updateClass(int userID, int classID) {
        DatabaseConnection databaseConnection;
        String sql = "update students set class_id=? where user_id=?;";
        try{
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, classID);
            statement.setInt(2, userID);
            statement.executeUpdate();
            statement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
    }
}