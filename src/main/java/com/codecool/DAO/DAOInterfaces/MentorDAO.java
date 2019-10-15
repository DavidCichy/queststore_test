package com.codecool.DAO.DAOInterfaces;

import com.codecool.model.Mentor;

import java.util.List;

public interface MentorDAO {
    List<Mentor> getAll();
    Mentor getByID(int id);
    List<String> getClassesById(int id);
}
