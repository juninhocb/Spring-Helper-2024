package com.carlosjr.exam1.core;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.lang.StringTemplate.STR;


/* 1.4.1 All stuff of this code */
/* Won't work because is not auto-configuration class
@SpringBootApplication(exclude = BoomBean.class) */
@SpringBootApplication
@ComponentScan(basePackages = {"com.external", "com.carlosjr.exam1.core"})
public class Application {

	public static void main(String[] args) {


		var ctx = SpringApplication.run(Application.class, args);

		MyService myService = ctx.getBean(MyService.class);
		myService.executeMyInterface();

		MyServiceOne myServiceOne = ctx.getBean(MyServiceOne.class);
		myServiceOne.doWhatever();

		MyServiceTwo myServiceTwo = ctx.getBean(MyServiceTwo.class);
		myServiceTwo.doWhatever();

		MyServiceThree myServiceThree = ctx.getBean(MyServiceThree.class);
		myServiceThree.doWhatever();

		MyServiceUseMyRepository myServiceUseMyRepository = ctx.getBean(MyServiceUseMyRepository.class);
		myServiceUseMyRepository.doMyServiceTask();
	}

}
/* 1.4.4 stereotype annotations */
@Service
class MyServiceUseMyRepository{
	final MyRepository myRepository;

	// Preferred injection! 1.4.2
    MyServiceUseMyRepository(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

	void doMyServiceTask(){
		myRepository.printItems();
	}
}
/* 1.4.4 stereotype annotations */
@Repository
class MyRepository{

	ConcurrentMap<Integer, String> databaseItems = new ConcurrentHashMap<>();

	/* 1.4.3 */
	@PostConstruct
	void fillDatabase(){
		databaseItems.put(1, "Joseph");
		databaseItems.put(2, "John");
	}

	/* 1.4.3 */
	@PreDestroy
	void cleanDatabase(){
		System.out.println("Clear database!");
		databaseItems.clear();
	}

	void printItems(){
		databaseItems.forEach(( key, value ) -> System.out.println(STR."Key \{key} value \{value}"));
	}

}
/* 1.4.4 stereotype annotations */
@Component
@Lazy(value = true)
class BoomBean{
	static {
		System.out.println("I will fail!!!");
	}
	/* 1.4.3 */
	@PostConstruct
	void doBoom(){
		throw new RuntimeException("BOOOOOOOOOOOOOOOOOOOM");
	}

}
/* 1.4.4 stereotype annotations */
@Service
class MyServiceThree {
	/* Don't do this. Don't kill a unit test. */
	@Autowired
	MyServiceOne myServiceOne;

	void doWhatever(){
		myServiceOne.doWhatever();
		System.out.println("Doing whatever service Three");
	}

}
/* 1.4.4 stereotype annotations */
@Service
class MyServiceOne{

	/* Method injection are mutable */
	MyServiceTwo myServiceTwo;

	void doWhatever(){
		myServiceTwo.doWhatever();
		System.out.println("Doing whatever service One");
	}
	@Autowired
	void setMyServiceTwo(MyServiceTwo myServiceTwo){
		this.myServiceTwo = myServiceTwo;
	}

}
/* 1.4.4 stereotype annotations */
@Service
class MyServiceTwo{

	MyServiceOne myServiceOne;
	void doWhatever(){
		System.out.println("Doing whatever service Two");
	}

	/* Evokes circular bean problem, because one depends on two and two depends on one. */
	@Autowired
	void setMyServiceOne(MyServiceOne myServiceOne){
		this.myServiceOne = myServiceOne;
	}

}
/* 1.4.4 stereotype annotations */
@Service
class MyService{

	final MyInterface myInterface;
	/* Constructor conflict inject  */
	/* Only annotate with @Autowired in conflicts! */
	@Autowired
	MyService(@Qualifier("myBean") MyInterface myInterface, ResourceLoader resourceLoader){
		this.myInterface = myInterface;
	}
    MyService(MyInterface myInterface) {
        this.myInterface = myInterface;
    }
	void executeMyInterface(){
		myInterface.doSomething();
	}
}

interface MyInterface{
	void doSomething();
}

/* 1.4.4 stereotype annotations */
@Component
class MyBean implements MyInterface{
	@Override
	public void doSomething() {
		System.out.println("Doing something, bean 1");
	}
}
/* 1.4.4 stereotype annotations */
@Component
class MyBeanTwo implements MyInterface{
	@Override
	public void doSomething() {
		System.out.println("Doing something, bean 2");
	}
}
