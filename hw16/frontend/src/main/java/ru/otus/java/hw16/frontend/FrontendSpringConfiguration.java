package ru.otus.java.hw16.frontend;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.java.hw16.common.Props;
import ru.otus.java.hw16.frontend.socket.FrontendMessageWorker;

import java.io.IOException;
import java.net.Socket;

@Configuration
public class FrontendSpringConfiguration {

    @Bean
    public Socket socket() throws IOException {
        return new Socket(Props.SOCKET_HOST, Props.FRONTDEND_SOCKET_PORT);
    }

    @Bean(initMethod = "init")
    public FrontendMessageWorker frontendMessageWorker() throws IOException{
        return new FrontendMessageWorker(socket());
    }

}
