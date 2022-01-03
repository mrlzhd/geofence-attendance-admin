package com.example.geofencingadmin.Model;

public class Subject {

    private String namaClass, matrik;

    public Subject() {
    }

    public Subject(String namaClass, String matrik) {
        this.namaClass = namaClass;
        this.matrik = matrik;
    }

    public String getNamaClass() {
        return namaClass;
    }

    public void setNamaClass(String namaClass) {
        this.namaClass = namaClass;
    }

    public String getMatrik() {
        return matrik;
    }

    public void setMatrik(String matrik) {
        this.matrik = matrik;
    }
}
