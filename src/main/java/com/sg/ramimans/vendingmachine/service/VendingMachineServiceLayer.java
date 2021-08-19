/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.ramimans.vendingmachine.service;

import com.sg.ramimans.vendingmachine.dao.InventoryPersistenceException;
import com.sg.ramimans.vendingmachine.dto.Change;
import com.sg.ramimans.vendingmachine.dto.Product;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author rmans
 */
public interface VendingMachineServiceLayer {
    void processPurchase(String position) throws InventoryPersistenceException, NoItemInventoryException, InsufficientFundsException;
    void addBalance(Change funds) throws InventoryPersistenceException ;
    Change returnChange() throws InventoryPersistenceException;
    List<Product> getAllProducts() throws InventoryPersistenceException;
    Product getProduct(String position) throws InventoryPersistenceException;
    BigDecimal getBalance();
    
}
