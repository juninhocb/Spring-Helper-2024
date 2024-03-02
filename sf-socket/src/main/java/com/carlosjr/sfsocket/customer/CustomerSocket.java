package com.carlosjr.sfsocket.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Arrays;

@Component
public class CustomerSocket {
    private static final Logger logger = LoggerFactory.getLogger(CustomerSocket.class);
    private final CustomerService customerService;
    private static final int PORT = 8080;

    public CustomerSocket(CustomerService customerService){
        this.customerService = customerService;
    }
    public void startServer(){
        try(ServerSocket serverSocket = new ServerSocket(PORT)){

            logger.info("Server started on {} at {}. Listening for connections", PORT, LocalTime.now().withNano(0));

            var communication =  serverSocket.accept();

            try(InputStream data = communication.getInputStream()){

                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data))){
                    var responseBuilder = new StringBuilder();
                    String line;
                    CustomerResourcesEnum customerResourcesEnum = null;
                    int counter = 0;
                    while ( ( line = bufferedReader.readLine() ) != null){

                        if ( counter == 0 ){

                            int resourceId =  Integer.parseInt(line);

                            switch (resourceId){
                                case 0:
                                    customerResourcesEnum = CustomerResourcesEnum.FIND_ALL;
                                    responseBuilder.append(customerService.getAll());
                                    break;
                                case 1:
                                    customerResourcesEnum = CustomerResourcesEnum.FIND_BY_ID;
                                    break;
                                case 2:
                                    customerResourcesEnum = CustomerResourcesEnum.CREATE;
                                    break;
                                case 3:
                                    customerResourcesEnum = CustomerResourcesEnum.DELETE;
                                    break;
                                default:
                                    throw new IllegalStateException("Not a valid resource");
                            }

                        }

                        if ( counter == 1){

                            switch (customerResourcesEnum){
                                /*Assumes that object comes in name:age format */
                                case CustomerResourcesEnum.CREATE -> {

                                    var name = Arrays.toString(line.getBytes(StandardCharsets.UTF_8))
                                            .split(":")[0];

                                    var age = Arrays.toString(line.getBytes(StandardCharsets.UTF_8))
                                            .split(":")[1];

                                    var createdCustomer = customerService.create(Customer.builder()
                                            .name(name)
                                            .age(Integer.valueOf(age))
                                            .build());

                                    assert createdCustomer != null;

                                    responseBuilder.append(createdCustomer);
                                }

                                case CustomerResourcesEnum.FIND_BY_ID -> {

                                    var customer = customerService.getById( Integer.parseInt(line));

                                    assert customer != null;

                                    responseBuilder.append(customer);

                                }

                                case CustomerResourcesEnum.DELETE -> {
                                    customerService.deleteById( Integer.parseInt(line));

                                    responseBuilder.append("Deleted successfully");
                                }

                            }

                        }

                        if ( customerResourcesEnum.equals(CustomerResourcesEnum.FIND_ALL))
                            break;

                        counter++;

                    }

                    var response = communication.getOutputStream();

                    response.write(responseBuilder.toString().getBytes(StandardCharsets.UTF_8));
                    response.flush();
                    response.close();

                }

            }

        }catch (IOException ex){
            logger.error("Something went wrong on dealing with server socket {}", ex.getMessage());
        }
    }

}
