package com.carlosjr.config;

import com.carlosjr.EventStartedNotifier;
import com.carlosjr.TRunner;
import com.carlosjr.product.ProductService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@ComponentScan({ "com.carlosjr.product" })
@Import({ DatabaseConfig.class, PersistenceJPAConfig.class, WebConfig.class })
public class AppConfig {
    private final ProductService productService;
    public AppConfig(ProductService productService, ApplicationContext applicatixonContext){
        this.productService = productService;
    }

    @PostConstruct
    void afterBeanInjection(){
        TRunner runner = new TRunner(productService);
        Thread threadServiceTest = new Thread(runner);
        threadServiceTest.run();
    }

    @Bean
    public EventStartedNotifier notifier(){
        return new EventStartedNotifier();
    }






}
