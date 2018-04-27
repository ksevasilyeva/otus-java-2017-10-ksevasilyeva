package ru.otus.java.hw16.common.app;

public class CacheInfo {

    private final int currentCacheSize;

    private final long hitsCount;

    private final long missesCount;

    public CacheInfo(int currentCacheSize, long hitsCount, long missesCount) {
        this.currentCacheSize = currentCacheSize;
        this.hitsCount = hitsCount;
        this.missesCount = missesCount;
    }

    public int getCurrentCacheSize() {
        return currentCacheSize;
    }

    public long getHitsCount() {
        return hitsCount;
    }

    public long getMissesCount() {
        return missesCount;
    }
}
