package ru.otus.java.hw16.frontend.servlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.springframework.context.ApplicationContext;
import ru.otus.java.hw16.frontend.socket.StatisticWebSocketCreator;


import javax.servlet.annotation.WebServlet;
import java.util.concurrent.TimeUnit;

@WebServlet(displayName = "webSocketStatisticServlet",
    name = "webSocketStatisticServlet",
    urlPatterns = "/ws-stat")
public class WebSocketStatisticServlet extends WebSocketServlet {

    private final static long LOGOUT_TIME = TimeUnit.MINUTES.toMillis(10);

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        ApplicationContext context =
            (ApplicationContext) this.getServletContext().getAttribute("SpringBeansContext");
        webSocketServletFactory.setCreator(new StatisticWebSocketCreator(context));
    }
}
