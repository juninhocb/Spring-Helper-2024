package com.carlosjr.messagemodulith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.carlosjr.customer", "com.carlosjr.product", "com.carlosjr.messagemodulith"})
public class MessageModulithApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageModulithApplication.class, args);
	}

}
