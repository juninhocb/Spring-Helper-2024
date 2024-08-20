package com.example.rabbitmq_client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
class PersonController {
    private final PersonService personService;
    @PutMapping("/points")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void updatePoints(Principal principal){
        personService.updatePoints(principal);
    }
}
