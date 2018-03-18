package ru.otus.java.hw12;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.otus.java.hw10.data.DataSet;
import ru.otus.java.hw10.dbService.DBServiceHibernateImpl;
import ru.otus.java.hw11.cache.CacheEngine;
import ru.otus.java.hw11.cache.CacheEngineImpl;
import ru.otus.java.hw11.db.CacheDbService;
import ru.otus.java.hw12.db.AccountDBService;
import ru.otus.java.hw12.db.AccountDBServiceImpl;
import ru.otus.java.hw12.server.LoginServlet;
import ru.otus.java.hw12.server.SignUpServlet;
import ru.otus.java.hw12.server.StatisticServlet;

public class Main {

    private final static int PORT = 8093;
    private final static String PUBLIC_HTML = "/public_html";

    public static void main(String[] args) throws Exception {
        CacheEngine<DataSet> cacheEngine = new CacheEngineImpl<>(5, 5000, 0, false);
        CacheDbService dbService = new CacheDbService(new DBServiceHibernateImpl(), cacheEngine);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setBaseResource(Resource.newClassPathResource(PUBLIC_HTML));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        AccountDBService accountDBService = new AccountDBServiceImpl();

        context.addServlet(new ServletHolder(new LoginServlet(accountDBService)), "/login");
        context.addServlet(new ServletHolder(new SignUpServlet(accountDBService)), "/signup");
        context.addServlet(new ServletHolder(new StatisticServlet(cacheEngine)), "/stat");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();

        UserDataSetGenerator dataGenerator = new UserDataSetGenerator(dbService);
        new Thread(dataGenerator).start();

        server.join();
    }
}
