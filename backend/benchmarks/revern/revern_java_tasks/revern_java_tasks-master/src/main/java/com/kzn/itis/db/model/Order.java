package com.kzn.itis.db.model;

/**
 * Created by Алмаз on 30.03.2015.
 */
public class Order {
    int id;
    int customerid;
    int salerspersonalid;
    int totalamount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getSalerspersonalid() {
        return salerspersonalid;
    }

    public void setSalerspersonalid(int salerspersonalid) {
        this.salerspersonalid = salerspersonalid;
    }

    public int getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(int totalamount) {
        this.totalamount = totalamount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (customerid != order.customerid) return false;
        if (id != order.id) return false;
        if (salerspersonalid != order.salerspersonalid) return false;
        if (totalamount != order.totalamount) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + customerid;
        result = 31 * result + salerspersonalid;
        result = 31 * result + totalamount;
        return result;
    }
}

