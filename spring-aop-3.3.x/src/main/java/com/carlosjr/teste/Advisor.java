package com.carlosjr.teste;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
class Advisor {

    /**
     * 1. O que vai definir se o AOP será "trigado" é se o nome da variável do parâmetro
     * é igual ao utilizado no SPEL. A anotação TIMED é chamada através da identificação
     * do parâmetro que é utilizado no Método, onde o SPRING cria o AOP em runtime.
     * 2. O método é chamado independente se retorna "Object" ou não. Porém, o void
     * faz com que Dispatcher entenda que não há nada no body da response.
     * 3. O encapsulamento default não interfere em nada, pois todas as classes
     * estão no mesmo pacote.
     */
    @Around("@annotation(whatever)")
    Object aroundJob(ProceedingJoinPoint pjp, Timed whatever) throws Throwable {
        long initTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        pjp.proceed();
        long endTime = System.currentTimeMillis() - initTime;
        System.out.println("Execution timestamp = " + endTime);
        return result; // Correto
        //return "whatever"; // Irá retornar whater em response
        //return null; // o dispatcher entende como Void
    }

    /*@Before("execution(* com.carlosjr.teste.AppService.processString(..))")
    void beforeJob(){
        System.out.println("Before");
    }

    @After("execution(* com.carlosjr.teste.AppService.processString(..))")
    void afterJob(JoinPoint jp){
        Integer random = Integer.parseInt(jp.getArgs()[0].toString());
        System.out.println("Executing" + random);
    }

    @AfterReturning("execution(* com.carlosjr.teste.AppService.processString(..))")
    void adviceNotThrows(){
        System.out.println("Everything was well executed");
    }*/

    @AfterThrowing("execution(* com.carlosjr.teste.AppService.processString(..))")
    void adviceThrows(){
        System.out.println("Exception was raised");
    }

}
