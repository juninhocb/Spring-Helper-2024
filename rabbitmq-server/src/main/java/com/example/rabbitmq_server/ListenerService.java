package com.example.rabbitmq_server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@ListenerComponent
@RequiredArgsConstructor
class ListenerService implements PersonListener {
    private final PersonService personService;
    @Override
    @RabbitListener(queues = PERSON_QUEUE)
    public void listenPerson(PersonDto person){
        personService.updatePoints(person);
    }
    @RabbitListener(queues = TEST_QUEUE)
    public void testQueue(MyObject myObject){
        System.out.println("String received = " + myObject);
    }


}
@AllArgsConstructor
@NoArgsConstructor
@Data
class MyObject{
    private Integer id;
    private String name;
    private Boolean isWhatever;
}
