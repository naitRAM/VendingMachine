package com.sg.ramimans.vendingmachine.service;

import com.sg.ramimans.vendingmachine.dao.InventoryAuditFileImpl;
import com.sg.ramimans.vendingmachine.dao.InventoryFileImpl;
import com.sg.ramimans.vendingmachine.dao.InventoryPersistenceException;
import com.sg.ramimans.vendingmachine.dto.Change;
import com.sg.ramimans.vendingmachine.dto.Product;
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
    private InventoryAuditFileImpl audit;
    private BigDecimal balance;
    
    
    
    public VendingMachineServiceLayerImpl(InventoryFileImpl dao, InventoryAuditFileImpl audit) {
        this.dao = dao;
        this.audit = audit;
        this.balance = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
    }
    
    public void processPurchase(String position) throws InventoryPersistenceException, NoItemInventoryException, InsufficientFundsException {
        
        Product inInventory = this.getProduct(position);
        
        long inventoryQuantity = inInventory.getQuantity();
        if (inventoryQuantity == 0) {
            throw new NoItemInventoryException("Could not find item in " + position);
        }
        BigDecimal purchasePrice = inInventory.getPrice();
        Boolean hasEnoughBalance = this.balance.compareTo(purchasePrice) >= 0;
        if (!hasEnoughBalance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
            this.decrementProductQuantity(position, inventoryQuantity);
            this.balance = this.balance.subtract(purchasePrice);
            audit.writeAuditEntry("Sold " + inInventory.getTitle() + " from " + position + " for $" + purchasePrice + " to user. Balance = $" 
                + this.getBalance());
        
        
    }
    
    public void addBalance(Change funds) throws InventoryPersistenceException {
        BigDecimal value = funds.getValue();
        this.balance = this.balance.add(funds.getValue());
        audit.writeAuditEntry("Added $" + value.toString() + " to balance. Balance = $" + this.getBalance());
    }
    
    public BigDecimal getBalance() {
        return this.balance;
        
    }
    
    public void setBalance(BigDecimal newBalance) {
        this.balance = newBalance;
    }
    
    public Change returnChange() throws InventoryPersistenceException{
        BigDecimal value = this.getBalance();
        this.setBalance(value.subtract(value));
        Change changeToReturn = new Change(value);
        audit.writeAuditEntry("Returned $" + changeToReturn.getValue().toString() + " to user. Balance = $" + this.getBalance());
        return changeToReturn;
    }
    
    public List<Product> getAllProducts() throws InventoryPersistenceException{
        return this.dao.getAllProducts();
    }
    
    public Product getProduct(String position) {
        return this.dao.getProduct(position);
    }
    
    public void decrementProductQuantity(String position, long quantity) throws InventoryPersistenceException {
        this.dao.setProductQuantity(position, quantity - 1);
    }
}