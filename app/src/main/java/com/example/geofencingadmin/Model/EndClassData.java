package com.example.geofencingadmin.Model;

public class EndClassData {

    String classname, lecturer;

    public EndClassData() {
    }

    public EndClassData(String classname, String lecturer) {
        this.classname = classname;
        this.lecturer = lecturer;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }
}
