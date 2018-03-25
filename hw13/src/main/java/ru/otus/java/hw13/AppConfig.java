package ru.otus.java.hw13;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.java.hw10.base.DBService;
import ru.otus.java.hw10.data.DataSet;
import ru.otus.java.hw10.dbService.DBServiceHibernateImpl;
import ru.otus.java.hw11.cache.CacheEngine;
import ru.otus.java.hw11.cache.CacheEngineImpl;
import ru.otus.java.hw11.db.CacheDbService;
import ru.otus.java.hw13.db.AccountDBService;
import ru.otus.java.hw13.db.AccountDBServiceImpl;

@Configuration
@ComponentScan
public class AppConfig {

    @Bean
    CacheDbService getCacheDbService(DBService dbService, CacheEngine<DataSet> cacheEngine) {
        return new CacheDbService(dbService, cacheEngine);
    }

    @Bean
    UserDataSetGenerator userDataSetGenerator(@Qualifier("getCacheDbService") CacheDbService dbService) {
        return new UserDataSetGenerator(dbService);
    }

    @Bean
    CacheEngine<DataSet> getCache() {
        return new CacheEngineImpl<>();
    }

    @Bean
    AccountDBService getAccountDbService() {
        return new AccountDBServiceImpl();
    }

    @Bean
    DBService getDbService() {
        return new DBServiceHibernateImpl();
    }
}
