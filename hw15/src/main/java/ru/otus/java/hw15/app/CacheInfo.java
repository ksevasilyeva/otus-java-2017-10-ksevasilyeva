package ru.otus.java.hw15.app;

import ru.otus.java.hw11.cache.CacheEngine;

public class CacheInfo {

    private final long hit;
    private final long miss;
    private final int size;

    public CacheInfo(CacheEngine cacheEngine) {
        hit = cacheEngine.getHitCount();
        miss = cacheEngine.getMissCount();
        size = cacheEngine.getCurrentSize();
    }

    public long getHit() {
        return hit;
    }

    public long getMiss() {
        return miss;
    }

    public int getSize() {
        return size;
    }
}
