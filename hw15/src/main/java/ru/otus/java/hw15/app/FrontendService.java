package ru.otus.java.hw15.app;


import ru.otus.java.hw15.frontEnd.StatisticsWebSocket;
import ru.otus.java.hw15.messageSystem.Addressee;

public interface FrontendService extends Addressee {

    void init();

    void handleCacheRequest(StatisticsWebSocket socket);

    void handleCacheResponse(StatisticsWebSocket socket, CacheInfo ci);
}
