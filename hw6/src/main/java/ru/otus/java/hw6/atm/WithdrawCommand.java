package ru.otus.java.hw6.atm;

public class WithdrawCommand implements Command {

    private int amount;

    private Atm atm;

    public WithdrawCommand(int amountToWithdraw, Atm atm) {
        this.amount = amountToWithdraw;
        this.atm = atm;
    }

    @Override
    public void execute() {
        atm.internalWithdrawCash(amount);
    }

    @Override
    public Atm getAtm() {
        return atm;
    }
}
