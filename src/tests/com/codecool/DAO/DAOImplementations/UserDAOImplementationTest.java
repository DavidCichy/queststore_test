package com.codecool.DAO.DAOImplementations;

import com.codecool.DAO.DatabaseConnection;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import org.flywaydb.core.Flyway;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplementationTest {

    private static final int USER_TABLE_SIZE = 8;

    @BeforeEach
    void setUpDb(){

        Flyway flyway = Flyway.configure().dataSource(
                "jdbc:postgresql://localhost:5432/ShopTest",
                "postgres",
                "root").load();
        flyway.clean();
        flyway.migrate();
    }


    @Test
    void checkTestDbByGetTypeByID() {

        for(int index = 1 ; index <=USER_TABLE_SIZE; index++ ) {
            String userTypeOriginalDb = getUserRole("jdbc:postgresql://localhost:5432/QuestStore", index);
            String userTypeTestDb = getUserRole("jdbc:postgresql://localhost:5432/ShopTest", index);

            assertEquals(userTypeOriginalDb, userTypeTestDb);
        }
    }

    @Test
    void checkTypeByID() {

        int index = 1;

        String userTypeTestDb = getUserRole("jdbc:postgresql://localhost:5432/ShopTest",index);

        assertEquals("student", userTypeTestDb);
    }

    @Test
    void delete() throws SQLException {

        ConnectionSettings.setUrl("jdbc:postgresql://localhost:5432/ShopTest");


        UserDAOImplementation userDAOImplementation = new UserDAOImplementation();
        userDAOImplementation.delete(1);
        assertEquals(userDAOImplementation.getTypeByID(1111),null);



    }

    private String getUserRole(String db, int index) {
        UserDAOImplementation userDao = getUserDAOImplementation(
                db);
        return userDao.getTypeByID(index);
    }

    private UserDAOImplementation getUserDAOImplementation(String db) {
        return new UserDAOImplementation(
                new DatabaseConnection(db, "postgres", "root"));
    }

}
