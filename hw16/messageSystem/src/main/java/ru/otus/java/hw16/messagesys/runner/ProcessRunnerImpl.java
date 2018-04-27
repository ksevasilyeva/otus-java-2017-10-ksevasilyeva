package ru.otus.java.hw16.messagesys.runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessRunnerImpl {
    private final StringBuffer out = new StringBuffer();
    private Process process;

    public void start(String command) throws IOException {
        process = runProcess(command);
    }

    public void stop() {
        process.destroy();
    }

    public String getOutput() {
        return out.toString();
    }

    private Process runProcess(String command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        StreamListener output = new StreamListener(process.getInputStream(), "OUTPUT");
        output.start();

        return process;
    }

    private class StreamListener extends Thread {
        private final Logger logger = Logger.getLogger(StreamListener.class.getName());

        private final InputStream is;
        private final String type;

        private StreamListener(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        @Override
        public void run() {
            try (InputStreamReader inputStreamReader = new InputStreamReader(is)) {
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    out.append(type).append('>').append(line).append('\n');
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }
}
