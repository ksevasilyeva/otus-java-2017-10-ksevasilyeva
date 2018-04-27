package ru.otus.java.hw16.messagesys.socket;

import ru.otus.java.hw16.common.socket.SocketMessageWorker;

import java.io.IOException;
import java.net.Socket;

public class ServerMessageWorker  extends SocketMessageWorker {

    private final Socket socket;

    public ServerMessageWorker(Socket socket) throws IOException {
        super(socket);
        this.socket = socket;
    }

    public void close() throws IOException {
        super.close();
        socket.close();
    }
}