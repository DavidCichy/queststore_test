package com.codecool.DAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {
    private DatabaseConnection dbconnection;


    @BeforeEach
    void setUp() {
        this.dbconnection = new DatabaseConnection();
    }

    @org.junit.jupiter.api.Test
    void checkIfDefaultConnectionIsValid() throws SQLException {
        Connection connection = dbconnection.setConnection();
        assertEquals(true, connection.isValid(100));
    }

    @org.junit.jupiter.api.Test
    void checkIfCorrectConnectionIsValid() throws SQLException {
        Connection connection = dbconnection.setConnection("jdbc:postgresql://localhost:5432/QuestStore", "postgres", "fallout");
        assertEquals(true, connection.isValid(100));
    }

    @org.junit.jupiter.api.Test
    void checkIsConnectionNullIfDBDataIsInvalid()   throws SQLException {
        assertNull(dbconnection.setConnection("jdbc:postgresql://localhost:5432/wrongdatabase/", "postgres", "fallout"));
    }

    @org.junit.jupiter.api.Test
    void checkIsConnectionNullIfUserIsInvalid()   throws SQLException {
        assertNull(dbconnection.setConnection("jdbc:postgresql://localhost:5432/QuestStore", "possdsdtgres", "fallout"));
    }

    @org.junit.jupiter.api.Test
    void checkIsConnectionNullIfPasswordIsInvalid()   throws SQLException {
        assertNull(dbconnection.setConnection("jdbc:postgresql://localhost:5432/QuestStore", "postgres", "fasdfsgllout"));
    }
}