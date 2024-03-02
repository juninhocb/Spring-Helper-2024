package com.carlosjr.sfsocket.customer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.ServerSocket;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class CustomerSocket {
    private static final Logger logger = LoggerFactory.getLogger(CustomerSocket.class);
    private final CustomerService customerService;
    private static final int PORT = 8080;

    public void startServer(){
        try(ServerSocket serverSocket = new ServerSocket(PORT)){

            logger.info("Server started on {} at {}. Listening for connections", PORT, LocalTime.now().withNano(0));

            while(true) {
                var communication =  serverSocket.accept();
                logger.info("New connection arrived!");
                TCustomerConnectionHandling handler = new TCustomerConnectionHandling(communication, customerService);
                Thread manageConnection = new Thread(handler);
                manageConnection.start();
            }

        }catch (IOException ex){
            logger.error("Something went wrong on dealing with server socket {}", ex.getMessage());
        }
    }

}
