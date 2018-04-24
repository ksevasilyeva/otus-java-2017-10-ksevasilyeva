package ru.otus.java.hw15.db;

import ru.otus.java.hw15.app.CacheInfo;
import ru.otus.java.hw15.app.FrontendService;
import ru.otus.java.hw15.messages.MsgToFrontend;
import ru.otus.java.hw15.frontEnd.StatisticsWebSocket;
import ru.otus.java.hw15.messageSystem.Address;

public class StatResponseMsg extends MsgToFrontend {

    private final StatisticsWebSocket socket;
    private CacheInfo statistics;

    public StatResponseMsg(Address from, Address to, StatisticsWebSocket socket, CacheInfo statistics) {
        super(from, to);
        this.socket = socket;
        this.statistics = statistics;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.handleCacheResponse(socket, statistics);
    }
}