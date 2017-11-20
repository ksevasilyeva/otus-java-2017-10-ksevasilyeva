package ru.otus.java.hw4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.java.hw4.app.App;
import ru.otus.java.hw4.logger.GCListener;
import ru.otus.java.hw4.logger.GCLogger;

import java.io.IOException;
import java.lang.management.ManagementFactory;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(GCListener.class);

    public static void main(String[] args) throws IOException {

        LOG.info("hw4 started with pid = " + ManagementFactory.getRuntimeMXBean().getName());

        GCLogger.startMonitoring();

        try {
            App.app().startApp();
        } catch (OutOfMemoryError err) {
            LOG.error("Caught OutOfMemoryError", err);
        } finally {
            GCLogger.printFinalInfo();
        }
    }
}
