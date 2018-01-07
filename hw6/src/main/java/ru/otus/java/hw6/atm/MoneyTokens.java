package ru.otus.java.hw6.atm;

public enum MoneyTokens {

    ONE_RUBLE(1),
    TWO_RUBLES(2),
    TEN_RUBLES(10),
    HUNDRED_RUBLES(100),
    FIVE_HUNDRED_RUBLES(1000);

    private final int moneyTokenValue;

    MoneyTokens(int moneyTokenValue) {
        this.moneyTokenValue = moneyTokenValue;
    }

    public int getValue() {
        return moneyTokenValue;
    }
}
