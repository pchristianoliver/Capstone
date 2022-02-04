package com.example.contacttracingapplication.Models;

public class ProvinceModel {
    private int id;
    private String psgcCode;
    private String provDesc;
    private String regCode;
    private String provCode;

    public ProvinceModel(int id, String psgcCode, String provDesc, String regCode, String provCode) {
        this.id = id;
        this.psgcCode = psgcCode;
        this.provDesc = provDesc;
        this.regCode = regCode;
        this.provCode = provCode;
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

    public String getProvDesc() {
        return provDesc;
    }

    public void setProvDesc(String provDesc) {
        this.provDesc = provDesc;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getProvCode() {
        return provCode;
    }

    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    @Override
    public String toString() {
        return  provDesc;
    }
}
