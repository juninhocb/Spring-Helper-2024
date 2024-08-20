package com.example.rabbitmq_client;

import java.security.Principal;

interface PersonService {
    String PERSON_EXCHANGE = "person-ex";
    void updatePoints(Principal principal);

}
