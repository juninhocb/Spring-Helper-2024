package com.carlosjr.sfsocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.carlosjr.sfsocket.customer")
public class AppConfig {

    static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    DataSource dataSource(){
        return DataSourceBuilder
                .create()
                .url("jdbc:mysql://localhost:3306/sf_socket_customer")
                .username("root")
                .password("root")
                .build();
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource){
        var jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    ApplicationListener<ContextRefreshedEvent> bootstrapListener(){

        return event -> logger.info("Spring initialize successfully {}", event.getTimestamp());

    }

}
