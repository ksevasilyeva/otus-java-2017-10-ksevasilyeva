package ru.otus.java.hw16.messagesys;

import ru.otus.java.hw16.messagesys.runner.DBServerRunner;
import ru.otus.java.hw16.messagesys.server.MessageServer;

public class MessageServerApp {


    public static void main(String[] args) {
        MessageServer messageServer = new MessageServer();
        messageServer.start();
        DBServerRunner.run();
        //TODO front run
    }
}
