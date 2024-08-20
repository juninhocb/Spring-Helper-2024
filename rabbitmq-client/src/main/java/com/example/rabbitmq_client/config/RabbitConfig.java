package com.example.rabbitmq_client.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RabbitConfig {
    @Bean
    RabbitTemplate customRabbitTemplate(
            @Qualifier("customMessageConverter") MessageConverter messageConverter,
            ConnectionFactory connectionFactory){
        var rt =  new RabbitTemplate(connectionFactory);
        rt.setMessageConverter(messageConverter);
        return rt;
    }
    @Bean
    MessageConverter customMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
