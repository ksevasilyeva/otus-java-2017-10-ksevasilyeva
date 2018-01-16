package ru.otus.java.hw7.department;

import ru.otus.java.hw6.atm.ATM;

import java.util.HashSet;
import java.util.Set;

public class ATMDepartment implements Department {

    private Set<ATM> atmSet = new HashSet<>();

    @Override
    public void addATM(ATM atm) {
        atmSet.add(atm);
    }

    @Override
    public Set<ATM> getATMs() {
        return atmSet;
    }

    @Override
    public int getTotalBalance() {
        return atmSet.stream().mapToInt(ATM::getBalance).sum();
    }

    @Override
    public void resetAllATM() {
        atmSet.forEach(atm -> atm.resetAtmToInitState());
    }

    @Override
    public int numberOfAtms(){
        return atmSet.size();
    }
}
