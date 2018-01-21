package ru.otus.java.hw6.atm.commands;

import ru.otus.java.hw6.atm.Atm;

public class ResetCommand implements Command {

    private Atm atm;

    public ResetCommand(Atm atm) {
        this.atm = atm;
    }

    @Override
    public void execute() {
        atm.toInitialState();
    }

    @Override
    public Atm getAtm() {
        return atm;
    }
}
