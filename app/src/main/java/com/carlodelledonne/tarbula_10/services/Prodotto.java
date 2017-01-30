package com.carlodelledonne.tarbula_10.services;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This is a service class, useful to identify any aspect of a product
 * @author Carlo
 * @author Filippo
 * @author Edoardo
 * @version 1.0
 */
public class Prodotto implements Serializable{

    // campi
    private String name;
    private String description;
    private Inquilino buyer;
    private float price;
    private int quantity;
    private List<Inquilino> users;
    private Date date;

    /**
     * Private constructor, only inner methods can call it
     */
    private Prodotto() {
    }

    /**
     * Static method used for instantiating an object of Prodotto
     * @param name the name of the Prodotto
     * @return a Prodotto with the specified name
     */
    public static Prodotto newProdotto(String name) {
        Prodotto result = new Prodotto();
        result.name = name;
        result.quantity = 1;
        result.date = new Date();
        return result;
    }

    /**
     * Method for adding a description to a Prodotto
     * @param description the description to be added
     * @return the Prodotto which called this method, with the specified description
     */
    public Prodotto addDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Method for adding a price to a Prodotto
     * @param price the price to be added
     * @return the Prodotto which called this method, with the specified price
     */
    public Prodotto addPrice(float price) {
        this.price = price;
        return this;
    }

    /**
     * Method for adding a quantity to a Prodotto
     * @param quantity the quantity to be added
     * @return the Prodotto which called this method, with the specified quantity
     */
    public Prodotto addQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Method for adding a buyer to a Prodotto
     * @param buyer the buyer to be added
     * @return the Prodotto which called this method, with the specified buyer
     */
    public Prodotto addBuyer(Inquilino buyer) {
        this.buyer = buyer;
        return this;
    }

    public Prodotto addUsers(List<Inquilino> users) {
        this.users = users;
        return this;
    }

    /**
     * Method for getting the name of a Prodotto
     * @return the name of the Prodotto which called this method
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method for getting the description of a Prodotto
     * @return the description of the Prodotto which called this method
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Method for getting the price of a Prodotto
     * @return the price of the Prodotto which called this method
     */
    public float getPrice(){
        return this.price;
    }

    /**
     * Method for getting the quantity of a Prodotto
     * @return the quantity of the Prodotto which called this method
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Method for getting the buyer of a Prodotto
     * @return the buyer of the Prodotto which called this method
     */
    public Inquilino getBuyer() {
        return this.buyer;
    }

    public List<Inquilino> getUsers() {
        return this.users;
    }

    public Date getDate() {
        return this.date;
    }

    public String getDateString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        return df.format(this.date);
    }

    public String getPriceString() {
        String s = String.valueOf(this.price);
        int i;
        for (i=0; i<s.length(); i++) {
            if(s.charAt(i) == '.')
                break;
        }
        int decimals = s.length() -(i+1);
        if (decimals == 1)
            return (s + "0");
        else return s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
