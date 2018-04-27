package ru.otus.java.hw16.dbserver.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.java.hw16.dbserver.config.DBSpringConfiguration;
import ru.otus.java.hw16.dbserver.generator.UserDataSetGenerator;

public class DBServerApplication {
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(DBSpringConfiguration.class);

//        ServletContext servletContext = sce.getServletContext();
//        servletContext.setAttribute("cache", appContext.getBean(CacheEngine.class));
//        servletContext.setAttribute("accountDBService", appContext.getBean(AccountDBService.class));

        UserDataSetGenerator dataGenerator = appContext.getBean(UserDataSetGenerator.class);
        new Thread(dataGenerator).start();
    }
}
