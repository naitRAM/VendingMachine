package com.sg.ramimans.vendingmachine.service;

import com.sg.ramimans.vendingmachine.dao.Inventory;
import com.sg.ramimans.vendingmachine.dao.InventoryAudit;
import com.sg.ramimans.vendingmachine.dao.InventoryPersistenceException;
import com.sg.ramimans.vendingmachine.dto.Product;
import java.util.List;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Aug. 18, 2021
 * purpose: 
 */
public class InventoryAuditStubImpl implements InventoryAudit{

    @Override
    public void writeAuditEntry(String entry) throws InventoryPersistenceException {
        // if only every method could be empty like this
    }

    
    
}
