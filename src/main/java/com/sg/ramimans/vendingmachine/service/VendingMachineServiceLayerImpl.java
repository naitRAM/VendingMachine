package com.sg.ramimans.vendingmachine.service;

import com.sg.ramimans.vendingmachine.dao.Inventory;
import com.sg.ramimans.vendingmachine.dao.InventoryAudit;
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
 * @author Rami Mansieh email: rmansieh@gmail.com data: Aug. 13, 2021 purpose:
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private Inventory dao;
    private InventoryAudit audit;
    private BigDecimal balance;

    public VendingMachineServiceLayerImpl(Inventory dao, InventoryAudit audit) {
        this.dao = dao;
        this.audit = audit;
        this.balance = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
    }

    @Override
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

    @Override

    public void addBalance(Change funds) throws InventoryPersistenceException {
        BigDecimal value = funds.getValue();
        BigDecimal currentBalance = this.getBalance();
        this.setBalance(currentBalance.add(value));
        BigDecimal updatedBalance = this.getBalance();
        audit.writeAuditEntry("Added $" + value.toString() + " to balance. Balance = $" + updatedBalance);
    }

    @Override
    public BigDecimal getBalance() {
        return this.balance;

    }

    
    private void setBalance(BigDecimal newBalance) {
        this.balance = newBalance;
    }

    @Override

    public Change returnChange() throws InventoryPersistenceException {
        BigDecimal value = this.getBalance();
        BigDecimal currentBalance = this.getBalance();
        this.setBalance(currentBalance.subtract(value));
        Change changeToReturn = new Change(value);
        BigDecimal updatedBalance = this.getBalance();
        audit.writeAuditEntry("Returned $" + changeToReturn.getValue().toString() + " to user. Balance = $" + updatedBalance);
        return changeToReturn;
    }

    @Override

    public List<Product> getAllProducts() throws InventoryPersistenceException {
        return this.dao.getAllProducts();
    }

    @Override

    public Product getProduct(String position) throws InventoryPersistenceException {
        return this.dao.getProduct(position);
    }

    private void decrementProductQuantity(String position, long quantity) throws InventoryPersistenceException {
        this.dao.setProductQuantity(position, quantity - 1);
    }
}
