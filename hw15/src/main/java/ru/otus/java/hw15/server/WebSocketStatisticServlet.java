package ru.otus.java.hw15.server;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.java.hw15.app.FrontendService;
import ru.otus.java.hw15.frontEnd.StatisticWebSocketCreator;

import java.util.concurrent.TimeUnit;

public class WebSocketStatisticServlet extends WebSocketServlet {
    private final static long LOGOUT_TIME = TimeUnit.MINUTES.toMillis(10);
    private FrontendService frontendService;

    public WebSocketStatisticServlet(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        webSocketServletFactory.setCreator(new StatisticWebSocketCreator(frontendService));
    }
}
