package com.codecool.DAO.DAOInterfaces;

public interface LoginDAO {
    boolean checkIfLoginExists(String loginInput);
    boolean checkIfPasswordMatches(String loginInput, String passwordInput);
    int getUserIDByLogin(String loginInput);
}
