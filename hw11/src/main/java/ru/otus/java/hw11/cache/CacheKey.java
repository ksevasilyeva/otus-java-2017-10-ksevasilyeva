package ru.otus.java.hw11.cache;

import ru.otus.java.hw10.data.DataSet;

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
