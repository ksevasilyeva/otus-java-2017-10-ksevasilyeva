package ru.outs.java.hw6.atm;

import org.junit.Test;
import ru.otus.java.hw6.atm.AtmController;
import ru.otus.java.hw6.atm.MoneyTokenCell;
import ru.otus.java.hw6.atm.MoneyTokens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AtmControllerTest {

    @Test
    public void testGetMinMoneyTokens() {
        List<MoneyTokenCell> tokenCells = new ArrayList<>();
        Arrays.asList(MoneyTokens.values())
            .forEach(token -> tokenCells.add(new MoneyTokenCell(5000, 100, token)));

        int[] minTokensArray = new AtmController().getMinMoneyTokens(tokenCells,9);

        assertThat(minTokensArray, is(new int[]{1, 2, 2, 2, 2}));
    }

    @Test
    public void tesMinTokensExpectOneToken() {
        List<MoneyTokenCell> tokenCells = new ArrayList<>();
        Arrays.asList(MoneyTokens.values())
            .forEach(token -> tokenCells.add(new MoneyTokenCell(5000, 100, token)));

        int[] minTokensArray = new AtmController().getMinMoneyTokens(tokenCells,10);

        assertThat(minTokensArray, is(new int[]{10}));
    }

    @Test
    public void testWithdrawCash() {
        List<MoneyTokenCell> tokenCells = new ArrayList<>();
        Arrays.asList(MoneyTokens.values())
            .forEach(token -> tokenCells.add(new MoneyTokenCell(5000, 100, token)));

        boolean operationResult = new AtmController().withdrawCash(tokenCells, 6);

        assertThat(operationResult, is(true));
    }

    @Test
    public void shouldNotWithdrawZero() {
        List<MoneyTokenCell> tokenCells = new ArrayList<>();
        Arrays.asList(MoneyTokens.values())
            .forEach(token -> tokenCells.add(new MoneyTokenCell(5000, 100, token)));

        boolean operationResult = new AtmController().withdrawCash(tokenCells, 0);

        assertThat(operationResult, is(false));
    }

    @Test
    public void shouldNotWithdrawFromEmptyCells() {
        List<MoneyTokenCell> tokenCells = new ArrayList<>();
        Arrays.asList(MoneyTokens.values())
            .forEach(token -> tokenCells.add(new MoneyTokenCell(5000, 0, token)));

        boolean operationResult = new AtmController().withdrawCash(tokenCells, 6);

        assertThat(operationResult, is(false));
    }

    @Test
    public void shouldNotWithdrawWhenNotEnoughTokens() {
        List<MoneyTokenCell> tokenCells = new ArrayList<>();
        Arrays.asList(MoneyTokens.values())
            .forEach(token -> tokenCells.add(new MoneyTokenCell(5000, 1, token)));

        boolean operationResult = new AtmController().withdrawCash(tokenCells, 6);

        assertThat(operationResult, is(false));
    }

    @Test
    public void shouldPutInMoneyTokenCell() {
        List<MoneyTokenCell> tokenCells = new ArrayList<>();
        AtmController controller = new AtmController();
        Arrays.asList(MoneyTokens.values())
            .forEach(token -> tokenCells.add(new MoneyTokenCell(5000, 0, token)));

        boolean operationResult = controller.putInMoneyTokenCell(tokenCells, 10);

        assertThat(operationResult, is(true));
        assertThat(controller.getTokenCellByValue(tokenCells, 10).get().getBalance(), is(10));
        assertThat(controller.getTokenCellByValue(tokenCells, 1).get().getBalance(), is(0));
        assertThat(controller.getTokenCellByValue(tokenCells, 100).get().getBalance(), is(0));
    }
}
