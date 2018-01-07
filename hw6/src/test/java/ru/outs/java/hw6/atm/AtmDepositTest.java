package ru.outs.java.hw6.atm;

import org.junit.Test;
import ru.otus.java.hw6.atm.ATM;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AtmDepositTest {

    @Test
    public void shouldDepositOneMoneyToken() {
        ATM atm = ATM.initAtmWithoutMoney();
        atm.depositCash(10);

        assertThat(atm.getBalance(), is(10));
        assertThat(atm.getTokenCellByValue(10).getBalance(), is(10));
    }

    @Test
    public void shouldDepositMultipleMoneyTokens() {
        ATM atm = ATM.initAtmWithoutMoney();
        atm.depositCash(10, 100, 1);

        assertThat(atm.getBalance(), is(111));
        assertThat(atm.getTokenCellByValue(10).getBalance(), is(10));
        assertThat(atm.getTokenCellByValue(100).getBalance(), is(100));
        assertThat(atm.getTokenCellByValue(1).getBalance(), is(1));
        assertThat(atm.getTokenCellByValue(2).getBalance(), is(0));
    }

    @Test
    public void shouldNotProceedInvalidToken() {
        ATM atm = ATM.initAtmWithoutMoney();
        atm.depositCash(11);

        assertThat(atm.getBalance(), is(0));
    }

    @Test
    public void shouldNotProceedSumWithInvalidToken() {
        ATM atm = ATM.initAtmWithoutMoney();
        atm.depositCash(10, 1, 9);

        assertThat(atm.getBalance(), is(0));
        assertThat(atm.getTokenCellByValue(10).getBalance(), is(0));
    }
}
