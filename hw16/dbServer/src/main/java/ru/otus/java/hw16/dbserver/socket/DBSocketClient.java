package ru.otus.java.hw16.dbserver.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.hw16.common.Props;
import ru.otus.java.hw16.common.app.CacheInfo;
import ru.otus.java.hw16.common.messages.CacheDataMessage;
import ru.otus.java.hw16.common.messages.Message;
import ru.otus.java.hw16.common.messages.RequestMessage;
import ru.otus.java.hw16.dbserver.cache.CacheEngine;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DBSocketClient {
    private static final Logger LOG = LoggerFactory.getLogger(DBSocketClient.class);

    private final DBMessageWorker client;
    private final CacheEngine cacheEngine;

    public DBSocketClient(DBMessageWorker client, CacheEngine cacheEngine) throws IOException {
        this.client = client;
        this.cacheEngine = cacheEngine;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void start() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    RequestMessage message = (RequestMessage) client.take();
                    if (message != null && message.getDirection().equals(Props.DB_DIRECTION) && message.getBackPortNumber() != 0) {
                        LOG.info("Msg: " + message.toString());
                        sendMessage(
                            new CacheDataMessage(new CacheInfo(cacheEngine.getCurrentSize(), cacheEngine.getHitCount(),
                                cacheEngine.getMissCount()), Props.FRONTEND_DIRECTION, 0, message.getBackPortNumber()));
                    }
                }
            } catch (InterruptedException e) {
                LOG.error("", e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void sendMessage(Message message) {
        LOG.info("Msg: " + message.toString());
        client.send(message);
    }
}
