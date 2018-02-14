package ru.otus.java.hw9.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.hw10.base.DBService;
import ru.otus.java.hw10.data.UserDataSet;
import ru.otus.java.hw9.db.dao.UserDataSetDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBServiceImpl implements AutoCloseable, DBService {

    private static final Logger LOG = LoggerFactory.getLogger(DBServiceImpl.class);

    private final Connection connection;
    private UserDataSetDAO userDataSetDAO;

    public DBServiceImpl() {
        connection = ConnectionHelper.getConnection();
        userDataSetDAO = new UserDataSetDAO(connection);
    }

    public void createTable() {
        userDataSetDAO.createTable();
    }

    public void deleteTable() {
        userDataSetDAO.deleteTable();
    }

    @Override
    public void save(UserDataSet user) throws SQLException {
        userDataSetDAO.save(user);
    }

    @Override
    public UserDataSet load(long id) throws SQLException {
        return userDataSetDAO.read(id);
    }

    @Override
    public List<UserDataSet> load(Class<UserDataSet> clazz) {
        return userDataSetDAO.readAll();
    }

    public List<String> getAllTables() throws SQLException {
        List<String> tablesList = new ArrayList<>();
        String[] types = {"TABLE"};
        ResultSet resultSet = getConnection().getMetaData().getTables(null, null, "%", types);
        while (resultSet.next()) {
            tablesList.add(resultSet.getString("TABLE_NAME"));
        }
        return tablesList;
    }

    protected Connection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        try {
            connection.close();
            LOG.info("Connection closed. Bye!");
        } catch (SQLException e) {
            LOG.error("Failed to close connection to DB", e);
        }
    }
}
