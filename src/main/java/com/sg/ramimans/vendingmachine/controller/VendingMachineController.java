package com.sg.ramimans.vendingmachine.controller;

import com.sg.ramimans.vendingmachine.dao.InventoryFileImpl;
import com.sg.ramimans.vendingmachine.dao.InventoryPersistenceException;
import com.sg.ramimans.vendingmachine.dto.Change;
import com.sg.ramimans.vendingmachine.dto.Product;
import com.sg.ramimans.vendingmachine.service.InsufficientFundsException;
import com.sg.ramimans.vendingmachine.service.NoItemInventoryException;
import com.sg.ramimans.vendingmachine.service.VendingMachineServiceLayerImpl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Aug. 16, 2021
 * purpose: 
 */
public class VendingMachineController {
    
    private final VendingMachineServiceLayerImpl service;
    public VendingMachineController(VendingMachineServiceLayerImpl service) {
        
        this.service = service;
    }
    public void run() throws InsufficientFundsException, NoItemInventoryException, InventoryPersistenceException {
        List<Product> menuItems = this.service.getAllProducts();
        this.printMenuItems(menuItems);
        
        if (this.userAgrees("select an item")) {
            Change deposit = this.getUserDeposit();
            this.service.addBalance(deposit);
        } else {
            return;
        }
        String userSelection = this.getUserSelection();
        Boolean notDoneYet = true;
        do {
            try {
                service.processPurchase(userSelection);
                System.out.println("Dispensing " + userSelection);
                notDoneYet = false;
            } catch (NoItemInventoryException e) {
                System.out.println(e.getMessage());
                if (this.userAgrees("choose another item")) {
                    userSelection = this.getUserSelection();
                } else {
                    notDoneYet = false;
                }
            } catch (InsufficientFundsException e) {
                System.out.println(e.getMessage());
                if (this.userAgrees("add more balance")) {
                    Change deposit = this.getUserDeposit();
                    service.addBalance(deposit);
                } else {
                notDoneYet = false;
                }
            }
        } while (notDoneYet);
        
        Change userChange = this.service.returnChange();
        
        this.printChangeReturned(userChange.getChange());
        
    }
    
    public void printMenuItems(List<Product> products) {
        
        String DELIMITER = "  ";
        for (Product product : products) {
            if (product.getQuantity() > 0) {
                System.out.print(product.getPosition() + DELIMITER);
                System.out.print(product.getTitle() + DELIMITER);
                System.out.print("$" + product.getPrice() + DELIMITER);
                System.out.println();
            } 
        }
    }
    
    public String getUserSelection() {
        Scanner userInput = new Scanner(System.in);
        String userChoice;
        System.out.println("Choose item by position (e.g. A1)");
        userChoice = userInput.nextLine();
        return userChoice;
    }
    
    public Boolean userAgrees(String action) {
        Scanner userInput = new Scanner(System.in);
        String userResponse;
        Boolean willSelect = false;
        System.out.println("Enter 'y' to " + action + " or 'n' to exit:");
        userResponse = userInput.nextLine();
        if (userResponse.equals("y")) {
            willSelect = true;
        } 
        return willSelect;

    }
    
    public Change getUserDeposit(){
        Scanner userInput = new Scanner(System.in);
        String userAmount;
        System.out.println("Deposit funds to continue (e.g. 1.50): ");
        userAmount = userInput.nextLine();
        Change amount = new Change(new BigDecimal(userAmount).setScale(2, RoundingMode.HALF_UP));
        return amount;
    }
    
    public void printChangeReturned(Map<String, BigDecimal> coinsToCount) {
        
        System.out.println("Returning change:");
        System.out.println("-----------------");
        System.out.println("\nquarter count = " + coinsToCount.get("QUARTER"));
        System.out.println("dime count = " + coinsToCount.get("DIME"));
        System.out.println("nickel count = " + coinsToCount.get("NICKEL"));
        System.out.println("penny count = " + coinsToCount.get("PENNY"));
    }
}


