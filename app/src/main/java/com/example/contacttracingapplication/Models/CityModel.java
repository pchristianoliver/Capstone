package com.example.contacttracingapplication.Models;

public class CityModel {
    private int id;
    private String psgcCode;
    private String citymunDesc;
    private String regDesc;
    private String provCode;
    private String citymunCode;

    public CityModel(int id, String psgcCode, String citymunDesc, String regDesc, String provCode, String citymunCode) {
        this.id = id;
        this.psgcCode = psgcCode;
        this.citymunDesc = citymunDesc;
        this.regDesc = regDesc;
        this.provCode = provCode;
        this.citymunCode = citymunCode;
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

    public String getCitymunDesc() {
        return citymunDesc;
    }

    public void setCitymunDesc(String citymunDesc) {
        this.citymunDesc = citymunDesc;
    }

    public String getRegDesc() {
        return regDesc;
    }

    public void setRegDesc(String regDesc) {
        this.regDesc = regDesc;
    }

    public String getProvCode() {
        return provCode;
    }

    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    public String getCitymunCode() {
        return citymunCode;
    }

    public void setCitymunCode(String citymunCode) {
        this.citymunCode = citymunCode;
    }

    @Override
    public String toString() {
        return  citymunDesc;
    }
}
