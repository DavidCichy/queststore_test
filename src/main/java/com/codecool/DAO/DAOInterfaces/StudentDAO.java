package com.codecool.DAO.DAOInterfaces;

import com.codecool.DAO.DAOImplementations.ArtifactDAOImplementation;
import com.codecool.DAO.DAOImplementations.CoinDAOImplementation;
import com.codecool.DAO.DAOImplementations.ExperienceDAOImplementation;
import com.codecool.DAO.DAOImplementations.UserDAOImplementation;
import com.codecool.model.Student;

import java.util.List;

public interface StudentDAO {
    List<Student> getAll(CoinDAOImplementation coinDAO, ExperienceDAOImplementation expDAO,
                                 ArtifactDAOImplementation artifactDAO);
    Student getByID(int userID ,CoinDAOImplementation coinDAO, ExperienceDAOImplementation expDAO,
                           ArtifactDAOImplementation artifactDAO);
    void add(String firstName, String lastName, String email, int classID,String login,
                    String password, UserDAOImplementation userDAO);
    void delete(int userID, UserDAOImplementation userDAO);
    void update(int userID, String name, String surname, String email);
    void updateClass(int userID, int classID);
}

