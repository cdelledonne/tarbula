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
public class Product implements Serializable{

    // campi
    private String name;
    private String description;
    private Tenant buyer;
    private float price;
    private int quantity;
    private List<Tenant> users;
    private Date date;

    /**
     * Private constructor, only inner methods can call it
     */
    private Product() {
    }

    /**
     * Static method used for instantiating an object of Product
     * @param name the name of the Product
     * @return a Product with the specified name
     */
    public static Product newProdotto(String name) {
        Product result = new Product();
        result.name = name;
        result.quantity = 1;
        result.date = new Date();
        return result;
    }

    /**
     * Method for adding a description to a Product
     * @param description the description to be added
     * @return the Product which called this method, with the specified description
     */
    public Product addDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Method for adding a price to a Product
     * @param price the price to be added
     * @return the Product which called this method, with the specified price
     */
    public Product addPrice(float price) {
        this.price = price;
        return this;
    }

    /**
     * Method for adding a quantity to a Product
     * @param quantity the quantity to be added
     * @return the Product which called this method, with the specified quantity
     */
    public Product addQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Method for adding a buyer to a Product
     * @param buyer the buyer to be added
     * @return the Product which called this method, with the specified buyer
     */
    public Product addBuyer(Tenant buyer) {
        this.buyer = buyer;
        return this;
    }

    public Product addUsers(List<Tenant> users) {
        this.users = users;
        return this;
    }

    /**
     * Method for getting the name of a Product
     * @return the name of the Product which called this method
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method for getting the description of a Product
     * @return the description of the Product which called this method
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Method for getting the price of a Product
     * @return the price of the Product which called this method
     */
    public float getPrice(){
        return this.price;
    }

    /**
     * Method for getting the quantity of a Product
     * @return the quantity of the Product which called this method
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Method for getting the buyer of a Product
     * @return the buyer of the Product which called this method
     */
    public Tenant getBuyer() {
        return this.buyer;
    }

    public List<Tenant> getUsers() {
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
