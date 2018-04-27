package ru.otus.java.hw16.dbserver.cache;

public interface CacheEngine<V> {

    void put(String key, V value);

    V get(String key);

    int getHitCount();

    int getMissCount();

    void dispose();

    int getCurrentSize();
}
