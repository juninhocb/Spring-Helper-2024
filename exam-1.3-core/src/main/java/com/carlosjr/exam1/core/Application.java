package com.carlosjr.exam1.core;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		var ctx = SpringApplication.run(Application.class, args);
		/* 1.3.1 */
		MyBean myBean = (MyBean) ctx.getBean("myBean");
		myBean.printPerson();
		/* 1.3.1 - alternative */
		MyBeanAlternative myBeanAlternative = (MyBeanAlternative) ctx.getBean("myBeanAlternative");
		myBeanAlternative.printPerson();
		/* 1.3.1 - alternative two */
		MyBeanSecondAlternative myBeanSecondAlternative = (MyBeanSecondAlternative) ctx.getBean("myBeanSecondAlternative");
		myBeanSecondAlternative.printPerson();

		/* 1.3.2 */
		String[] profiles = ctx.getEnvironment().getActiveProfiles();
		for (String profile : profiles){
			if (profile.equals("cloud")){
				System.out.println("__This is a cloud environment");
				break;
			}
		}

		/* 1.3.3 SPeL is widely used in Caches, AOP and Spring Security (authorize) */
	}

}

/* 1.3.2 */
@Configuration
@Profile("profiled")
class MyProfiledConfiguration{

	@Bean
	ApplicationRunner applicationRunner(){
		return args -> {
			System.out.println("__Run as a profiled configuration!");
		};
	}

}

@Configuration
class MyConfiguration{

	/* 1.3.1 */
	@Value("${app.prop.name}")
	private String name;
	/* 1.3.1 */
	@Value("${app.prop.age}")
	private Integer age;
	/* 1.3.1 */
	@Value("${app.prop.is-any}")
	private Boolean isAny;
	/* 1.3.1 */
	@Value("${app.prop.roles}")
	private String[] roles;

	/* 1.3.3 */
	@Value("#{personProperties}")
	private PersonProperties fromSpEL;
	/* 1.3.3 */
	@Value("#{new Integer(2) * 3}")
	private Integer number;
	/* 1.3.3 */
	@Value(("#{environment['app.test']}"))
	private Integer spELFromProps;

	/* 1.3.1 */
	@Bean
	MyBean myBean(){
		return new MyBean(name, age, isAny, roles);
	}

	/* 1.3.1 - alternative */
	@Bean
	MyBeanAlternative myBeanAlternative(PersonProperties personProperties){
		return new MyBeanAlternative(personProperties);
	}

	/* 1.3.1 - alternative two */
	@Bean
	MyBeanSecondAlternative myBeanSecondAlternative(Environment environment){
		return new MyBeanSecondAlternative(environment);
	}

	/* 1.3.3 - printing from SpEL */
	@Bean
	ApplicationRunner applicationRunnerTwo(){
		return args ->  {
			System.out.println(STR."""
						FROM SpEL
						name \{fromSpEL.name}
						age \{fromSpEL.age}
						isAny \{fromSpEL.isAny}
						roles \{String.join("|", fromSpEL.roles)}
						number \{number}
						fromProps \{spELFromProps}
					""");;
		};
	}

}

/* 1.3.1 - alternative two */
class MyBeanSecondAlternative{
	final Person person;
	MyBeanSecondAlternative(Environment environment){
		String name = environment.getProperty("app.prop.name");
		Integer age = environment.getProperty("app.prop.age", Integer.class);
		Boolean isAny = environment.getProperty("app.prop.is-any", Boolean.class);
		String[] roles = environment.getProperty("app.prop.roles", String[].class);
		this.person = new Person(name, age, isAny, roles);
	}
	void printPerson(){
		System.out.println(STR."THIRD APPROACH { \r\n\{person}}");
	}
}

/* 1.3.1 - alternative */
class MyBeanAlternative{

	final Person person;
	MyBeanAlternative(PersonProperties personProperties){
		this.person = new Person(
				personProperties.name,
				personProperties.age,
				personProperties.isAny,
				personProperties.roles);
	}

	void printPerson(){
		System.out.println(STR."SECOND APPROACH { \r\n\{person}}");
	}
}

/* 1.3.1 */
@Configuration
@PropertySource("stub.whatever")
class MyExternalConf{
	@Value("${app.whatever}")
	String whatever;

	@PostConstruct
	void show(){
		System.out.println(whatever);
	}



}

/* 1.3.1 */
class MyBean{
	final Person person;
	MyBean(String name, Integer age, Boolean isAny, String[] roles){
		this.person = new Person(name, age, isAny, roles);
	}
	void printPerson(){
		System.out.println(STR."FIRST APPROACH { \r\n\{person}}");
	}
}

/* 1.3.1 */
record Person(String name, Integer age, Boolean isAny, String[] roles){
	@Override
	public String toString(){
		return STR."""
				name is \{name}
				age is \{age}
				isAny is \{isAny}
				roles are \{String.join(",", roles)}
				""";
	}
}
/* 1.3.1 - alternative */
@Component
@ConfigurationProperties(prefix = "app.prop")
@Setter
class PersonProperties{
	String name;
	Integer age;
	Boolean isAny;
	String[] roles;
}
