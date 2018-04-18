package ru.otus.java.hw15;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.hw10.data.DataSet;
import ru.otus.java.hw10.dbService.DBServiceHibernateImpl;
import ru.otus.java.hw11.cache.CacheEngine;
import ru.otus.java.hw11.cache.CacheEngineImpl;
import ru.otus.java.hw11.db.CacheDbService;
import ru.otus.java.hw12.UserDataSetGenerator;
import ru.otus.java.hw15.app.DBService;
import ru.otus.java.hw15.app.FrontendService;
import ru.otus.java.hw15.app.MessageSystemContext;
import ru.otus.java.hw15.db.DBServiceImpl;
import ru.otus.java.hw15.frontEnd.FrontendServiceImpl;
import ru.otus.java.hw15.messageSystem.Address;
import ru.otus.java.hw15.messageSystem.MessageSystem;

public class App {

    private final static int PORT = 8093;
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {

        MessageSystem messageSystem = new MessageSystem();
        MessageSystemContext context = new MessageSystemContext(messageSystem);
        Address frontAddress = new Address("FrontEnd");
        context.setFrontAddress(frontAddress);
        Address dbAddress = new Address("DbCache");
        context.setDbAddress(dbAddress);

        FrontendService frontendService = new FrontendServiceImpl(context, frontAddress);
        frontendService.init();


        CacheEngine<DataSet> cacheEngine = new CacheEngineImpl<>(5, 5000, 0, false);
        CacheDbService dbService = new CacheDbService(new DBServiceHibernateImpl(), cacheEngine);
        DBService cacheService = new DBServiceImpl(dbAddress, context, cacheEngine);
        cacheService.init();

        messageSystem.start();

        Server server = WebServer.configureWebServer(PORT, frontendService, cacheEngine);
        server.start();
        LOG.info("Server started. http://localhost:{}", PORT);

        UserDataSetGenerator dataGenerator = new UserDataSetGenerator(dbService);
        new Thread(dataGenerator).start();

        server.join();
        messageSystem.dispose();
    }
}
