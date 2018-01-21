package ru.outs.java.hw6.atm;

import org.junit.Test;
import ru.otus.java.hw6.atm.Atm;
import ru.otus.java.hw6.atm.MoneyTokens;

import static com.google.common.collect.ImmutableBiMap.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AtmWithdrawalTest {

    @Test
    public void shouldNotWithdrawWhenNotEnoughTokens() {
        Atm atm = new Atm();
        atm.withdrawCash(10);

        assertThat(atm.getBalance(), is(0));
    }

    @Test
    public void shouldWithdrawOneMoneyToken() {
        Atm atm = new Atm(of(MoneyTokens.HUNDRED_RUBLES, 1, MoneyTokens.TEN_RUBLES, 20));
        int atmBalance = atm.getBalance();
        int tokenCellBalance = atm.getTokenCellByValue(10).getBalance();

        atm.withdrawCash(10);

        assertThat(atm.getBalance(), is(atmBalance - 10));
        assertThat(atm.getTokenCellByValue(10).getBalance(), is(tokenCellBalance - 10));
    }

    @Test
    public void shouldWithdrawMultipleMoneyTokens() {
        Atm atm = new Atm(of(MoneyTokens.HUNDRED_RUBLES, 10, MoneyTokens.TWO_RUBLES, 100));
        int atmBalance = atm.getBalance();
        int tokenCellBalance100 = atm.getTokenCellByValue(100).getBalance();
        int tokenCellBalance2 = atm.getTokenCellByValue(2).getBalance();

        atm.withdrawCash(102);

        assertThat(atm.getBalance(), is(atmBalance - 102));
        assertThat(atm.getTokenCellByValue(100).getBalance(), is(tokenCellBalance100 - 100));
        assertThat(atm.getTokenCellByValue(2).getBalance(), is(tokenCellBalance2 - 2));
    }

    @Test
    public void shouldNotWithdrawInvalidToken() {
        Atm atm = new Atm(of(MoneyTokens.HUNDRED_RUBLES, 10, MoneyTokens.FIVE_HUNDRED_RUBLES, 2));
        int atmBalance = atm.getBalance();

        atm.withdrawCash(0);
        assertThat(atm.getBalance(), is(atmBalance));
    }
}
