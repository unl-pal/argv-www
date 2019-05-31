/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;

import java.io.Serializable;

/**
 *
 * @author rmsor_000
 */
public class Tea {

    String name;
    String description;
    int quantity;
    double price;
    private boolean editable;
    String picture;

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean newValue) {
        editable = newValue;
    }

    public Object getCategory() {
        return new Object();
    }

    public String getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

}
