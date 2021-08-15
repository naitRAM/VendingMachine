package com.sg.ramimans.vendingmachine.dao;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Aug. 15, 2021
 * purpose: 
 */
public class InventoryPersistenceException extends Exception{
    public InventoryPersistenceException(String message) {
        super(message);
    }
    public InventoryPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
