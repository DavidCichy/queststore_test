package com.codecool.model;

import java.util.List;

public class Mentor extends User {
    private List<String> classes;
    private String classType;

    public Mentor(int id, String firstName, String lastName, String login, String password,
                  String email, List<String> classes) {
        super(id, firstName, lastName, login, password, email);
        this.classes = classes;
    }

    public Mentor(int id, String firstName, String lastName, String login, String password, String email){
        super(id, firstName, lastName, login, password, email);
    }

    public List<String> getClasses() { return classes; }

    public String getClassType(){ return  classType; }
}