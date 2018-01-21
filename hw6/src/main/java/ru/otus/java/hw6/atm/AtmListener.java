package ru.otus.java.hw6.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AtmListener {

    private static Logger LOG = LoggerFactory.getLogger(AtmListener.class);

    public void newAtmCreated(int initBalance) {
        LOG.info("New Atm created with init balance: {}", initBalance);
    }

    public void atmReseted() {
        LOG.info("Atm was successfully reset to it's initial state");
    }

    public void cashWithdrawed(int withdrawedAmount) {
        LOG.info("Successfully withdrew: {}", withdrawedAmount);
    }

    public void cashDeposit(int depositAmount) {
        LOG.info("Successfully deposit: {}", depositAmount);
    }

    public void operationFailed(String message) {
        LOG.error("Failed to execute operation: {}", message);
    }
}
