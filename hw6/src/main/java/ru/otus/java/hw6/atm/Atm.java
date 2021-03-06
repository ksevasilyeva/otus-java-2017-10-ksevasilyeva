package ru.otus.java.hw6.atm;

import ru.otus.java.hw6.atm.commands.CommandExecutor;
import ru.otus.java.hw6.atm.commands.DepositCommand;
import ru.otus.java.hw6.atm.commands.ResetCommand;
import ru.otus.java.hw6.atm.commands.WithdrawCommand;

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

    public Set<MoneyTokenCell> availableTokenCells = new HashSet<>();

    private CommandExecutor commandExecutor = new CommandExecutor();

    private Map<MoneyTokens, Integer> initState = new HashMap<>();

    private AtmListener atmListener = new AtmListener();

    public Atm() {
        initTokenCells();
        atmListener.newAtmCreated(0);
    }

    public Atm(Map<MoneyTokens, Integer> initCash) {
        initTokenCells();
        fillCellsWithCashAmount(initCash);
        initState = initCash;
        atmListener.newAtmCreated(getInternalBalance());
    }


    public void toInitialState() {
        initTokenCells();
        fillCellsWithCashAmount(initState);
        atmListener.atmReseted();
    }

    public void toInitialStateInternal() {
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
            throw new RuntimeException();
        }
        int[] moneyTokensToWithdraw = getMinMoneyTokens(requestedCash);

        if (!validateMoneyTokensAvailability(moneyTokensToWithdraw)) {
            throw new RuntimeException();
        }

        for (int token : moneyTokensToWithdraw) {
            getTokenCellByValue(token).withdrawTokenFromCell();
        }
    }


    public boolean withdrawCash(int requestedCash) {
        try {
            commandExecutor.execute(new WithdrawCommand(requestedCash, this));
            atmListener.cashWithdrawed(requestedCash);
            return true;
        } catch (RuntimeException e) {
            atmListener.operationFailed("Failed to withdraw amount of: " + requestedCash);
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
            atmListener.cashDeposit(token);
            return true;
        } catch (RuntimeException e) {
            atmListener.operationFailed("Failed to deposit: " + token);
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
                atmListener.operationFailed(
                    String.format("Cash contains unsupported Money token: [%s]. Returning back all Money Tokens", token));
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
        if (validateMoneyTokens(moneyTokens)) {
            depositMoneyTokens(moneyTokens);
        }
    }

    public void depositCash(MoneyTokens... moneyTokens) {
        depositMoneyTokens(moneyTokens);
    }
}
