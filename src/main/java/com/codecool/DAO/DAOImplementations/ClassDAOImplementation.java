package com.codecool.DAO.DAOImplementations;

import com.codecool.DAO.DAOInterfaces.ClassDAO;
import com.codecool.DAO.DatabaseConnection;
import com.codecool.model.Mentor;
import com.codecool.model.SchoolClass;
import com.codecool.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassDAOImplementation implements ClassDAO {

    @Override
    public List<SchoolClass> getAll(StudentDAOImplementation studentDAO, MentorDAOImplementation mentorDAO,
                                           CoinDAOImplementation coinDAO, ExperienceDAOImplementation expDAO,
                                           ArtifactDAOImplementation artifactDAO){
        DatabaseConnection databaseConnection;
        List<SchoolClass> schoolClasses = new ArrayList<>();
        try {
            databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.setConnection();
            PreparedStatement pstmt = connection.prepareStatement("select * from classes");
            ResultSet rs = pstmt.executeQuery();

            List<Student> students = studentDAO.getAll(coinDAO, expDAO, artifactDAO);
            List<Mentor> mentors = mentorDAO.getAll();

            while(rs.next()){
                List<Student> classStudents = new ArrayList<>();
                List<Mentor> classMentors = new ArrayList<>();
                for(Student student : students){
                    if(student.getClassType().equals(rs.getString("type"))){
                        classStudents.add(student);
                    }
                }
                for(Mentor mentor : mentors){
                    if(mentor.getClasses().contains(rs.getString("type"))){
                        classMentors.add(mentor);
                    }
                }
                SchoolClass schoolClass = new SchoolClass(rs.getInt("id"),
                        classStudents, classMentors, rs.getString("type"));
                schoolClasses.add(schoolClass);
            }

            pstmt.close();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()
                    + "\nSQLState: " + e.getSQLState()
                    + "\nVendorError: " + e.getErrorCode());
        }
        return schoolClasses;
    }
}
