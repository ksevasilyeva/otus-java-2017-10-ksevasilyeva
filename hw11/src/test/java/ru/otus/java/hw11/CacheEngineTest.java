package ru.otus.java.hw11;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.java.hw10.base.DBService;
import ru.otus.java.hw10.data.DataSet;
import ru.otus.java.hw10.data.UserDataSet;
import ru.otus.java.hw10.dbService.DBServiceHibernateImpl;
import ru.otus.java.hw11.cache.CacheEngine;
import ru.otus.java.hw11.cache.CacheEngineImpl;
import ru.otus.java.hw11.db.CacheDbService;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CacheEngineTest {

    private DBService dbService;
    private CacheEngine<DataSet> cacheEngine;

    @Before
    public void setUp() {
        cacheEngine = new CacheEngineImpl<>(5, 1000, 0, false);
        dbService = new CacheDbService(new DBServiceHibernateImpl(), cacheEngine);
    }

    @After
    public void tearDown() throws Exception {
        dbService.close();
    }

    @Test
    public void shouldNotIncrementHitMiss() throws SQLException {
        UserDataSet user = new UserDataSet("name1", 31);
        UserDataSet user2 = new UserDataSet("name2", 32);

        dbService.save(user);
        dbService.save(user2);

        assertThat(cacheEngine.getCurrentSize(), is(2));

        assertThat(cacheEngine.getMissCount(), is(0));
        assertThat(cacheEngine.getHitCount(), is(0));
    }

    @Test
    public void shouldIncrementHit() throws SQLException {
        UserDataSet user = new UserDataSet(1, "name1", 31);
        UserDataSet user2 = new UserDataSet(2, "name2", 32);

        dbService.save(user);
        dbService.save(user2);

        assertThat(cacheEngine.getCurrentSize(), is(2));

        dbService.load(1);
        dbService.load(2);

        assertThat(cacheEngine.getMissCount(), is(0));
        assertThat(cacheEngine.getHitCount(), is(2));
    }

    @Test
    public void shouldIncrementMiss() throws SQLException, InterruptedException {
        UserDataSet user = new UserDataSet(1, "name1", 31);
        UserDataSet user2 = new UserDataSet(2, "name2", 32);

        dbService.save(user);
        dbService.save(user2);

        assertThat(cacheEngine.getCurrentSize(), is(2));

        Thread.sleep(3000);

        UserDataSet downloadedUser2 = dbService.load(2);
        UserDataSet downloadedUser = dbService.load(1);

        assertThat(cacheEngine.getHitCount(), is(0));
        assertThat(cacheEngine.getMissCount(), is(2));

        assertThat(downloadedUser.toString(), is(user.toString()));
        assertThat(downloadedUser2.toString(), is(user2.toString()));
    }
}
