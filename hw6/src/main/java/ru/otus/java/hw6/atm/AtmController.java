package ru.otus.java.hw6.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AtmController {

    private static final Logger LOG = LoggerFactory.getLogger(AtmController.class);

    private ATM atm;

    public AtmController(ATM atm) {
        this.atm = atm;
    }

    public boolean withdrawCash(int requestedCash) {
        if (requestedCash <= 0) {
            return false;
        }
        int[] moneyTokensToWithdraw = getMinMoneyTokens(requestedCash);

        if (!validateMoneyTokensAvailability(moneyTokensToWithdraw)) {
            LOG.error("Not enough Money tokens to withdraw requested sum");
            return false;
        }

        for (int token : moneyTokensToWithdraw) {
            getTokenCellByValue(token).get().withdrawTokenFromCell();
        }
        return true;
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
            for (MoneyTokenCell cell : atm.availableTokenCells)  {
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
        Optional<MoneyTokenCell> tokenCell = getTokenCellByValue(token);
        if (tokenCell.isPresent()) {
            tokenCell.get().addMoneyTokenToCell();
            return true;
        } else {
            LOG.info("Cannot deposit the following Money token: {}", token);
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
        List<Integer> cellValues = atm.availableTokenCells.stream()
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
            Optional<MoneyTokenCell> cell = getTokenCellByValue(token);
            long amountOfTokenOccurance = IntStream.of(moneyTokens)
                .filter(money -> money == token)
                .count();

            if (!cell.isPresent() || cell.get().getTokenAmount() < amountOfTokenOccurance) {
                return false;
            }
        }
        return true;
    }

    public Optional<MoneyTokenCell> getTokenCellByValue(int value) {
        return atm.availableTokenCells.stream()
            .filter(cell -> cell.getTokenValue() == value)
            .findFirst();
    }
}
