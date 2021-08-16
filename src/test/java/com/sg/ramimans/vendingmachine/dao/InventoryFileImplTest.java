/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.ramimans.vendingmachine.dao;

import com.sg.ramimans.vendingmachine.dto.Product;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author rmans
 */
public class InventoryFileImplTest {
    public InventoryFileImpl testDao;
    public InventoryFileImplTest() {
    }
    
    @BeforeEach
    public void setUpClass() throws Exception{
        String testInventory = "test_inventory.txt";
        new FileWriter(testInventory);
        this.testDao = new InventoryFileImpl(testInventory);
    }
    @Test
    public void testAddGetProduct() throws Exception{
        String position = "A1";
        String title = "KitKat";
        BigDecimal price = new BigDecimal("1.25").setScale(2, RoundingMode.HALF_UP);
        long quantity = 10;
        Product testAddProduct = new Product(position, title, price, quantity);
        this.testDao.addProduct(testAddProduct);
        Product testGetProduct = this.testDao.getProduct(position);
        
        assertEquals(testAddProduct.getPosition(), testGetProduct.getPosition(), "Check product position");
        assertEquals(testAddProduct.getTitle(), testGetProduct.getTitle(), "Check product title");
        assertEquals(testAddProduct.getPrice(), testGetProduct.getPrice(), "Check product price");
        assertEquals(testAddProduct.getQuantity(), testGetProduct.getQuantity(), "Check product quantity");
        
        
    }
    
    
}
