package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* Part of 1.2.3 */
@Configuration
public class ExternalConfig {
    @Bean
    MyBeanTwo myBeanTwo(){
        return new MyBeanTwo();
    }

}

