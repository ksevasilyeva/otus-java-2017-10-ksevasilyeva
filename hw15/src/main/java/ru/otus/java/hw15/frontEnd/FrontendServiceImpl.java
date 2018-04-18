package ru.otus.java.hw15.frontEnd;

import ru.otus.java.hw15.app.CacheInfo;
import ru.otus.java.hw15.app.FrontendService;
import ru.otus.java.hw15.app.MessageSystemContext;
import ru.otus.java.hw15.db.StatRequestMsg;
import ru.otus.java.hw15.messageSystem.Address;
import ru.otus.java.hw15.messageSystem.Message;
import ru.otus.java.hw15.messageSystem.MessageSystem;

public class FrontendServiceImpl implements FrontendService {

    private final Address address;
    private final MessageSystemContext context;

    public FrontendServiceImpl(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
    }

    @Override
    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public void handleCacheRequest(StatisticsWebSocket socket) {
        Message message = new StatRequestMsg(getAddress(), context.getDbAddress(), socket);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void handleCacheResponse(StatisticsWebSocket socket, CacheInfo ci) {
        socket.sendStatisticsAnswer(ci);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
