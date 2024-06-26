package com.carlosjr.teste;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/stub")
class AppController {

    final AtomicInteger appValue = new AtomicInteger(0);
    final AppService appService;
    public AppController(AppService appService){
        this.appService = appService;
    }

    @GetMapping
    ResponseEntity<String> getResponse(){
        int random = new Random().nextInt(7, 12);
        return ResponseEntity.ok().body(appService.processString(random));
    }

    @PostMapping
    ResponseEntity<Integer> postResponse(@RequestBody Integer value){
        System.out.println("Thread is " + Thread.currentThread().getName() + " timestamp = " + System.currentTimeMillis());
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            //ignore
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(getUpdatedValue(value));
    }

    @ExceptionHandler(LazyException.class)
    ResponseEntity<String> responseErrHandler(LazyException le){
        return ResponseEntity.status(999).body(le.getMessage());
    }

    private Integer getUpdatedValue(Integer value){
        Integer returnValue = value;
        synchronized (Void.class) {
            System.out.println("Current thread is " + Thread.currentThread() + " of value " + value);
            appValue.incrementAndGet();

        }
        return appValue.get() + returnValue;
    }

}
