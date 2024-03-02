package com.carlosjr.sfsocket.customer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

@RequiredArgsConstructor
public class TCustomerConnectionHandling implements Runnable{
    private final Socket communication;
    private final CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(TCustomerConnectionHandling.class);
    @SneakyThrows
    @Override
    public void run() {
        try(InputStream data = communication.getInputStream()){

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data))){
                var responseBuilder = new StringBuilder();
                String line;
                CustomerResourcesEnum customerResourcesEnum;
                while ( ( line = bufferedReader.readLine() ) != null){

                    if ( line.isEmpty())
                        continue;

                    int resourceId =  Integer.parseInt(line.trim().split("P")[0]);

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


                    switch (customerResourcesEnum){
                        /*Assumes that object comes in name:age format */
                        case CustomerResourcesEnum.CREATE -> {
                            String customerData = line.split("P")[1];
                            var name = customerData.split(":")[0];

                            var age = customerData.split(":")[1];

                            var createdCustomer = customerService.create(Customer.builder()
                                    .name(name)
                                    .age(Integer.parseInt(age.trim()))
                                    .build());

                            assert createdCustomer != null;

                            responseBuilder.append(createdCustomer);
                        }

                        case CustomerResourcesEnum.FIND_BY_ID -> {

                            var customer = customerService.getById( Integer.parseInt(line.trim().split("P")[1]));

                            assert customer != null;

                            responseBuilder.append(customer);

                        }

                        case CustomerResourcesEnum.DELETE -> {
                            customerService.deleteById( Integer.parseInt(line.trim().split("P")[1]));

                            responseBuilder.append("Deleted successfully");
                        }

                    }

                }

                logger.info("Responding ::: {} data", responseBuilder);

                logger.info("Current customers on database is {}", customerService.getAll());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
