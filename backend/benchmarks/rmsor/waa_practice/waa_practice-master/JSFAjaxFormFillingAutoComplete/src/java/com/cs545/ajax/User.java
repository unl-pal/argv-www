/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs545.ajax;

/**
 *
 * @author 984317
 */
public class User {
    private String fName;
    private String lName;
    private String email;
    private String strtAddress;
    private String state;
    private int zip;

    public User() {
    }
    
    public User(String fName, String lName, String email, String strtAddress, String state, int zip) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.strtAddress = strtAddress;
        this.state = state;
        this.zip = zip;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStrtAddress() {
        return strtAddress;
    }

    public void setStrtAddress(String strtAddress) {
        this.strtAddress = strtAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
}
