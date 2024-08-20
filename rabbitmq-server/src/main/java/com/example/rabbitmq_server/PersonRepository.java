package com.example.rabbitmq_server;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findPersonByName(String name);

}
