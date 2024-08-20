package com.example.rabbitmq_client;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Random;

@Service
@RequiredArgsConstructor
class PersonServiceImpl implements PersonService{

    private final RabbitTemplate rabbitTemplate;
    @Override
    public void updatePoints(Principal principal) {
        int generatedPoints = getPoints();
        rabbitTemplate
                .convertAndSend(PERSON_EXCHANGE,
                        "",
                        PersonDto.builder()
                                .name(principal.getName())
                                .points(generatedPoints)
                                .build());
    }

    private int getPoints(){
        var random = new Random();
        return random.nextInt(1,100);
    }

}
