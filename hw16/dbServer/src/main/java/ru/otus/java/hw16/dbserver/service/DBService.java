package ru.otus.java.hw16.dbserver.service;


import ru.otus.java.hw16.dbserver.model.UserDataSet;

import java.sql.SQLException;
import java.util.List;

public interface DBService extends AutoCloseable {

    void save(UserDataSet user) throws SQLException;

    UserDataSet load(long id) throws SQLException;

    List<UserDataSet> load(Class<UserDataSet> clazz);

    void close();
}
