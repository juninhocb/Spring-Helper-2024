package com.carlosjr.webconcepts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@SpringBootApplication
public class WebConceptsApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(WebConceptsApplication.class, args);
		System.out.println(ctx.getParent());

		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(AppConfig.class);
		System.out.println(context.getParent());
		context.refresh();
		for ( String beanName : context.getBeanDefinitionNames()){
			System.out.println(beanName);
		}

		MyBean myBean = (MyBean)  ctx.getBean("myBean");
		MyBean myBean2 = (MyBean)  context.getBean("myBean");

		myBean.methodInvoker();
		myBean2.methodInvoker();


	}

}
