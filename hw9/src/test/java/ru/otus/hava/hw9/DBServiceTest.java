package ru.otus.hava.hw9;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.java.hw9.data.DataSet;
import ru.otus.java.hw9.data.UserDataSet;
import ru.otus.java.hw9.db.DBServiceConnection;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class DBServiceTest {

    DBServiceConnection dbService;

    @Before
    public void setUp() throws SQLException {
        dbService = new DBServiceConnection();
        dbService.createTable();
    }

    @After
    public void tearDown() throws SQLException {
        dbService.deleteTable();
    }

    @Test
    public void shouldCreateTable() throws SQLException {

        assertThat(dbService.getAllTables(), hasItem("user"));
    }

    @Test
    public void shouldDeleteTable() throws SQLException {
        assertThat(dbService.getAllTables(), hasItem("user"));

        dbService.deleteTable();
        assertThat(dbService.getAllTables(), not(hasItem("user")));
    }

    @Test
    public void shouldAddUsers() throws SQLException {
        DataSet user = new UserDataSet(1,"First Last", 12);
        dbService.save(user);

        assertThat(dbService.load(1, UserDataSet.class).toString(), is(user.toString()));
    }
}
