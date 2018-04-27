package ru.otus.java.hw16.dbserver.socket;

import ru.otus.java.hw16.common.socket.SocketMessageWorker;

import java.io.IOException;
import java.net.Socket;

public class DBMessageWorker  extends SocketMessageWorker {

    private final Socket socket;

    public DBMessageWorker(Socket socket) {
        super(socket);
        this.socket = socket;
    }

    public void close() throws IOException {
        super.close();
        socket.close();
    }
}
