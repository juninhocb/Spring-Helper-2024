package com.example.rabbitmq_server;

interface PersonListener {

    String PERSON_QUEUE = "person-queue";
    String TEST_QUEUE = "test-queue";
    void listenPerson(PersonDto person);
}
