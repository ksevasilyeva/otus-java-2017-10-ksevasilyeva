package ru.otus.java.hw16.frontend.socket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.springframework.context.ApplicationContext;

public class StatisticWebSocketCreator implements WebSocketCreator {

    ApplicationContext context;

    public StatisticWebSocketCreator(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        return context.getBean("statisticsWebSocket");
    }
}
