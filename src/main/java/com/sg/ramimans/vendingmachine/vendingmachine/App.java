package com.sg.ramimans.vendingmachine.vendingmachine;

import com.sg.ramimans.vendingmachine.dto.Product;
import com.sg.ramimans.vendingmachine.dao.InventoryFileImpl;
import com.sg.ramimans.vendingmachine.dto.Change;
import com.sg.ramimans.vendingmachine.service.VendingMachineServiceLayerImpl;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Aug. 13, 2021 purpose:
 */
public class App {

    public static void main(String[] args) throws IOException {
        
        InventoryFileImpl testInventory = new InventoryFileImpl();
        VendingMachineServiceLayerImpl testService = new VendingMachineServiceLayerImpl(testInventory);
        List<Product> inventoryList = testService.getAllProducts();
        System.out.println("Balance: $" + testService.getBalance());
        for (Product product : inventoryList) {
            System.out.println(product.getTitle() + " - $" + product.getPrice() + " - " + product.getQuantity());
        }
        
        BigDecimal funds = new BigDecimal("2.73").setScale(2, RoundingMode.HALF_UP);
        Change fundsToAdd = new Change(funds);
        testService.addBalance(fundsToAdd);
        System.out.println("Balance: $" + testService.getBalance());
        testService.processPurchase("C1");
        inventoryList = testInventory.getAllProducts();
        for (Product product : inventoryList) {
            System.out.println(product.getTitle() + " - $" + product.getPrice() + " - " + product.getQuantity());
        }
        
        System.out.println("Balance: $" + testService.getBalance());
        Change changeDue = testService.returnChange();
        
       
        
        Map<String, BigDecimal> coinsToCount = changeDue.getChange();
        System.out.println(coinsToCount.get("QUARTER"));
        System.out.println(coinsToCount.get("DIME"));
        System.out.println(coinsToCount.get("NICKEL"));
        System.out.println(coinsToCount.get("PENNY"));
        
        System.out.println("Balance: $" + testService.getBalance());
    }
}
