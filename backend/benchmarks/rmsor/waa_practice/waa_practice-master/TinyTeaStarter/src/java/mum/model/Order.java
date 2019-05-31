package mum.model;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

/**
 *
 * @author Tina
 */
public class Order  implements Serializable{
   private String name;
   private double price;
   private int amount;
   private double sum;

    public void setSum(double sum){
        this.sum = sum;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getSum() {
        return sum;
    }
}
