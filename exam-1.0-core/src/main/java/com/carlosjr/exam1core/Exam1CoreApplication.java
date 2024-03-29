package com.carlosjr.exam1core;

import com.config.ExternalConfig;
import com.config.MyBeanTwo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;


//vmw-spring-professional-develop-exam-guide.pdf
@SpringBootApplication
/* 1.2.3 */
@Import(ExternalConfig.class)
public class Exam1CoreApplication {

	/*  1.1 - Dependency Injection | Portable Service Abstractions | AOP */
	public static void main(String[] args) {

		var ctx = SpringApplication.run(Exam1CoreApplication.class, args);

		/* 1.2.2 */
		var beanFromCtx = (MyBean) ctx.getBean("customBeanName");
		beanFromCtx.doSomething();

		/* 1.2.3 */
		// Get by name
		var beanFromExternalConfig = (MyBeanTwo) ctx.getBean("myBeanTwo");
		beanFromExternalConfig.doExternalThing();
		// Get by class
		var beanFromExternalConfigClass = (MyBeanTwo) ctx.getBean(MyBeanTwo.class);
		beanFromExternalConfigClass.doExternalThing();

		/* 1.2.4 */
		var dependentBean = (MyDependentBean) ctx.getBean(MyDependentBean.class);
		dependentBean.doSomething();

		/* 1.2.5 */
		// Call the bean creation...
		ctx.getBean(PrototypeScopedBean.class);
		
	}

}

@Configuration
class JavaConfiguration{

	/* 1.2.1 */
	@Bean(name ="customBeanName", autowireCandidate = true, initMethod = "myInitMethod", destroyMethod = AbstractBeanDefinition.INFER_METHOD)
	MyBean myBean(){
		return new MyBean();
	}

	/* 1.2.4 */
	@Bean
	MyIndependentBean myIndependentBean(){
		return new MyIndependentBean();
	}

	/* 1.2.4 */
	@Bean
	@Primary
	MyOtherIndependentBean myOtherIndependentBean(){
		return new MyOtherIndependentBean();
	}

	/* 1.2.4 */
	@Bean
	MyDependentBean myDependentBean(@Qualifier("myIndependentBean") MyInterface myInterface){
		return new MyDependentBean(myInterface);
	}

	/* 1.2.5 */
	@Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
	SingletonScopedBean mySingletonScopedBean(){
		return new SingletonScopedBean();
	}

	/* 1.2.5 */
	// Only runs when called!
	@Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	PrototypeScopedBean myPrototypeScopedBean(){
		return new PrototypeScopedBean();
	}

}

/* 1.2.5 */
class PrototypeScopedBean{
	void initMethod(){
		System.out.println("Initializing prototype scoped bean");
	}

	void destroyMethod(){
		System.out.println("Destroy prototype scoped bean");
	}
}

/* 1.2.5 */
class SingletonScopedBean{
	void initMethod(){
		System.out.println("Initializing singleton scoped bean");
	}

	void destroyMethod(){
		System.out.println("Destroy singleton scoped bean");
	}
}

/* 1.2.4 */
interface MyInterface {
	String getMessage();
}

/* 1.2.4 */
class MyOtherIndependentBean implements MyInterface{

	@Override
	public String getMessage() {
		return " __another implementation!";
	}
}

/* 1.2.4 */
class MyIndependentBean implements MyInterface{
	final String message = " __dependent resource";

	@Override
	public String getMessage(){
		return message;
	}
}
class MyDependentBean {
	final MyInterface myInterface;
	MyDependentBean(MyInterface myInterface){
		this.myInterface = myInterface;
	}

	void doSomething(){
		System.out.println("this bean with the other bean feature " + myInterface.getMessage());
	}

}

/* 1.2.1 */
class MyBean{

	void myInitMethod(){
		System.out.println("Initialization bean");
	}
	void doSomething(){
		System.out.println("Hello Bean!");
	}
}
