package com.carlosjr;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class RunCtx {
    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class, DatabaseConfig.class);

        for ( String beanName : ctx.getBeanDefinitionNames()){
            System.out.println(beanName);
        }

    }
}