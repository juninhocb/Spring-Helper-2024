package com.example.demo;

import external.ExternalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

//@Configuration
class AppConfig {

    @Bean
    //@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    MyService myService(){
        return new MyService();
    }
    @Bean
    ExternalService externalService(){
        return new ExternalService();
    }

}
