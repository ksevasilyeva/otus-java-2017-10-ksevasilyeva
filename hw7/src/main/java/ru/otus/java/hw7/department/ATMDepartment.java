package ru.otus.java.hw7.department;

import ru.otus.java.hw6.atm.Atm;

import java.util.HashSet;
import java.util.Set;

public class ATMDepartment {

    private Set<Atm> atmSet = new HashSet<>();

    public void addATM(Atm atm) {
        atmSet.add(atm);
    }

    public Set<Atm> getATMs() {
        return atmSet;
    }

    public int getTotalBalance() {
        return atmSet.stream().mapToInt(Atm::getBalance).sum();
    }

    public void resetAllATM() {
        atmSet.forEach(atm -> atm.resetAtmToInitState());
    }

    public int numberOfAtms(){
        return atmSet.size();
    }
}
