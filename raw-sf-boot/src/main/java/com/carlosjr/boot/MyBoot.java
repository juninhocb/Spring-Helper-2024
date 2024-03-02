package com.carlosjr.boot;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@SpringBootApplication
public class MyBoot {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(MyBoot.class);
		for (String beanName : ctx.getBeanDefinitionNames() ){
			log.info("Bean on context with name {}", beanName);
		}
	}
}
@Component
@RequiredArgsConstructor
@Slf4j
class TRunApp implements CommandLineRunner {
	private final DatabaseService databaseService;
	@Override
	public void run(String... args) throws Exception {
		databaseService.insertCustomer(Customer.builder().name("charles").build());

		databaseService.getCustomersFromDb().forEach(customer -> {
			log.info("This customer has id {} and name {}", customer.getId(), customer.getName());
		});
	}
}

interface DatabaseRepository extends JpaRepository<Customer, Integer>{ }

@RequiredArgsConstructor
@Service
class DatabaseService {

	private final DatabaseRepository databaseRepository;
	public List<Customer> getCustomersFromDb() {
		return databaseRepository.findAll();
	}
	public void insertCustomer(Customer customer) {
		databaseRepository.save(customer);
	}
}

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
class Customer{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String name;
}
