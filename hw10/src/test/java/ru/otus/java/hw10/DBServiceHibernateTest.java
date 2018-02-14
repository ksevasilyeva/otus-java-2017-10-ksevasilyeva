package ru.otus.java.hw10;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.java.hw10.base.DBService;
import ru.otus.java.hw10.data.AddressDataSet;
import ru.otus.java.hw10.data.PhoneDataSet;
import ru.otus.java.hw10.data.UserDataSet;
import ru.otus.java.hw10.dbService.DBServiceHibernateImpl;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class DBServiceHibernateTest {

    private DBService dbService;

    @Before
    public void setUp() {
        dbService = new DBServiceHibernateImpl();
    }

    @After
    public void tearDown() throws Exception {
        dbService.close();
    }

    @Test
    public void shouldCreateTable() {
    }

    @Test
    public void shouldAddUser() throws SQLException {
        UserDataSet user = new UserDataSet("name1", 31);
        UserDataSet user2 = new UserDataSet("name2", 32);

        dbService.save(user);
        dbService.save(user2);

        assertThat(dbService.load(1).toString(), is(user.toString()));
        assertThat(dbService.load(2).toString(), is(user2.toString()));
    }

    @Test
    public void shouldAddUserWithAddress() throws SQLException {
        UserDataSet user = new UserDataSet("name1", 31);
        AddressDataSet address = new AddressDataSet("StreetName", 33, 12);
        user.setAddress(address);

        dbService.save(user);
        assertThat(dbService.load(1).toString(), is(user.toString()));
    }

    @Test
    public void shouldAddUserWithPhone() throws SQLException {
        UserDataSet user = new UserDataSet("name1", 31);
        PhoneDataSet phone = new PhoneDataSet("12345-67890");
        PhoneDataSet phone2 = new PhoneDataSet("09876-54321");
        user.addPhone(phone);
        user.addPhone(phone2);

        dbService.save(user);
        UserDataSet dbUser = dbService.load(1);
        assertThat(dbUser.getPhones(), hasSize(2));
    }

    @Test
    public void shouldLoadByClass() throws SQLException {
        UserDataSet user = new UserDataSet("name1", 31);
        UserDataSet user2 = new UserDataSet("name2", 32);
        UserDataSet user3 = new UserDataSet("name3", 33);

        dbService.save(user);
        dbService.save(user2);
        dbService.save(user3);

        List<UserDataSet> userDataSets = dbService.load(UserDataSet.class);
        assertThat(userDataSets, hasSize(3));
    }
}
