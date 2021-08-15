package com.sg.ramimans.vendingmachine.dto;


import java.math.BigDecimal;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Aug. 13, 2021
 * purpose: 
 */
public class Product {
    String position;
    String title;
    BigDecimal price;
    long quantity;
    
    
    public Product (String position, String productName, BigDecimal productPrice, long productQuantity) {
        this.position = position;
        this.title = productName;
        this.price = productPrice;
        this.quantity = productQuantity;
    }

    public String getPosition() {
        return position;
    }

    public String getTitle() {
        return this.title;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public long getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(long productQuantity) {
        this.quantity = productQuantity;
    }
    
}
