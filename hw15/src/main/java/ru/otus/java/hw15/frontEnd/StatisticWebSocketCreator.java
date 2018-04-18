package ru.otus.java.hw15.frontEnd;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import ru.otus.java.hw15.app.FrontendService;

public class StatisticWebSocketCreator implements WebSocketCreator {

    private final FrontendService frontendService;

    public StatisticWebSocketCreator(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        return new StatisticsWebSocket(frontendService);
    }
}
