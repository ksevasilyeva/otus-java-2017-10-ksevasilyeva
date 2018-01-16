package ru.outs.java.hw6.atm;

import org.junit.Test;
import ru.otus.java.hw6.atm.ATM;
import ru.otus.java.hw6.atm.AtmController;
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

public class AtmControllerTest {

    @Test
    public void testGetMinMoneyTokens() {
        ATM atm = new ATM(of(MoneyTokens.ONE_RUBLE, 10, MoneyTokens.TWO_RUBLES, 5, MoneyTokens.TEN_RUBLES, 6));

        List<Integer> minTokensArray = Arrays
            .stream(new AtmController(atm).getMinMoneyTokens(9)).boxed()
            .collect(Collectors.toList());
        Collections.sort(minTokensArray);

        assertThat(minTokensArray, is(Arrays.asList(1, 2, 2, 2, 2)));
    }

    @Test
    public void tesMinTokensExpectOneToken() {
        List<MoneyTokenCell> tokenCells = new ArrayList<>();
        ATM atm = new ATM();
        Arrays.asList(MoneyTokens.values())
            .forEach(token -> tokenCells.add(new MoneyTokenCell(5000, 100, token)));

        int[] minTokensArray = new AtmController(atm).getMinMoneyTokens(10);

        assertThat(minTokensArray, is(new int[]{10}));
    }

    @Test
    public void testWithdrawCash() {
        ATM atm = new ATM(of(MoneyTokens.TWO_RUBLES, 10));

        boolean operationResult = new AtmController(atm).withdrawCash(6);

        assertThat(operationResult, is(true));
    }

    @Test
    public void shouldNotWithdrawZero() {
        ATM atm = new ATM(of(MoneyTokens.TWO_RUBLES, 10));

        boolean operationResult = new AtmController(atm).withdrawCash(0);

        assertThat(operationResult, is(false));
    }

    @Test
    public void shouldNotWithdrawFromEmptyCells() {
        ATM atm = new ATM();

        boolean operationResult = new AtmController(atm).withdrawCash(6);

        assertThat(operationResult, is(false));
    }

    @Test
    public void shouldNotWithdrawWhenNotEnoughTokens() {
        ATM atm = new ATM(of(MoneyTokens.ONE_RUBLE, 1));

        boolean operationResult = new AtmController(atm).withdrawCash(6);

        assertThat(operationResult, is(false));
    }

    @Test
    public void shouldPutInMoneyTokenCell() {
        ATM atm = new ATM();
        AtmController controller = new AtmController(atm);

        boolean operationResult = controller.putInMoneyTokenCell(10);

        assertThat(operationResult, is(true));
        assertThat(controller.getTokenCellByValue(10).get().getBalance(), is(10));
        assertThat(controller.getTokenCellByValue(1).get().getBalance(), is(0));
        assertThat(controller.getTokenCellByValue(100).get().getBalance(), is(0));
    }
}
