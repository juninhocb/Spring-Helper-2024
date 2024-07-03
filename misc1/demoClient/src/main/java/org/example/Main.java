package org.example;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        Main main = new Main();
        main.process();
    }

    private void process(){

        final int numberOfThreads = 20;
        try (ExecutorService service = Executors.newFixedThreadPool(numberOfThreads)){
            for(int i = 0; i < numberOfThreads; i++){
                service.submit(this::doRequest);
            }
        };


    }

    private void doRequest(){

        final String URL = "http://localhost:8080/stub/1";
        ResponseEntity<String> getResponse = restTemplate
                .exchange(URL, HttpMethod.GET, HttpEntity.EMPTY, String.class);

        System.out.println(getResponse.getBody());
    }
}