package com.codecool.model;

import java.util.List;

public class SchoolClass {
    private int id;
    private List<Student> students;
    private List<Mentor> mentors;
    private String type;

    public SchoolClass(int id, List<Student> students, List<Mentor> mentors, String type) {
        this.id = id;
        this.students = students;
        this.mentors = mentors;
        this.type = type;
    }

    public SchoolClass(){

    }

    public int getId(){
        return  id;
    }

    public void setId(int id){
        this.id = id;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Mentor> getMentors() {
        return mentors;
    }

    public void setMentors(List<Mentor> mentors) {
        this.mentors = mentors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
