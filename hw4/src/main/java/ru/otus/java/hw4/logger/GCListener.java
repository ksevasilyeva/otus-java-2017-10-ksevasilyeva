package ru.otus.java.hw4.logger;


import com.sun.management.GarbageCollectionNotificationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

public class GCListener implements NotificationListener {

    private static final Logger LOG = LoggerFactory.getLogger(GCListener.class);

    private static int youngCount = 0;
    private static int oldCount = 0;

    @Override
    public void handleNotification(Notification notification, Object handback) {

        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            GarbageCollectionNotificationInfo notificationInfo =
                GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

            GCEventInfo eventInfo = GCEventInfo.parse(notificationInfo);

            LOG.info(eventInfo.toString());

            if(eventInfo.getGeneration().equals(GCEventInfo.Generation.YOUNG)) {
                youngCount ++;
            }

            if(eventInfo.getGeneration().equals(GCEventInfo.Generation.OLD)) {
                oldCount ++;
            }
        }
    }

    public static int getYoungCount() {
        return youngCount;
    }

    public static int getOldCount() {
        return oldCount;
    }
}