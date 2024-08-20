package com.example.rabbitmq_server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PersonServiceTest {
    @Autowired
    private PersonService personService;

    @Test
    @DirtiesContext
    void testServiceFlow(){

        PersonDto person = getPerson(10);

        var updated = personService.updatePoints(person);

        assertThat(updated).isNotNull();
        assertThat(updated.points()).isEqualTo(10);

        System.out.println(updated);

        var newPerson = getPerson(20);

        var updatedAgain = personService.updatePoints(newPerson);

        assertThat(updatedAgain).isNotNull();
        assertThat(updatedAgain.points()).isEqualTo(20);

        System.out.println(updatedAgain);
    }

    private PersonDto getPerson(int points){
        return PersonDto.builder()
                .name("John")
                .points(points)
                .build();
    }
}