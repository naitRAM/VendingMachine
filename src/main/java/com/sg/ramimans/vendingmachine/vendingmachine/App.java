package com.sg.ramimans.vendingmachine.vendingmachine;

import com.sg.ramimans.vendingmachine.controller.VendingMachineController;
import com.sg.ramimans.vendingmachine.dao.Inventory;
import com.sg.ramimans.vendingmachine.dao.InventoryAuditFileImpl;
import com.sg.ramimans.vendingmachine.dao.InventoryFileImpl;
import com.sg.ramimans.vendingmachine.dao.InventoryPersistenceException;
import com.sg.ramimans.vendingmachine.service.InsufficientFundsException;
import com.sg.ramimans.vendingmachine.service.NoItemInventoryException;
import com.sg.ramimans.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.ramimans.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.sg.ramimans.vendingmachine.userio.UserIO;
import com.sg.ramimans.vendingmachine.userio.UserIOConsoleImpl;
import com.sg.ramimans.vendingmachine.userio.VendingMachineView;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Aug. 13, 2021 purpose:
 */
public class App {

    public static void main(String[] args) throws InventoryPersistenceException, NoItemInventoryException, InsufficientFundsException {
        
        Inventory dao = new InventoryFileImpl();
        InventoryAuditFileImpl audit = new InventoryAuditFileImpl();
        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao, audit);
        UserIO io = new UserIOConsoleImpl();
        VendingMachineView view = new VendingMachineView(io);
        VendingMachineController controller = new VendingMachineController(service, view);
        controller.run();
        
    }
}
