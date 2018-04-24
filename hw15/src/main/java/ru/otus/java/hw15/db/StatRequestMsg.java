package ru.otus.java.hw15.db;

import ru.otus.java.hw15.app.CacheInfo;
import ru.otus.java.hw15.app.DBService;
import ru.otus.java.hw15.messages.MsgToDB;
import ru.otus.java.hw15.frontEnd.StatisticsWebSocket;
import ru.otus.java.hw15.messageSystem.Address;

public class StatRequestMsg extends MsgToDB {

    private final StatisticsWebSocket socket;

    public StatRequestMsg(Address from, Address to, StatisticsWebSocket socket) {
        super(from, to);
        this.socket = socket;
    }

    @Override
    public void exec(DBService dbService) {
        CacheInfo cacheInfo = new CacheInfo(dbService.getCacheEngine());
        dbService.getMS().sendMessage(new StatResponseMsg(getTo(), getFrom(), socket, cacheInfo));
    }
}
