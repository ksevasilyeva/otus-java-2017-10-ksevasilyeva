package ru.otus.java.hw6.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ATM {

    private static final Logger LOG = LoggerFactory.getLogger(ATM.class);

    public Set<MoneyTokenCell> availableTokenCells = new HashSet<>();

    public AtmState initAtmState;

    private AtmController atmController = new AtmController(this);

    public ATM() {
        initAtmState = new AtmState(new HashMap<>());
        initTokenCells();
    }

    public ATM(Map<MoneyTokens, Integer> initCash) {
        initTokenCells();
        fillCellsWithCashAmount(initCash);
        initAtmState = new AtmState(initCash);
    }

    public ATM resetAtmToInitState() {
        initTokenCells();
        fillCellsWithCashAmount(initAtmState.getState());
        return this;
    }

    private void fillCellsWithCashAmount(Map<MoneyTokens, Integer> cash) {
        if(cash.isEmpty()) {
            return;
        }
        cash.entrySet().forEach(token -> availableTokenCells.stream()
                .filter(cell -> cell.getMoneyToken().equals(token.getKey()))
                .findFirst()
                .get()
                .addMoneyTokenToCell(token.getValue()));
    }

    private void initTokenCells() {
        availableTokenCells = new HashSet<>();
        Arrays.asList(MoneyTokens.values())
            .forEach(token -> availableTokenCells.add(new MoneyTokenCell(0, token)));
    }

    public int getBalance() {
        return availableTokenCells.stream()
            .mapToInt(cell -> cell.getBalance())
            .sum();
    }

    public void depositCash(int... moneyTokens) {
        LOG.info("Cash deposit of Sum: [{}] requested. Current Balance: {}", IntStream.of(moneyTokens).sum(), getBalance());
        if (atmController.validateMoneyTokens(moneyTokens)) {
            atmController.depositMoneyTokens(moneyTokens);
            LOG.info("Successfully deposit Sum: [{}]. Current Balance: {}", IntStream.of(moneyTokens).sum(), getBalance());
        }
    }

    public void depositCash(MoneyTokens... moneyTokens) {
        int targetSum = Stream.of(moneyTokens).mapToInt(token -> token.getValue()).sum();
        LOG.info("Cash deposit of Sum: [{}] requested. Current Balance: {}", targetSum, getBalance());
        atmController.depositMoneyTokens(moneyTokens);
        LOG.info("Successfully deposit Sum: [{}]. Current Balance: {}", targetSum, getBalance());
    }

    public void withdrawCash(int requestedCash) {
        LOG.info("Cash withdrawal of Sum: [{}] requested. Current Balance: {}", requestedCash, getBalance());
        if (atmController.withdrawCash(requestedCash)) {
            LOG.info("Successfully withdrew sum of [{}]. Current Balance: {}", requestedCash, getBalance());
        } else {
            LOG.error("Withdrawal failed, request another sum of money");
        }
    }

    public MoneyTokenCell getTokenCellByValue(int value) {
        Optional<MoneyTokenCell> cell = atmController.getTokenCellByValue(value);
        if (cell.isPresent()) {
            return cell.get();
        } else {
            LOG.error("ATM doesn't support Money Tokens of value: [{}]", value);
        }
        return null;
    }
}
