package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
class MyService {
    private static final Logger logger = LoggerFactory.getLogger(MyService.class);
    private int counter = 0;
    MyService(){}
    String getHello(){
        logger.trace("Entering on MyService#getHello method ");
        logger.info("Current system mills is =  {}", System.currentTimeMillis());
        try {
            Thread.sleep(new Random().nextInt(2, 6) * 1000L);
        } catch (InterruptedException e){
            //ignore
        }
        return calculateStr();
    }

    private String calculateStr(){
        synchronized (this){
            this.counter++;
            return "Hello, you're the " + counter + " visitor!";
        }
    }

}
