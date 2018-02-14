package ru.otus.hava.hw9;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.java.hw10.data.UserDataSet;
import ru.otus.java.hw9.db.DBServiceImpl;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

public class DBServiceTest {

    DBServiceImpl dbService;

    @Before
    public void setUp() throws SQLException {
        dbService = new DBServiceImpl();
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
        UserDataSet user = new UserDataSet(1, "First Last", 12);
        dbService.save(user);

        assertThat(dbService.load(1).toString(), is(user.toString()));
    }

    @Test
    public void shouldReadAllUsers() throws SQLException {
        UserDataSet user = new UserDataSet(1, "First Last", 12);
        UserDataSet user2 = new UserDataSet(2, "First Last", 13);
        UserDataSet user3 = new UserDataSet(3, "First Last", 14);
        dbService.save(user);
        dbService.save(user2);
        dbService.save(user3);

        assertThat(dbService.load(UserDataSet.class), hasSize(3));
    }
}
