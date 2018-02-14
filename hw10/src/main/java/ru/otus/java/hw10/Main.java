package ru.otus.java.hw10;

import ru.otus.java.hw10.base.DBService;
import ru.otus.java.hw10.data.UserDataSet;
import ru.otus.java.hw10.dbService.DBServiceHibernateImpl;

public class Main {
    public static void main(String[] args) throws Exception {
        UserDataSet user = new UserDataSet("name1", 30);
        UserDataSet user2 = new UserDataSet("name2", 30);

        try(DBService dbService = new DBServiceHibernateImpl()) {

            dbService.save(user);
            dbService.save(user2);


            UserDataSet dataSet = dbService.load(1);
            System.out.println(dataSet);
        }
    }
}
