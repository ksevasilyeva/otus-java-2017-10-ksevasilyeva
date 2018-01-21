package ru.otus.java.hw6.atm.commands;

import ru.otus.java.hw6.atm.Atm;

public interface Command {

    void execute();

    Atm getAtm();
}
