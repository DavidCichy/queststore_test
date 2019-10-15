package com.codecool.DAO.DAOInterfaces;

import com.codecool.DAO.DAOImplementations.*;
import com.codecool.model.SchoolClass;

import java.util.List;

public interface ClassDAO {
    List<SchoolClass> getAll(StudentDAOImplementation studentDAO, MentorDAOImplementation mentorDAO,
                             CoinDAOImplementation coinDAO, ExperienceDAOImplementation expDAO,
                             ArtifactDAOImplementation artifactDAO);
}
