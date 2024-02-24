package com.carlosjr;


import com.carlosjr.config.AppConfig;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;


public class RunCtx {
    public static void main(String[] args) {

        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();

        for ( String beanName : ctx.getBeanDefinitionNames()){
            System.out.println(beanName);
        }
    }
}