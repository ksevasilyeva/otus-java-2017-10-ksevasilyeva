package ru.otus.java.hw16.dbserver.cache;


import ru.otus.java.hw16.dbserver.model.DataSet;

public class CacheKey {

    Class clazz;

    public CacheKey(Class clazz) {
        this.clazz = clazz;
    }

    public static String getKey(DataSet dataSet) {
        return new CacheKey(dataSet.getClass())
            .buildKey(dataSet.getId());
    }

    public String buildKey(long id) {
        return new StringBuilder(clazz.getCanonicalName())
            .append("-")
            .append(id).toString();
    }
}
