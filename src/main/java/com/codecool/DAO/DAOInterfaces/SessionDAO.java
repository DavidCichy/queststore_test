package com.codecool.DAO.DAOInterfaces;

public interface SessionDAO {
    boolean isCurrent(String sessionID);
    int getUserIDFromSessionID(String sessionID);
    void add(String sessionID, int userID);
    void delete(String sessionID);
}
