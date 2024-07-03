package com.example.demo;

import external.ExternalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stub")
class MyController {
    private final MyService myService;
    private final ExternalService externalService;
    MyController(MyService myService, ExternalService externalService){
        this.myService = myService;
        this.externalService = externalService;
    }
    @GetMapping("/1")
    ResponseEntity<String> getHello1(){
        System.out.println("thread = " + Thread.currentThread().getName());
        return ResponseEntity.ok().body(myService.getHello() + externalService.doMagic());
    }
    @GetMapping("/2")
    String getHello2(){
       return myService.getHello();
    }

}
