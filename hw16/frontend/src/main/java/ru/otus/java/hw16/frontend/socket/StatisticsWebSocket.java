package ru.otus.java.hw16.frontend.socket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.hw16.common.Props;
import ru.otus.java.hw16.common.messages.RequestMessage;
import ru.otus.java.hw16.frontend.FrontendInitializer;

import java.io.IOException;
import java.net.Socket;

@WebSocket
public class StatisticsWebSocket {

    private static final int PORT = FrontendInitializer.getContext().getBean(Socket.class).getLocalPort();

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsWebSocket.class);

    private Session session;
    private FrontendMessageWorker socketMsgWorker;

    public StatisticsWebSocket(FrontendMessageWorker socketMsgWorker) {
        this.socketMsgWorker = socketMsgWorker;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
        this.socketMsgWorker.init();
        LOG.info("OnOpen socket");
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        try {
            socketMsgWorker.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOG.info("onClose socket");
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        socketMsgWorker.send(
            new RequestMessage(RequestMessage.class,
                Props.FRONTEND_CACHE_DATA_REQUEST,
                Props.DB_DIRECTION, PORT, 0));
    }
}
