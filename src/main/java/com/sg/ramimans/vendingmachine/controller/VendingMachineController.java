package com.sg.ramimans.vendingmachine.controller;

import com.sg.ramimans.vendingmachine.dao.InventoryPersistenceException;
import com.sg.ramimans.vendingmachine.dto.Change;
import com.sg.ramimans.vendingmachine.dto.Product;
import com.sg.ramimans.vendingmachine.service.InsufficientFundsException;
import com.sg.ramimans.vendingmachine.service.NoItemInventoryException;
import com.sg.ramimans.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.ramimans.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.sg.ramimans.vendingmachine.userio.VendingMachineView;
import java.util.List;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Aug. 16, 2021
 * purpose: 
 */
public class VendingMachineController {
    
    private final VendingMachineServiceLayer service;
    private final VendingMachineView view;
    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.view = view;
        this.service = service;
    }
    public void run() throws InsufficientFundsException, NoItemInventoryException, InventoryPersistenceException {
        List<Product> menuItems = this.service.getAllProducts();
        this.view.printMenuItems(menuItems);
        
        if (this.view.userAgrees("select an item")) {
            Change deposit = this.view.getUserDeposit();
            this.service.addBalance(deposit);
        } else {
            return;
        }
        String userSelection = this.view.getUserSelection(menuItems);
        Boolean notDoneYet = true;
        do {
            try {
                this.service.processPurchase(userSelection);
                this.view.displayDispenseSuccess(this.service.getProduct(userSelection));
                notDoneYet = false;
            } catch (NoItemInventoryException e) {
                this.view.displayMessage(e.getMessage());
                if (this.view.userAgrees("choose another item")) {
                    userSelection = this.view.getUserSelection(menuItems);
                } else {
                    notDoneYet = false;
                }
            } catch (InsufficientFundsException e) {
                this.view.displayMessage(e.getMessage());
                if (this.view.userAgrees("add more balance")) {
                    Change deposit = this.view.getUserDeposit();
                    this.service.addBalance(deposit);
                } else {
                    notDoneYet = false;
                }
            }
        } while (notDoneYet);
        
        Change userChange = this.service.returnChange();
        
        this.view.displayChangeReturned(userChange.getChange());
        
    }
    
    
}


