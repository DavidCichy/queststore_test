package com.codecool.DAO.DAOInterfaces;

import com.codecool.model.Quest;

import java.util.List;

public interface QuestDAO {
    List<Quest> getAllQuests();
    Quest getByID(int questID);
    List<Quest> getStudentsCompletedQuests(int userID);
    void delete(int questID);
    void update( int questID, String name, String description, int reward, int category);
    void add(String name, String description, int reward, int category);
    void markAsDone(int userID, int questID);
}
