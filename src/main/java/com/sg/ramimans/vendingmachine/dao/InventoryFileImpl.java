package com.sg.ramimans.vendingmachine.dao;

import com.sg.ramimans.vendingmachine.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Aug. 13, 2021 purpose:
 */
public class InventoryFileImpl {

    public final String INVENTORY_FILE;
    public static final String DELIMITER = "::";
    private final List<Product> inventoryList = new ArrayList<>();

    public InventoryFileImpl() {
        this.INVENTORY_FILE = "products.txt";
        
        
    }
    
    public InventoryFileImpl(String fileName) {
        this.INVENTORY_FILE = fileName;
    }
    
    public List<Product> getAllProducts () throws IOException {
        this.loadInventory();
        return this.inventoryList.stream().collect(Collectors.toList());
    }
    
    public Product getProduct(String position) {
       
        return this.inventoryList.stream().filter((p) -> p.getPosition().equals(position)).findFirst().get();
    }
            
    public void loadInventory() throws IOException {
        
        Scanner fileInput;
        try {
            fileInput = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new IOException(e);
        } 
        inventoryList.clear();
        String currentLine;
        Product currentProduct;
        while (fileInput.hasNextLine()) {
            currentLine = fileInput.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            inventoryList.add(currentProduct);
            
        }
        
    }
    
    public void setProductQuantity(String position, long quantity) throws IOException {
        Product productToSet = this.getProduct(position);
        productToSet.setQuantity(quantity);
        this.writeInventory();
    }
    
    public void writeInventory() throws IOException {
        PrintWriter fileOutput; 
        try {
            fileOutput = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new IOException(e);
        }
        this.inventoryList.stream().forEach((product) -> {
            String entry = marshallProduct(product);
            fileOutput.println(entry);
            fileOutput.flush();
        });
        fileOutput.close();
        
    }
    
    private Product unmarshallProduct(String entry) {
        Product unmarshalledProduct;
        String[] fieldArray = entry.split(DELIMITER);
        String productPosition = fieldArray[0];
        String productName = fieldArray[1];
        BigDecimal productValue = new BigDecimal(fieldArray[2]);
        Long productQuantity = Long.parseLong(fieldArray[3]);
        unmarshalledProduct = new Product(productPosition, productName, productValue, productQuantity);
        return unmarshalledProduct;
    }
    
    private String marshallProduct(Product product) {
        
        String productToEntry = product.getPosition() + DELIMITER;
        productToEntry += product.getTitle() + DELIMITER;
        productToEntry += product.getPrice() + DELIMITER;
        productToEntry += product.getQuantity();
        return productToEntry;
    }
    
    
    
}
