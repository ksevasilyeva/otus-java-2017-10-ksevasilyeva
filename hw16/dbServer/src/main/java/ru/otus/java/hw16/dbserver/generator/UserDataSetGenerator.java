package ru.otus.java.hw16.dbserver.generator;



import org.kohsuke.randname.RandomNameGenerator;
import ru.otus.java.hw16.dbserver.model.UserDataSet;
import ru.otus.java.hw16.dbserver.service.CacheDbService;

import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class UserDataSetGenerator implements Runnable {

    private final CacheDbService dbService;

    private int pauseTime = 1000;

    private int userCount = 0;

    public UserDataSetGenerator(CacheDbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void run() {

        while (true) {
            try {
                dbService.save(getRandomUser());
                userCount++;
                dbService.load(ThreadLocalRandom.current().nextInt(0, userCount + 1));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(pauseTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private UserDataSet getRandomUser() {
        RandomNameGenerator rnd = new RandomNameGenerator();
        return new UserDataSet(rnd.next(), 25);
    }
}
