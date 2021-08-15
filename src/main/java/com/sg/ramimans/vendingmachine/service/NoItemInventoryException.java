package com.sg.ramimans.vendingmachine.service;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Aug. 15, 2021
 * purpose: 
 */
public class NoItemInventoryException extends Exception{
    public NoItemInventoryException(String message) {
        super(message);
    }
    public NoItemInventoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
