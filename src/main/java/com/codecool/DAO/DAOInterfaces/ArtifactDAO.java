package com.codecool.DAO.DAOInterfaces;

import com.codecool.model.Artifact;

import java.util.List;

public interface ArtifactDAO {
    List<Artifact> getAll();
    Artifact getByID(int artifactID);
    void delete(int artifactID);
    void update( int artifactID, String name, String description, int price, int category);
    void add(String name, String description, int price, int category);
    void buy(int userID, int artifactID);
    List<Artifact> getBought(int userID);
    void use(int userID, int artifactID);
}
