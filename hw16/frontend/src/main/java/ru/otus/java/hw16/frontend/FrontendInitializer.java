package ru.otus.java.hw16.frontend;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;

public class FrontendInitializer implements WebApplicationInitializer {

    private static AnnotationConfigWebApplicationContext context =
        new AnnotationConfigWebApplicationContext();

    @Override
    public void onStartup(ServletContext servletContext) {
        context.register(FrontendSpringConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(context));
    }

    public static AnnotationConfigWebApplicationContext getContext() {
        return context;
    }
}
