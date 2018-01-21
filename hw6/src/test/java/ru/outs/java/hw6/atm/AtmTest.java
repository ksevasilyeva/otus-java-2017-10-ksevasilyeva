package ru.outs.java.hw6.atm;

import org.junit.Test;
import ru.otus.java.hw6.atm.Atm;
import ru.otus.java.hw6.atm.MoneyTokenCell;
import ru.otus.java.hw6.atm.MoneyTokens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableBiMap.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AtmTest {

    @Test
    public void testGetMinMoneyTokens() {
        Atm atm = new Atm(of(MoneyTokens.ONE_RUBLE, 10, MoneyTokens.TWO_RUBLES, 5, MoneyTokens.TEN_RUBLES, 6));

        List<Integer> minTokensArray = Arrays
            .stream(atm.getMinMoneyTokens(9)).boxed()
            .collect(Collectors.toList());
        Collections.sort(minTokensArray);

        assertThat(minTokensArray, is(Arrays.asList(1, 2, 2, 2, 2)));
    }

    @Test
    public void tesMinTokensExpectOneToken() {
        List<MoneyTokenCell> tokenCells = new ArrayList<>();
        Atm atm = new Atm();
        Arrays.asList(MoneyTokens.values())
            .forEach(token -> tokenCells.add(new MoneyTokenCell(5000, 100, token)));

        int[] minTokensArray = atm.getMinMoneyTokens(10);

        assertThat(minTokensArray, is(new int[]{10}));
    }

    @Test
    public void testWithdrawCash() {
        Atm atm = new Atm(of(MoneyTokens.TWO_RUBLES, 10));

        boolean operationResult = atm.withdrawCash(6);

        assertThat(operationResult, is(true));
    }

    @Test
    public void shouldNotWithdrawZero() {
        Atm atm = new Atm(of(MoneyTokens.TWO_RUBLES, 10));

        boolean operationResult = atm.withdrawCash(0);

        assertThat(operationResult, is(false));
    }

    @Test
    public void shouldNotWithdrawFromEmptyCells() {
        Atm atm = new Atm();

        boolean operationResult = atm.withdrawCash(6);

        assertThat(operationResult, is(false));
    }

    @Test
    public void shouldNotWithdrawWhenNotEnoughTokens() {
        Atm atm = new Atm(of(MoneyTokens.ONE_RUBLE, 1));

        boolean operationResult = atm.withdrawCash(6);

        assertThat(operationResult, is(false));
    }

    @Test
    public void shouldPutInMoneyTokenCell() {
        Atm atm = new Atm();

        boolean operationResult = atm.putInMoneyTokenCell(10);

        assertThat(operationResult, is(true));
        assertThat(atm.getTokenCellByValue(10).getBalance(), is(10));
        assertThat(atm.getTokenCellByValue(1).getBalance(), is(0));
        assertThat(atm.getTokenCellByValue(100).getBalance(), is(0));
    }

    @Test
    public void atmShouldResetToDateState() {
        Atm atm = new Atm();
        atm.depositMoneyTokens(MoneyTokens.HUNDRED_RUBLES, MoneyTokens.FIVE_HUNDRED_RUBLES);
        atm.withdrawCash(100);
        atm.depositMoneyTokens(MoneyTokens.HUNDRED_RUBLES);
        atm.depositMoneyTokens(MoneyTokens.FIVE_HUNDRED_RUBLES);
        atm.withdrawCash(100);

        atm.resetToEventId(3);

        assertThat(atm.getBalance(), is(500));
    }

    @Test
    public void atmShouldResetToInitState() {
        Atm atm = new Atm(of(MoneyTokens.TWO_RUBLES, 10));
        atm.depositMoneyTokens(MoneyTokens.HUNDRED_RUBLES, MoneyTokens.FIVE_HUNDRED_RUBLES);

        atm.resetAtmToInitState();

        assertThat(atm.getBalance(), is(20));
    }

    @Test
    public void atmShouldResetToEmptyState() {
        Atm atm = new Atm();
        atm.depositMoneyTokens(MoneyTokens.HUNDRED_RUBLES, MoneyTokens.FIVE_HUNDRED_RUBLES);

        atm.resetAtmToInitState();

        assertThat(atm.getBalance(), is(0));
    }
}
