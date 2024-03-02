package com.carlosjr.sfsocket;


import com.carlosjr.sfsocket.config.AppConfig;
import com.carlosjr.sfsocket.customer.CustomerSocket;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class SfSocketApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		var customerSocket = (CustomerSocket) ctx.getBean("customerSocket");

		customerSocket.startServer();

	}

}
