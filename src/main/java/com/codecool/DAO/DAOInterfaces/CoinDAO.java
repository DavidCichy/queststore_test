package com.codecool.DAO.DAOInterfaces;

import com.codecool.DAO.DAOImplementations.ExperienceDAOImplementation;

public interface CoinDAO {
    int getAmount(int userID, ExperienceDAOImplementation expDAO);
}
