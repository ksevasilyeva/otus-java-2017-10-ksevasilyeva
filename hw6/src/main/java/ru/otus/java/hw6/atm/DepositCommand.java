package ru.otus.java.hw6.atm;

public class DepositCommand implements Command {

    private int token;

    private Atm atm;

    public DepositCommand(int token, Atm atm) {
        this.token = token;
        this.atm = atm;
    }

    @Override
    public void execute() {
        atm.getTokenCellByValue(token).addMoneyTokenToCell();
    }

    @Override
    public Atm getAtm() {
        return atm;
    }
}
