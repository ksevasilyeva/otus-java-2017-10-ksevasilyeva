package ru.otus.java.hw11.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class CacheEngineImpl<V> implements CacheEngine<V> {

    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElementsInCache;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private int hit = 0;
    private int miss = 0;

    private final Map<String, CacheEntity<V>> elements = new LinkedHashMap<>();

    private final Timer timer = new Timer();

    public CacheEngineImpl() {
        this(3, 0, 0, true);
    }

    public CacheEngineImpl(int maxElementsInCache, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElementsInCache = maxElementsInCache;
        this.lifeTimeMs = lifeTimeMs;
        this.idleTimeMs = idleTimeMs;
        this.isEternal = isEternal;
    }


    @Override
    public void put(String key, V value) {
        CacheEntity<V> element = new CacheEntity<>(key, value);

        if (elements.size() == maxElementsInCache) {
            String firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        elements.put(key, element);

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    @Override
    public V get(String key) {
        CacheEntity<V> cacheElement = elements.get(key);
        V cacheElementValue = cacheElement.getValue();
        if (cacheElement != null && cacheElementValue != null) {
            hit++;
        } else {
            miss++;
            elements.remove(key);
            return null;
        }
        return cacheElementValue;
    }

    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final String key, Function<CacheEntity<V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheEntity<V> element = elements.get(key);
                if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }


    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

    public int getCurrentSize() {
        return elements.size();
    }
}

