package com.example.contacttracingapplication.Models;

public class SymptomModel {
    private int id;
    private String symptomName;

    public SymptomModel(int id, String symptomName) {
        this.id = id;
        this.symptomName = symptomName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }
}