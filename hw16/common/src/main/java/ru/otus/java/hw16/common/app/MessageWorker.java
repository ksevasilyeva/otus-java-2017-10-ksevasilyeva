package ru.otus.java.hw16.common.app;

import ru.otus.java.hw16.common.messages.Message;

import java.io.IOException;

public interface MessageWorker {
    void init();

    void send(Message message);

    Message pull();

    Message take() throws InterruptedException;

    int getSocketRemotePort();

    void close() throws IOException;
}
