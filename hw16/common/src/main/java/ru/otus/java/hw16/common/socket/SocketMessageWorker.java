package ru.otus.java.hw16.common.socket;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.java.hw16.common.app.MessageWorker;
import ru.otus.java.hw16.common.messages.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketMessageWorker implements MessageWorker {

    private static final Logger logger = Logger.getLogger(SocketMessageWorker.class.getName());
    private static final int WORKERS_COUNT = 2;

    private final BlockingQueue<Message> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> input = new LinkedBlockingQueue<>();

    private final ExecutorService executor;
    private final Socket socket;

    public SocketMessageWorker(Socket socket) {
        this.socket = socket;
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    public void send(Message message) {
        logger.info("Sending a message: " + message.toString());
        output.add(message);
    }

    public Message pull() {
        return input.poll();
    }

    public Message take() throws InterruptedException {
        Message tokenMessage = input.take();
        logger.info("Took a message: " + tokenMessage.toString());

        return tokenMessage;
    }

    @Override
    public int getSocketRemotePort() {
        return socket.getPort();
    }


    public void addMessage(Message message) {
        input.add(message);
    }

    public void close() throws IOException {
        executor.shutdown();
    }

    public void init() {
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }

    private void sendMessage() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                Message message = output.take();
                String json = new Gson().toJson(message);
                logger.info("Jsonified message: " + json);
                out.println(json);
                out.println();
            }
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void receiveMessage() {
        try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = inputReader.readLine()) != null) {
                logger.info("Getting line: " + inputLine);
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) {
                    String json = stringBuilder.toString();
                    logger.info("Got jsonified message: " + json);
                    Message message = getMessageFromJSON(json);
                    input.add(message);
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (IOException | ParseException e) {
            logger.log(Level.SEVERE, "Couldn't read from socket: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Message getMessageFromJSON(String json) throws ParseException, ClassNotFoundException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        String className = (String) jsonObject.get(Message.CLASS_NAME_VARIABLE);
        Class<?> messageClass = Class.forName(className);
        Message parsedMessage = (Message) new Gson().fromJson(json, messageClass);
        logger.info("Parsed json to message: " + parsedMessage);

        return parsedMessage;
    }
}