package ru.otus.java.hw13.db;

import ru.otus.java.hw13.data.AccountDataSet;

import java.sql.SQLException;

public interface AccountDBService extends AutoCloseable {

    void save(AccountDataSet user) throws SQLException;

    AccountDataSet load(String login) throws SQLException;

    void close();
}