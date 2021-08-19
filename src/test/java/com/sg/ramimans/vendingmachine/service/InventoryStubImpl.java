package com.sg.ramimans.vendingmachine.service;

import com.sg.ramimans.vendingmachine.dao.Inventory;
import com.sg.ramimans.vendingmachine.dao.InventoryPersistenceException;
import com.sg.ramimans.vendingmachine.dto.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Aug. 18, 2021
 * purpose: 
 */
public class InventoryStubImpl implements Inventory {
    
    public Product onlyProduct;
    
    public InventoryStubImpl () {
        this.onlyProduct = new Product("A1", "KitKat", new BigDecimal("1.25").setScale(2, RoundingMode.HALF_UP), 10);
    }
    
    public InventoryStubImpl (Product product) {
        this.onlyProduct = product;
    }

    @Override
    public List<Product> getAllProducts() throws InventoryPersistenceException {
        List<Product> products = new ArrayList<>();
        products.add(onlyProduct);
        return products;
    }

    @Override
    public Product addProduct(Product product) throws InventoryPersistenceException {
        if (this.onlyProduct.equals(product)) {
            return this.onlyProduct;
        } else {
            return null;
        }
    }

    @Override
    public Product getProduct(String position) throws InventoryPersistenceException {
        if (this.onlyProduct.getPosition().equals(position)) {
            return this.onlyProduct;
        } else {
            return null;
        }
    }

    @Override
    public Product setProductQuantity(String position, long quantity) throws InventoryPersistenceException {
        if (this.onlyProduct.getPosition().equals(position)) {
            this.onlyProduct.setQuantity(quantity);
            return this.onlyProduct;
        } else {
            return null;
        }
    }

}
