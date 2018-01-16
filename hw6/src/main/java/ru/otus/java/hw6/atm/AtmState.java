package ru.otus.java.hw6.atm;

import java.util.Map;

public class AtmState {

    private Map<MoneyTokens, Integer> savedCashState;

    public AtmState(Map<MoneyTokens, Integer> cash) {
        this.savedCashState = cash;
    }

    public Map<MoneyTokens, Integer> getState() {
        return savedCashState;
    }
}
