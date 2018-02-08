package ru.otus.java.hw9.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.hw9.ReflectionHelper;
import ru.otus.java.hw9.data.DataSet;
import ru.otus.java.hw9.executor.DbExecutor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.java.hw9.db.QueryStorage.CREATE_TABLE_USER;
import static ru.otus.java.hw9.db.QueryStorage.DELETE_TABLE_USER;
import static ru.otus.java.hw9.db.QueryStorage.GET_DATA_BY_ID;
import static ru.otus.java.hw9.db.QueryStorage.SAVE_DATA;

public class DBServiceConnection implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(DBServiceConnection.class);

    private final Connection connection;
    private final DbExecutor executor;

    public DBServiceConnection() {
        connection = ConnectionHelper.getConnection();
        executor = new DbExecutor(getConnection());
    }

    public void createTable() throws SQLException {
        executor.executeUpdate(CREATE_TABLE_USER.getQuery());
        LOG.info("Table created");
    }

    public void deleteTable() throws SQLException {
        executor.executeUpdate(DELETE_TABLE_USER.getQuery());
        LOG.info("Table deleted");
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

    public <T extends DataSet> void save(T user) throws SQLException{
        String fieldsToString = ReflectionHelper.getFieldsValues(user);
        executor.executeUpdate(String.format(SAVE_DATA.getQuery(), fieldsToString));

        LOG.info("Executed: " + String.format(SAVE_DATA.getQuery(), fieldsToString));
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        return executor.executeQuery(GET_DATA_BY_ID.getQuery() + id, queryResult -> {
            queryResult.next();
            Object[] parameters = new Object[queryResult.getMetaData().getColumnCount()];
            for (int i = 0; i < parameters.length; i++) {
                parameters[i] = queryResult.getObject(i + 1);
            }
            return ReflectionHelper.newInstanceWithParameters(clazz, parameters);
        });
    }

    protected Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
        LOG.info("Connection closed. Bye!");
    }
}
