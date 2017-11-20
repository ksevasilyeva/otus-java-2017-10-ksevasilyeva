package ru.otus.java.hw4.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.NotificationEmitter;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class GCLogger {

    private static final Logger LOG = LoggerFactory.getLogger(GCLogger.class);

    public static void startMonitoring() {
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        //Install a GCListener for each bean
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            emitter.addNotificationListener(new GCListener(), null, null);
        }
    }

    public static void printFinalInfo() {
        LOG.info("Finishing executing: YOUNG: {}, OLD: {}", GCListener.getYoungCount(), GCListener.getOldCount());
    }
}
