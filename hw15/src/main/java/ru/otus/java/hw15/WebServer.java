package ru.otus.java.hw15;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.otus.java.hw11.cache.CacheEngine;
import ru.otus.java.hw12.db.AccountDBService;
import ru.otus.java.hw12.db.AccountDBServiceImpl;
import ru.otus.java.hw12.server.LoginServlet;
import ru.otus.java.hw12.server.SignUpServlet;
import ru.otus.java.hw15.app.FrontendService;
import ru.otus.java.hw15.server.StatisticServlet;
import ru.otus.java.hw15.server.WebSocketStatisticServlet;

public class WebServer {

    private final static String PUBLIC_HTML = "/public_html";

    static Server configureWebServer(int port, FrontendService frontendService, CacheEngine cacheEngine) {

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setBaseResource(Resource.newClassPathResource(PUBLIC_HTML));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        AccountDBService accountDBService = new AccountDBServiceImpl();
        context.addServlet(new ServletHolder(new LoginServlet(accountDBService)), "/login");
        context.addServlet(new ServletHolder(new SignUpServlet(accountDBService)), "/signup");
        context.addServlet(new ServletHolder(new StatisticServlet(cacheEngine)), "/stat");
        context.addServlet(new ServletHolder(new WebSocketStatisticServlet(frontendService)), "/ws-stat");

        Server server = new Server(port);
        server.setHandler(new HandlerList(resourceHandler, context));
        return server;
    }
}
