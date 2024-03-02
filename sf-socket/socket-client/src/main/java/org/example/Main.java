package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Format {@Query}
 * [ActionIndex]P[Args]
 * ActionIndex:
 * 0 -> Display all customers
 * 1 -> Display a customer by id
 * 2 -> Create a new Customer
 * 3 -> Delete a customer by id
 * Args:
 * 0 Action -> Does not have
 * 1 Action -> 1P[id]
 * 2 Action -> 2P[name]:[age]
 * 3 Action -> 3P[id]
 *
 */

public class Main {
    public static void main(String[] args) {

        final Logger logger = Logger.getLogger(Main.class.getName());
        logger.setLevel(Level.FINE);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINE);
        logger.addHandler(consoleHandler);

        final SocketAddress myAppEndpoint = new InetSocketAddress("localhost", 8080) {
        };

        var message = new StringBuilder();

        try (Scanner sc = new Scanner(System.in)) {
            String input;
            do {
                input = sc.next();
                if ( !input.equals("9")){
                    message.append(input).append(" ");
                }
            } while (!input.equals("9"));
        }

        logger.log(Level.FINE, "Message ::: " + message);


        try (Socket socketWriter = new Socket()) {
            socketWriter.connect(myAppEndpoint);
            var sender = socketWriter.getOutputStream();
            sender.write(message.toString().getBytes(StandardCharsets.UTF_8));
            sender.flush();
            sender.close();
        } catch (IOException ex){
            logger.log(Level.SEVERE, "Err while handling socket writer " + ex.getMessage());
        }

    }
}