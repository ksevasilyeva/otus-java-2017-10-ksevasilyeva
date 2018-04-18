package ru.otus.java.hw15.frontEnd;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.hw15.app.CacheInfo;
import ru.otus.java.hw15.app.FrontendService;

import java.io.IOException;

@WebSocket
public class StatisticsWebSocket {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsWebSocket.class);

    private Session session;
    private FrontendService frontendService;

    public StatisticsWebSocket(FrontendService frontendService) {
        this.frontendService = frontendService;
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
        LOG.info("OnOpen socket");
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        LOG.info("onClose socket");
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        frontendService.handleCacheRequest(this);
    }

    public void sendStatisticsAnswer(CacheInfo statistics) {
        try {
            Gson gson = new Gson();
            session.getRemote().sendString(gson.toJson(statistics));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
