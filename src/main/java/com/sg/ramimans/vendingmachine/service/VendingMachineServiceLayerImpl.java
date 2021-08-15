package com.sg.ramimans.vendingmachine.service;

import com.sg.ramimans.vendingmachine.dao.InventoryFileImpl;
import com.sg.ramimans.vendingmachine.dto.Change;
import com.sg.ramimans.vendingmachine.dto.Product;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Aug. 13, 2021
 * purpose: 
 */
public class VendingMachineServiceLayerImpl {
    
    private InventoryFileImpl dao;
    private BigDecimal balance;
    
    
    
    public VendingMachineServiceLayerImpl(InventoryFileImpl dao) {
        this.dao = dao;
        this.balance = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
    }
    
    public void processPurchase(String position) throws IOException {
        
        Product inInventory = this.getProduct(position);
        
        long inventoryQuantity = inInventory.getQuantity();
        BigDecimal purchasePrice = inInventory.getPrice();
        Boolean hasEnoughBalance = this.balance.compareTo(purchasePrice) >= 0;
        if (inventoryQuantity >= 0 && hasEnoughBalance) {
            this.decrementProductQuantity(position, inventoryQuantity);
            this.balance = this.balance.subtract(purchasePrice);
        }
        
    }
    
    public void addBalance(Change funds) {
        this.balance = this.balance.add(funds.getValue());
    }
    
    public BigDecimal getBalance() {
        return this.balance;
    }
    
    public void setBalance(BigDecimal newBalance) {
        this.balance = newBalance;
    }
    
    public Change returnChange() {
        BigDecimal value = this.getBalance();
        this.setBalance(value.subtract(value));
        Change changeToReturn = new Change(value);
        return changeToReturn;
    }
    
    public List<Product> getAllProducts() throws IOException{
        return this.dao.getAllProducts();
    }
    
    public Product getProduct(String position) {
        return this.dao.getProduct(position);
    }
    
    public void decrementProductQuantity(String position, long quantity) throws IOException {
        this.dao.setProductQuantity(position, quantity - 1);
    }
}