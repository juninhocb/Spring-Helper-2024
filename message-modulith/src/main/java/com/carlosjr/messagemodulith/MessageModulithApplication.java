package com.carlosjr.messagemodulith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.carlosjr.customer", "com.carlosjr.product", "com.carlosjr.messagemodulith"})
public class MessageModulithApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(MessageModulithApplication.class, args);
		for ( String beanName : ctx.getBeanDefinitionNames()){

			//System.out.println(beanName);

		}
	}

}
