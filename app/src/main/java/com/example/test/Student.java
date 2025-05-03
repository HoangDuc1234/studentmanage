package com.example.test;

import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private String mssv;
    private String email;
    private String sdt;

    public Student(String name, String mssv, String email, String sdt) {
        this.name = name;
        this.mssv = mssv;
        this.email = email;
        this.sdt = sdt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
