package ru.otus.java.hw7.department;

import ru.otus.java.hw6.atm.ATM;

import java.util.Set;

public interface Department {

    void addATM(ATM atm);

    Set<ATM> getATMs();

    int getTotalBalance();

    void resetAllATM();

    int numberOfAtms();
}
