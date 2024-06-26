package org.example;

import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

class JobService implements Runnable {
    private final String URL;
    private final String value;
    public JobService(String value){
        this.URL = "http://localhost:8081/stub";
        this.value = value;
    }

    @Override
    public void run() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> response =  restTemplate
                    .exchange(URL, HttpMethod.POST, new HttpEntity<>(value, headers), String.class);
            System.out.println("for value " + value + " response = " +  response.getBody());
        } catch (RestClientException ex){
            System.out.println("ex" + ex.getMessage());
        }

    }
}
