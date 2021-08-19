package com.sg.ramimans.vendingmachine.dto;


import java.math.BigDecimal;
import java.util.Objects;

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

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.position);
        hash = 37 * hash + Objects.hashCode(this.title);
        hash = 37 * hash + Objects.hashCode(this.price);
        hash = 37 * hash + (int) (this.quantity ^ (this.quantity >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }

    
}
