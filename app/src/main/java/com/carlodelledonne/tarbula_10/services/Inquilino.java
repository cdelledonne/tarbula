package com.carlodelledonne.tarbula_10.services;

import java.io.Serializable;

/**
 * Created by Carlo on 24/11/15.
 */
public class Inquilino implements Serializable, Comparable<Inquilino>{

    private String name;
    private float balance;
    private float totalExpense;
    private int boughtProduct;

    private Inquilino() {
    }

    public static Inquilino newInquilino(String name) {
        Inquilino result = new Inquilino();
        result.name = name;
        result.balance = 0;
        result.totalExpense = 0;
        result.boughtProduct = 0;
        return result;
    }

    public String getName() {
        return this.name;
    }

    public float getBalance() {
        return this.balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(float totalExpense) {
        this.totalExpense = totalExpense;
    }

    public int getBoughtProduct() {
        return boughtProduct;
    }

    public void setBoughtProduct(int boughtProduct) {
        this.boughtProduct = boughtProduct;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Inquilino another) {
        if (Math.round(this.getBalance()*100) > Math.round(another.getBalance()*100))
            return -1;
        else if (Math.round(this.getBalance() * 100) < Math.round(another.getBalance() * 100))
            return +1;
        else return 0;
    }
}
