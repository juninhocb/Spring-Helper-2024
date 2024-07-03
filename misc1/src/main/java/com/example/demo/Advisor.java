package com.example.demo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
class Advisor {

    @Around(value = "execution(* com.example.demo.MyService.getHello(..))")
    Object beforeRun(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Until proceed!");
        Object response = pjp.proceed();
        System.out.println("After proceed!");
        return response;
    }


}
