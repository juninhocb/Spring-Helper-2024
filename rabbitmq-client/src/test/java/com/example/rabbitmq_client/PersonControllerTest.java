package com.example.rabbitmq_client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldUpdatePersonPoints(){

        String url = "/api/person/points";

        HttpHeaders johnHeader = new HttpHeaders();
        johnHeader.setBasicAuth("John", "jpass");

        HttpHeaders anneHeader = new HttpHeaders();
        anneHeader.setBasicAuth("Anne", "apass");

        HttpHeaders charlesHeader = new HttpHeaders();
        charlesHeader.setBasicAuth("Charles", "cpass");

        var headers = List.of(johnHeader, anneHeader, charlesHeader);

        for (HttpHeaders header : headers){
            var response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(header), Void.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        }
    }


}