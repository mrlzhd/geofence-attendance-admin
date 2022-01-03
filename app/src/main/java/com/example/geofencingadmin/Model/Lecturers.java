package com.example.geofencingadmin.Model;

public class Lecturers {

    private String matric, password;

    public Lecturers() {
    }

    public Lecturers(String matric, String password) {
        this.matric = matric;
        this.password = password;
    }

    public String getMatric() {
        return matric;
    }

    public void setMatric(String matric) {
        this.matric = matric;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
