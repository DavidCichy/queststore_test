package com.codecool.model;

import java.util.List;

public class Student extends User {
    private String classType;
    private int coinsAmount;
    private int experience;
    private List<Artifact> boughtArtifacts;

    public Student(int id, String firstName, String lastName, String login, String password,
                   String email, String classType, int coinsAmount, int experience, List<Artifact> boughtArtifacts) {
        super(id, firstName, lastName, login, password, email);
        this.classType = classType;
        this.coinsAmount = coinsAmount;
        this.experience = experience;
        this.boughtArtifacts = boughtArtifacts;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public int getCoinsAmount() {
        return coinsAmount;
    }

    public void setCoinsAmount(int coinsAmount) {
        this.coinsAmount = coinsAmount;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public List<Artifact> getBoughtArtifacts() {
        return boughtArtifacts;
    }

    public void setBoughtArtifacts(List<Artifact> boughtArtifacts) {
        this.boughtArtifacts = boughtArtifacts;
    }
}
