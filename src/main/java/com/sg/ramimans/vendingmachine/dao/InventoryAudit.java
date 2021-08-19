/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.ramimans.vendingmachine.dao;

/**
 *
 * @author rmans
 */
public interface InventoryAudit {
    void writeAuditEntry(String entry)throws InventoryPersistenceException;
}
