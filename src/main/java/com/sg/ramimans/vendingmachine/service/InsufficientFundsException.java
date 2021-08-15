package com.sg.ramimans.vendingmachine.service;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Aug. 15, 2021
 * purpose: 
 */
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException (String message) {
        super(message);
    }
    public InsufficientFundsException (String message, Throwable cause) {
        super(message, cause);
    }
}
