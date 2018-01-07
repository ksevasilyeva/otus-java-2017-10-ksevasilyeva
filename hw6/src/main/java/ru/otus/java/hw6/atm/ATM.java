package ru.otus.java.hw6.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class ATM {

    private static final Logger LOG = LoggerFactory.getLogger(ATM.class);

    public List<MoneyTokenCell> availableTokenCells = new ArrayList<>();

    public static ATM initATMWithDefaultCashAmount() {
        ATM newATM = new ATM();
        Arrays.asList(MoneyTokens.values())
            .forEach(token ->
                newATM.availableTokenCells.add(new MoneyTokenCell(5000, 100, token)));
        return newATM;
    }

    public static ATM initAtmWithoutMoney() {
        ATM newATM = new ATM();
        Arrays.asList(MoneyTokens.values())
            .forEach(token ->
                newATM.availableTokenCells.add(new MoneyTokenCell(5000, 0, token)));
        return newATM;
    }

    public int getBalance() {
        return availableTokenCells.stream()
            .mapToInt(cell -> cell.getBalance())
            .sum();
    }

    public void depositCash(int... moneyTokens) {
        LOG.info("Cash deposit of Sum: [{}] requested. Current Balance: {}", IntStream.of(moneyTokens).sum(), getBalance());
        if (AtmController.validateMoneyTokens(availableTokenCells, moneyTokens)) {
            AtmController.depositMoneyTokens(availableTokenCells, moneyTokens);
            LOG.info("Successfully deposit Sum: [{}]. Current Balance: {}", IntStream.of(moneyTokens).sum(), getBalance());
        }
    }

    public void withdrawCash(int requestedCash) {
        LOG.info("Cash withdrawal of Sum: [{}] requested. Current Balance: {}", requestedCash, getBalance());
        if (AtmController.withdrawCash(availableTokenCells, requestedCash)) {
            LOG.info("Successfully withdrew sum of [{}]. Current Balance: {}", requestedCash, getBalance());
        } else {
            LOG.error("Withdrawal failed, request another sum of money");
        }
    }

    public MoneyTokenCell getTokenCellByValue(int value) {
        Optional<MoneyTokenCell> cell = AtmController.getTokenCellByValue(availableTokenCells, value);
        if (cell.isPresent()) {
            return cell.get();
        } else {
            LOG.error("ATM doesn't support Money Tokens of value: [{}]", value);
        }
        return null;
    }
}
