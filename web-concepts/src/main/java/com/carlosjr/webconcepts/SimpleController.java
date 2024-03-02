package com.carlosjr.webconcepts;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping
    public ResponseEntity<String> getHello(){
        return ResponseEntity
                .status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body("Hello World in Json");
    }

}
