package com.carlosjr.config;

import com.carlosjr.EventStartedNotifier;
import com.carlosjr.TRunner;
import com.carlosjr.product.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.carlosjr.product")
public class AppConfig {

    @Bean
    public EventStartedNotifier notifier(ProductService productService){
        TRunner runner = new TRunner(productService);
        Thread threadServiceTest = new Thread(runner);
        threadServiceTest.run();
        return new EventStartedNotifier();
    }




}
