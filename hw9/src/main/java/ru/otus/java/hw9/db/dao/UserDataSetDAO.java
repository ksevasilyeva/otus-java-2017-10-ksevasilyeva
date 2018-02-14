package ru.otus.java.hw9.db.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.hw10.data.UserDataSet;
import ru.otus.java.hw9.executor.DbExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDataSetDAO {

    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS user (id bigint(20) NOT NULL auto_increment primary key," +
                          "name varchar(255),age int(3))";
    private static final String DELETE_TABLE_USER = "DROP TABLE IF EXISTS user";
    private static final String SAVE_DATA = "INSERT INTO user (name, age) VALUES ('%s', %d);";
    private static final String GET_DATA_BY_ID = "SELECT * FROM user WHERE id=";
    private static final String GET_ALL = "SELECT * FROM user";

    private static final Logger LOG = LoggerFactory.getLogger(UserDataSetDAO.class);

    private final DbExecutor executor;

    public UserDataSetDAO(Connection connection) {
        this.executor = new DbExecutor(connection);
    }

    public void createTable() {
        try {
            executor.executeUpdate(CREATE_TABLE_USER);
            LOG.info("Table created");
        } catch (SQLException e) {

        }
    }

    public void deleteTable() {
        try {
            executor.executeUpdate(DELETE_TABLE_USER);
            LOG.info("Table deleted");
        } catch (SQLException e) {
            LOG.error("Failed to delete table", e);
        }

    }

    public void save(UserDataSet user) {
        String query = String.format(SAVE_DATA, user.getName(), user.getAge());
        try {
            executor.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOG.info("Executed: " + query);
    }

    public UserDataSet read(long id) {
        try {
            return executor.executeQuery(GET_DATA_BY_ID + id, queryResult -> {
                queryResult.next();
                return new UserDataSet(queryResult.getLong(1),
                    queryResult.getString(2), queryResult.getInt(3));
            });
        } catch (SQLException e) {
            LOG.error("Failed to load user by id: {}", id);
            return null;
        }
    }

    public List<UserDataSet> readAll() {
        List<UserDataSet> users = new ArrayList<>();
        try {
            return executor.executeQuery(GET_ALL, queryResult -> {
                while (queryResult.next()) {
                    users.add(new UserDataSet(queryResult.getLong(1),
                        queryResult.getString(2),
                        queryResult.getInt(3)));
                }
                return users;
            });
        } catch (SQLException e) {
            LOG.error("Failed to load users");
        }
        return users;
    }
}
