package ru.otus.java.hw10.base;

import ru.otus.java.hw10.data.UserDataSet;

import java.sql.SQLException;
import java.util.List;

public interface DBService extends AutoCloseable {

    void save(UserDataSet user) throws SQLException;

    UserDataSet load(long id) throws SQLException;

    List<UserDataSet> load(Class<UserDataSet> clazz);

    void close();
}
