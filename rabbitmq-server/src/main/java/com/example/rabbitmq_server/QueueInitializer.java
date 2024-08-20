package com.example.rabbitmq_server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class QueueInitializer implements CommandLineRunner {
    private final RabbitAdmin rabbitAdmin;
    @Override
    public void run(String... args) throws Exception {
        createQueue(PersonListener.PERSON_QUEUE);
        createQueue(PersonListener.TEST_QUEUE);
    }

    private void createQueue(String name){
        Queue queue = QueueBuilder
                .durable(name)
                .build();

        //Create it if it doesn't exist
        rabbitAdmin.declareQueue(queue);

        log.info("Queue {} initialized! ", name);
    }
}
