package ru.otus.java.hw15.app;

import ru.otus.java.hw11.cache.CacheEngine;
import ru.otus.java.hw15.messageSystem.Addressee;

public interface DBService extends Addressee {
    void init();

    CacheEngine getCacheEngine();
}