package ru.otus.java.hw6.atm;

public interface Command {

    void execute();

    Atm getAtm();
}
