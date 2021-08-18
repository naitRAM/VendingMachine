package com.sg.ramimans.vendingmachine.vendingmachine;

import com.sg.ramimans.vendingmachine.controller.VendingMachineController;
import com.sg.ramimans.vendingmachine.dao.InventoryAuditFileImpl;
import com.sg.ramimans.vendingmachine.dto.Product;
import com.sg.ramimans.vendingmachine.dao.InventoryFileImpl;
import com.sg.ramimans.vendingmachine.dao.InventoryPersistenceException;
import com.sg.ramimans.vendingmachine.dto.Change;
import com.sg.ramimans.vendingmachine.service.InsufficientFundsException;
import com.sg.ramimans.vendingmachine.service.NoItemInventoryException;
import com.sg.ramimans.vendingmachine.service.VendingMachineServiceLayerImpl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Aug. 13, 2021 purpose:
 */
public class App {

    public static void main(String[] args) throws InventoryPersistenceException, NoItemInventoryException, InsufficientFundsException {
        
        InventoryFileImpl testInventory = new InventoryFileImpl();
        InventoryAuditFileImpl testAudit = new InventoryAuditFileImpl();
        
        VendingMachineServiceLayerImpl testService = new VendingMachineServiceLayerImpl(testInventory, testAudit);
        
        VendingMachineController controller = new VendingMachineController(testService);
        controller.run();
        /*
        // getallProducts() will load the inventory
        List<Product> inventoryList = testService.getAllProducts();
        
        // print service layer balance, should be $0.00 since user did not deposit yet
        System.out.println("Balance: $" + testService.getBalance()+ "\n");
        
        // list all products from inventory including price and quantity
        for (Product product : inventoryList) {
            System.out.println(product.getTitle() + " - $" + product.getPrice() + " - " + product.getQuantity());
        }
        
        // add funds from user to service layer and verify value
        System.out.println("\nAdd $2.73 to balance");
        BigDecimal funds = new BigDecimal("2.73").setScale(2, RoundingMode.HALF_UP);
        Change fundsToAdd = new Change(funds);
        testService.addBalance(fundsToAdd);
        System.out.println("\nBalance: $" + testService.getBalance());
        
        // purchase item in position "C1", should decrement KitKat amount in inventory (DAO)
        System.out.println("\nUser purchases KitKat \n");
        testService.processPurchase("C1");
        
        // show products and counts again, notice KitKat quantity
        inventoryList = testInventory.getAllProducts();
        for (Product product : inventoryList) {
            System.out.println(product.getTitle() + " - $" + product.getPrice() + " - " + product.getQuantity());
        }
        
        // show balance after removing price of KitKat from it
        System.out.println("\nBalance: $" + testService.getBalance());
        
        // return change from service layer, balance should be $0.00 after this
        Change changeDue = testService.returnChange();
        System.out.println("\nReturning remaining balance to user");
        
        // display coin count for each coin type
        Map<String, BigDecimal> coinsToCount = changeDue.getChange();
        System.out.println("\nquarter count = " + coinsToCount.get("QUARTER"));
        System.out.println("dime count = " + coinsToCount.get("DIME"));
        System.out.println("nickel count = " + coinsToCount.get("NICKEL"));
        System.out.println("penny count = " + coinsToCount.get("PENNY"));
       
        // should display $0.00
        System.out.println("\nBalance: $" + testService.getBalance());
        */
    }
}
