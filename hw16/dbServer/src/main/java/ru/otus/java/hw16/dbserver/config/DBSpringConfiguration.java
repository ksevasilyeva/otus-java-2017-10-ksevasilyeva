package ru.otus.java.hw16.dbserver.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.java.hw16.common.Props;
import ru.otus.java.hw16.dbserver.cache.CacheEngine;
import ru.otus.java.hw16.dbserver.cache.CacheEngineImpl;
import ru.otus.java.hw16.dbserver.generator.UserDataSetGenerator;
import ru.otus.java.hw16.dbserver.model.DataSet;
import ru.otus.java.hw16.dbserver.service.CacheDbService;
import ru.otus.java.hw16.dbserver.service.DBService;
import ru.otus.java.hw16.dbserver.service.DBServiceHibernateImpl;
import ru.otus.java.hw16.dbserver.socket.DBMessageWorker;
import ru.otus.java.hw16.dbserver.socket.DBSocketClient;

import java.io.IOException;
import java.net.Socket;

@Configuration
public class DBSpringConfiguration {

    @Bean(initMethod = "init")
    public DBMessageWorker dbMessageWorker() throws IOException {
        return new DBMessageWorker(new Socket(Props.SOCKET_HOST, Props.DB_SOCKET_PORT));
    }

    @Bean(initMethod = "start")
    public DBSocketClient socketClient() throws IOException {
        return new DBSocketClient(dbMessageWorker(), cacheService());
    }

    @Bean
    CacheDbService getCacheDbService(DBService dbService, CacheEngine<DataSet> cacheEngine) {
        return new CacheDbService(dbService, cacheEngine);
    }

    @Bean
    UserDataSetGenerator userDataSetGenerator(@Qualifier("getCacheDbService") CacheDbService dbService) {
        return new UserDataSetGenerator(dbService);
    }

    @Bean
    CacheEngine<DataSet> cacheService() {
        return new CacheEngineImpl<>();
    }

    @Bean
    DBService getDbService() {
        return new DBServiceHibernateImpl();
    }
}
