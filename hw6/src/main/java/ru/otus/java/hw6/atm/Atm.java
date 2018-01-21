package ru.otus.java.hw6.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Atm {

    private static final Logger LOG = LoggerFactory.getLogger(Atm.class);

    private CommandExecutor commandExecutor = new CommandExecutor();

    public Set<MoneyTokenCell> availableTokenCells = new HashSet<>();

    private Map<MoneyTokens, Integer> initState = new HashMap<>();

    public Atm() {
        initTokenCells();
    }

    public Atm(Map<MoneyTokens, Integer> initCash) {
        initTokenCells();
        fillCellsWithCashAmount(initCash);
        initState = initCash;
    }


    public void toInitialState() {
        initTokenCells();
        fillCellsWithCashAmount(initState);
    }

    public void resetToEventId(int eventId) {
        commandExecutor.resetToEventId(eventId);
    }

    public void resetAtmToInitState() {
        commandExecutor.execute(new ResetCommand(this));
    }

    public void internalWithdrawCash(int requestedCash) {
        if (requestedCash <= 0) {
            throw new RuntimeException("Failed to withdraw");
        }
        int[] moneyTokensToWithdraw = getMinMoneyTokens(requestedCash);

        if (!validateMoneyTokensAvailability(moneyTokensToWithdraw)) {
            throw new RuntimeException("Failed to withdraw");
        }

        for (int token : moneyTokensToWithdraw) {
            getTokenCellByValue(token).withdrawTokenFromCell();
        }
    }


    public boolean withdrawCash(int requestedCash) {
        try {
            commandExecutor.execute(new WithdrawCommand(requestedCash, this));
            return true;
        } catch (RuntimeException e) {
            LOG.error(e.getMessage());
            return false;
        }
    }

    public int[] getMinMoneyTokens(int amount) {

        //Reference: Dynamic programming with Igor Kleiner https://www.youtube.com/watch?v=vTXVsW26ayc

        int[] minTokensArray = new int[amount + 1];
        int[] firstTokenInCombination = new int[amount + 1];

        for (int i = 0; i < minTokensArray.length; i++){
            minTokensArray[i] = Integer.MAX_VALUE;
        }

        minTokensArray[0] = 0;
        firstTokenInCombination[0] = 0;

        for (int i = 1; i < minTokensArray.length; i++) {
            for (MoneyTokenCell cell : availableTokenCells)  {
                if (i >= cell.getTokenValue() &&
                    i - cell.getTokenValue() >= 0 &&
                    minTokensArray[i - cell.getTokenValue()] + 1 < minTokensArray[i]) {
                        minTokensArray[i] = 1 + minTokensArray[i - cell.getTokenValue()];
                        firstTokenInCombination[i] = cell.getTokenValue();
                }
            }
        }

        int[] minimumCombination = new int[minTokensArray[amount]];
        minimumCombination[0] = firstTokenInCombination[amount];
        int prevPositionIndex = amount;

        for (int i = 1; i < minimumCombination.length; i++) {
            prevPositionIndex = prevPositionIndex - firstTokenInCombination[prevPositionIndex];
            minimumCombination[i] = firstTokenInCombination[prevPositionIndex];
        }
        return minimumCombination;
    }

    public boolean putInMoneyTokenCell(int token) {
        try {
            commandExecutor.execute(new DepositCommand(token, this));
            return true;
        } catch (RuntimeException e) {
            LOG.error(e.getMessage());
            return false;
        }
    }

    public void depositMoneyTokens(int[] moneyTokens) {
        for (int token : moneyTokens) {
            putInMoneyTokenCell(token);
        }
    }

    public void depositMoneyTokens(MoneyTokens... moneyTokens) {
        depositMoneyTokens(Stream.of(moneyTokens).mapToInt(token -> token.getValue()).toArray());
    }

    public boolean validateMoneyTokens(int[] moneyTokens) {
        List<Integer> cellValues = availableTokenCells.stream()
            .map(cell -> cell.getTokenValue())
            .collect(Collectors.toList());

        for (int token : moneyTokens) {
            if (!cellValues.contains(token)) {
                LOG.error("Cash contains unsupported Money token: [{}]. Returning back all Money Tokens", token);
                return false;
            }
        }
        return true;
    }

    public boolean validateMoneyTokensAvailability(int[] moneyTokens) {
        // Ограничение - не выдаем деньги если не хватает монет для выдачи в заданном минимальном количестве.
        // Иначе надо было хранить все минимальные комбинации...
        for (int token : moneyTokens) {
            MoneyTokenCell cell = getTokenCellByValue(token);
            long amountOfTokenOccurance = IntStream.of(moneyTokens)
                .filter(money -> money == token)
                .count();

            if (cell.getTokenAmount() < amountOfTokenOccurance) {
                return false;
            }
        }
        return true;
    }

    public MoneyTokenCell getTokenCellByValue(int value) {
        Optional<MoneyTokenCell> tokenCell = availableTokenCells.stream()
            .filter(cell -> cell.getTokenValue() == value)
            .findFirst();
        if (tokenCell.isPresent()) {
            return tokenCell.get();
        }
        throw new RuntimeException(String.format("No token cell found for %s", value));

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

    public int getInternalBalance() {
        return availableTokenCells.stream()
            .mapToInt(cell -> cell.getBalance())
            .sum();
    }
    public int getBalance() {
        return commandExecutor.getBalanceForAtm(this);
    }

    public void depositCash(int... moneyTokens) {
        LOG.info("Cash deposit of Sum: [{}] requested. Current Balance: {}", IntStream.of(moneyTokens).sum(), getBalance());
        if (validateMoneyTokens(moneyTokens)) {
            depositMoneyTokens(moneyTokens);
            LOG.info("Successfully deposit Sum: [{}]. Current Balance: {}", IntStream.of(moneyTokens).sum(), getBalance());
        }
    }

    public void depositCash(MoneyTokens... moneyTokens) {
        int targetSum = Stream.of(moneyTokens).mapToInt(token -> token.getValue()).sum();
        LOG.info("Cash deposit of Sum: [{}] requested. Current Balance: {}", targetSum, getBalance());
        depositMoneyTokens(moneyTokens);
        LOG.info("Successfully deposit Sum: [{}]. Current Balance: {}", targetSum, getBalance());
    }
}
