package ru.otus.java.hw15.app;


import ru.otus.java.hw15.messageSystem.Address;
import ru.otus.java.hw15.messageSystem.Addressee;
import ru.otus.java.hw15.messageSystem.Message;

public abstract class MsgToDB extends Message {

    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBService) addressee);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public abstract void exec(DBService dbService);
}