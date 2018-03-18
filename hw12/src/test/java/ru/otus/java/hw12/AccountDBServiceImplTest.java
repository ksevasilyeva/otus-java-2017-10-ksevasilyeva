package ru.otus.java.hw12;

import org.junit.Test;
import ru.otus.java.hw12.data.AccountDataSet;
import ru.otus.java.hw12.db.AccountDBService;
import ru.otus.java.hw12.db.AccountDBServiceImpl;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AccountDBServiceImplTest {

    @Test
    public void shouldAddAccount() throws SQLException {
        AccountDBService dbService = new AccountDBServiceImpl();
        AccountDataSet accountDataSet = new AccountDataSet("log", "pass");

        dbService.save(accountDataSet);

        assertThat(dbService.load("log").toString(), is(accountDataSet.toString()));
    }
}
