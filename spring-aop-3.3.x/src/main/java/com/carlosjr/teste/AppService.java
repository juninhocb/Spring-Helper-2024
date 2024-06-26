package com.carlosjr.teste;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
class AppService {

    @Timed
    String processString(Integer random){
        System.out.println(random);
        final int sleepTime = new Random().nextInt(0, 5);
        int fullTime = sleepTime*1000;
        if (sleepTime == 4){
            throw new LazyException("Too lazy to continue");
        }
        try {
            Thread.sleep(fullTime);
        }catch (InterruptedException e){

        }
        return "Hello world!";
    }


}
