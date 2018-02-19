package ru.otus.java.hw11.db;

import ru.otus.java.hw10.base.DBService;
import ru.otus.java.hw10.data.UserDataSet;

import ru.otus.java.hw10.data.DataSet;
import ru.otus.java.hw11.cache.CacheEngine;
import ru.otus.java.hw11.cache.CacheKey;

import java.sql.SQLException;
import java.util.List;

public class CacheDbService implements DBService {

    DBService dbService;
    CacheEngine<DataSet> cacheEngine;

    public CacheDbService(DBService dbService, CacheEngine<DataSet> cacheEngine) {
        this.dbService = dbService;
        this.cacheEngine = cacheEngine;
    }

    @Override
    public void save(UserDataSet user) throws SQLException {
        dbService.save(user);
        cacheEngine.put(CacheKey.getKey(user), user);
    }

    @Override
    public UserDataSet load(long id) throws SQLException {
        DataSet dataSet = cacheEngine.get(new CacheKey(UserDataSet.class).buildKey(id));

        if (dataSet == null) {
            dataSet = dbService.load(id);
            if (dataSet != null) {
                cacheEngine.put(CacheKey.getKey(dataSet), dataSet);
            }
        }
        return (UserDataSet) dataSet;
    }

    @Override
    public List<UserDataSet> load(Class<UserDataSet> clazz) {
        List<UserDataSet> result = dbService.load(clazz);
        for (DataSet entity : result) {
            cacheEngine.put(CacheKey.getKey(entity), entity);
        }
        return result;
    }

    @Override
    public void close() {
        dbService.close();
        cacheEngine.dispose();
    }
}
