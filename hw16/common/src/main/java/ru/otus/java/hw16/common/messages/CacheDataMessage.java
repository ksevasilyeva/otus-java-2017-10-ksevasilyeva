package ru.otus.java.hw16.common.messages;

import ru.otus.java.hw16.common.app.CacheInfo;

public class CacheDataMessage extends Message {


    private int currentCacheSize;

    private long hitsCount;

    private long missesCount;

    public CacheDataMessage(CacheInfo data, String address, int backPortNumber, int targetPortNumber) {
        super(CacheDataMessage.class, address, backPortNumber, targetPortNumber);
        this.currentCacheSize = data.getCurrentCacheSize();
        this.hitsCount = data.getHitsCount();
        this.missesCount = data.getMissesCount();
    }

    public CacheInfo makeCacheData() {
        return new CacheInfo(currentCacheSize, hitsCount, missesCount);
    }

    @Override
    public String toString() {
        return "CacheDataMessage{" +
            ", currentCacheSize=" + currentCacheSize +
            ", hitsCount=" + hitsCount +
            ", missesCount=" + missesCount +
            '}';
    }
}