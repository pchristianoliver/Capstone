package com.example.contacttracingapplication.Models;

public class RegionModel {
    private int id;
    private String psgcCode;
    private String regDesc;
    private String regCode;

    public RegionModel(int id, String psgcCode, String regDesc, String regCode) {
        this.id = id;
        this.psgcCode = psgcCode;
        this.regDesc = regDesc;
        this.regCode = regCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPsgcCode() {
        return psgcCode;
    }

    public void setPsgcCode(String psgcCode) {
        this.psgcCode = psgcCode;
    }

    public String getRegDesc() {
        return regDesc;
    }

    public void setRegDesc(String regDesc) {
        this.regDesc = regDesc;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    @Override
    public String toString() {
        return  regDesc;
    }
}
