package com.sg.ramimans.vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Aug. 15, 2021 purpose:
 */
public class InventoryAuditFileImpl implements InventoryAudit{

    public static final String AUDIT_FILE = "audit.txt";
    private PrintWriter auditOutput;
    private LocalDateTime timestamp;
    
    @Override
    public void writeAuditEntry(String entry) throws InventoryPersistenceException {
        try {
            this.auditOutput = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new InventoryPersistenceException("Could not persist audit entry information");
        }
        timestamp = LocalDateTime.now();
        this.auditOutput.println(timestamp + ": " + entry);
        this.auditOutput.flush();
        this.auditOutput.close();
    }
}

