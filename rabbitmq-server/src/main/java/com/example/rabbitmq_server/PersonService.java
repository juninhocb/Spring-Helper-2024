package com.example.rabbitmq_server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    PersonDto updatePoints(PersonDto person){

        var persistedPerson = findByUserName(person.name());

        if (persistedPerson == null){
            persistedPerson = createNewPerson(personMapper.dtoToEntity(person));
        }

        persistedPerson.setPoints(person.points());

        var updatedPerson = personRepository.save(persistedPerson);

        return personMapper.entityToDto(updatedPerson);
    }

    private Person createNewPerson(Person person){
        log.info("Creating new person {}", person.getName());
        return personRepository.save(person);
    }

    private Person findByUserName(String name){
        return personRepository.findPersonByName(name).orElse(null);
    }



}
