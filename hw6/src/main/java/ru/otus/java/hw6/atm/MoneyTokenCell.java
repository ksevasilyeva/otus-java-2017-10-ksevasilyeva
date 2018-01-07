package ru.otus.java.hw6.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoneyTokenCell {

    private static final Logger LOG = LoggerFactory.getLogger(MoneyTokenCell.class);

    private int maxCapacity;
    private int tokenAmount;
    private MoneyTokens moneyTokenValue;

    public MoneyTokenCell(int maxCapacity, int tokenAmount, MoneyTokens moneyTokenValue) {
        this.maxCapacity = maxCapacity;
        this.tokenAmount = tokenAmount;
        this.moneyTokenValue = moneyTokenValue;
    }

    public int getBalance() {
        return tokenAmount * moneyTokenValue.getValue();
    }

    public void addMoneyTokenToCell() {
        LOG.info("Proceeding Money token of value [{}]", moneyTokenValue.getValue());
        tokenAmount++;
    }

    public void withdrawTokenFromCell() {
        if (tokenAmount - 1 >= 0) {
            LOG.info("Withdrawing token of value {}", moneyTokenValue.getValue());
            tokenAmount --;
        } else {
            LOG.info("Cannot withdraw token of {} value: not enough money tokens", moneyTokenValue);
        }
    }

    public MoneyTokens getMoneyToken() {
        return moneyTokenValue;
    }

    public int getTokenValue() {
        return moneyTokenValue.getValue();
    }

    public int getTokenAmount() {
        return tokenAmount;
    }

    @Override
    public String toString() {
        return tokenAmount + " x " + getTokenValue();
    }
}
