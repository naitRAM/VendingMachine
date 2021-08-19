/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.ramimans.vendingmachine.dao;

import com.sg.ramimans.vendingmachine.dto.Product;
import java.util.List;

/**
 *
 * @author rmans
 */
public interface Inventory {
    List<Product> getAllProducts () throws InventoryPersistenceException;
    Product addProduct(Product product) throws InventoryPersistenceException;
    Product getProduct(String position) throws InventoryPersistenceException;
    Product setProductQuantity(String position, long quantity) throws InventoryPersistenceException;
}
