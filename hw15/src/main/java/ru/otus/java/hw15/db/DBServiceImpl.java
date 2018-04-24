package ru.otus.java.hw15.db;

import ru.otus.java.hw11.cache.CacheEngine;
import ru.otus.java.hw15.app.DBService;
import ru.otus.java.hw15.messages.MessageSystemContext;
import ru.otus.java.hw15.messageSystem.Address;
import ru.otus.java.hw15.messageSystem.MessageSystem;

public class DBServiceImpl implements DBService {

    private final CacheEngine cacheEngine;
    final private Address address;
    final private MessageSystemContext messageSystemContext;

    public DBServiceImpl(Address address, MessageSystemContext messageSystemContext, CacheEngine cacheEngine) {
        this.address = address;
        this.messageSystemContext = messageSystemContext;
        this.cacheEngine = cacheEngine;
    }

    @Override
    public void init() {
        messageSystemContext.getMessageSystem().addAddressee(this);
    }

    @Override
    public CacheEngine getCacheEngine() {
        return cacheEngine;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return messageSystemContext.getMessageSystem();
    }
}
