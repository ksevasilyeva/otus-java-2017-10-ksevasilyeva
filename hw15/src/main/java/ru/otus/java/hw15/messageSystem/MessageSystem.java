package ru.otus.java.hw15.messageSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSystem {

    private final static Logger LOG = LoggerFactory.getLogger(MessageSystem.class);
    private static final int DEFAULT_STEP_TIME = 10;

    private final List<Thread> workers;
    private final Map<Address, ConcurrentLinkedQueue<Message>> messagesMap;
    private final Map<Address, Addressee> addresseeMap;

    public MessageSystem() {
        workers = new ArrayList<>();
        messagesMap = new HashMap<>();
        addresseeMap = new HashMap<>();
    }

    public void addAddressee(Addressee addressee) {
        addresseeMap.put(addressee.getAddress(), addressee);
        messagesMap.put(addressee.getAddress(), new ConcurrentLinkedQueue<>());
    }

    public void sendMessage(Message message) {
        LOG.info("Message: {}, From: {}, To: {}", message.getClass().getSimpleName(), message.getFrom().getId(), message.getTo().getId());
        messagesMap.get(message.getTo()).add(message);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void start() {
        for (Map.Entry<Address, Addressee> entry : addresseeMap.entrySet()) {
            String name = "MS-worker-" + entry.getKey().getId();
            Thread thread = new Thread(() -> {
                while (true) {

                    ConcurrentLinkedQueue<Message> queue = messagesMap.get(entry.getKey());
                    while (!queue.isEmpty()) {
                        Message message = queue.poll();
                        message.exec(entry.getValue());
                    }
                    try {
                        Thread.sleep(MessageSystem.DEFAULT_STEP_TIME);
                    } catch (InterruptedException e) {
                        LOG.info("Thread interrupted. Finishing: " + name);
                        return;
                    }
                    if (Thread.currentThread().isInterrupted()) {
                        LOG.info("Finishing: " + name);
                        return;
                    }
                }
            });
            thread.setName(name);
            thread.start();
            LOG.info("Worker {} started", name);
            workers.add(thread);
        }
        LOG.info("messaging system started");
    }

    public void dispose() {
        workers.forEach(Thread::interrupt);
        LOG.info("Stop message system");
    }
}
