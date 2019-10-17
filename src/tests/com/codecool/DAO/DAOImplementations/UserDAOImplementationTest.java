package com.codecool.DAO.DAOImplementations;

import com.codecool.DAO.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplementationTest {


    @Test
    void checkTestDbByGetTypeByID() throws SQLException {

        String userTypeOriginalDb = getUserRole("jdbc:postgresql://localhost:5432/QuestStore");
        String userTypeTestDb = getUserRole("jdbc:postgresql://localhost:5432/ShopTest");

        assertEquals(userTypeOriginalDb, userTypeTestDb);
    }

    private String getUserRole(String db) {
        UserDAOImplementation userDao = getUserDAOImplementation(
                db);
        return userDao.getTypeByID(1);
    }

    private UserDAOImplementation getUserDAOImplementation(String db) {
        return new UserDAOImplementation(
                    new DatabaseConnection(db, "postgres", "root"));
    }


}
