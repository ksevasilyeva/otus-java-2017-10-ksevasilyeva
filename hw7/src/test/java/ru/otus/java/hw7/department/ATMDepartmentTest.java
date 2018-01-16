package ru.otus.java.hw7.department;

import org.junit.Test;
import ru.otus.java.hw6.atm.ATM;
import ru.otus.java.hw6.atm.MoneyTokens;

import static com.google.common.collect.ImmutableBiMap.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

public class ATMDepartmentTest {

    @Test
    public void departmentShouldHaveMultipleAtm() {
        ATMDepartment department = new ATMDepartment();
        assertThat(department.getATMs(), is(empty()));

        department.addATM(new ATM(of(MoneyTokens.FIVE_HUNDRED_RUBLES, 5, MoneyTokens.ONE_RUBLE, 1000)));
        department.addATM(new ATM(of(MoneyTokens.TEN_RUBLES, 100, MoneyTokens.ONE_RUBLE, 10)));
        department.addATM(new ATM(of(MoneyTokens.TWO_RUBLES, 5, MoneyTokens.TEN_RUBLES, 10)));
        assertThat(department.numberOfAtms(), is(3));
    }

    @Test
    public void departmentShouldCollectAtmBalances() {
        ATMDepartment department = new ATMDepartment();
        assertThat(department.getTotalBalance(), is(0));

        department.addATM(new ATM(of(MoneyTokens.TEN_RUBLES, 5, MoneyTokens.ONE_RUBLE, 1000)));
        department.addATM(new ATM(of(MoneyTokens.TWO_RUBLES, 5, MoneyTokens.TEN_RUBLES, 10)));
        assertThat(department.getTotalBalance(), is(1160));
    }

    @Test
    public void departmentShouldResetAllAtmsToInit() {
        ATMDepartment department = new ATMDepartment();
        ATM atm1 = new ATM(of(MoneyTokens.TEN_RUBLES, 5, MoneyTokens.ONE_RUBLE, 1000));
        ATM atm2 = new ATM(of(MoneyTokens.TWO_RUBLES, 5, MoneyTokens.TEN_RUBLES, 10));

        department.addATM(atm1);
        department.addATM(atm2);
        int initAtmBalance = department.getTotalBalance();

        atm1.depositCash(500, 500, 10, 10);
        atm2.depositCash(500, 500, 500);
        assertThat(department.getTotalBalance(), is(3680));

        department.resetAllATM();
        assertThat(department.getTotalBalance(), is(initAtmBalance));
        assertThat(atm1.getBalance(), is(1050));
        assertThat(atm2.getBalance(), is(110));
    }
}
