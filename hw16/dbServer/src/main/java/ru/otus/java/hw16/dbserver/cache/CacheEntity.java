package ru.otus.java.hw16.dbserver.cache;

import java.lang.ref.SoftReference;

public class CacheEntity<V> {

    private String key;
    private SoftReference<V> value;
    private long creationTime;
    private long lastAccessTime;

    public CacheEntity(String key, V value) {
        this.key = key;
        this.value = new SoftReference<>(value);
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    private long getCurrentTime(){
        return System.currentTimeMillis();
    }

    public String getKey(){
        return key;
    }

    public V getValue(){
        setAccessed();
        return value.get();
    }

    public long getCreationTime(){
        return creationTime;
    }

    public long getLastAccessTime(){
        return lastAccessTime;
    }

    public void setAccessed(){
        lastAccessTime = getCurrentTime();
    }
}
