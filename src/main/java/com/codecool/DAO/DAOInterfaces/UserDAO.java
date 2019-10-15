package com.codecool.DAO.DAOInterfaces;

public interface UserDAO {
    String getTypeByID(int userID);
    void addCredentials(String login, String password, int userType);
    void updatePassword(int userID, String password);
    void delete(int userID);
}
