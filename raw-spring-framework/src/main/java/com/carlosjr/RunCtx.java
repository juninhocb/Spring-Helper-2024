package com.carlosjr;


import com.carlosjr.config.AppConfig;
import com.carlosjr.config.DatabaseConfig;
import com.carlosjr.config.PersistenceJPAConfig;
import com.carlosjr.config.WebConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class RunCtx {
    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class, DatabaseConfig.class, PersistenceJPAConfig.class);

        for ( String beanName : ctx.getBeanDefinitionNames()){
            System.out.println(beanName);
        }

    }
}