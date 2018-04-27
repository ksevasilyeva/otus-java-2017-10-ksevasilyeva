package ru.otus.java.hw16.messagesys.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DBServerRunner {
    private static final Logger LOG = LoggerFactory.getLogger(DBServerRunner.class);

    public static void run() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            try {
                //FIXME: rm hardcoded address
                new ProcessRunnerImpl().start( "java -jar /Users/kvasileva/IdeaProjects/homework201710/hw16/dbServer/target/dbserver.jar");
                LOG.info("DB server stared");
            } catch (IOException e) {
                LOG.error("Failed to start DB", e);
            }
        }, 10, TimeUnit.SECONDS);
    }
}
