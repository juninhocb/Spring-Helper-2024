package com.carlosjr.sf;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;


@Slf4j
public class MySpringFramework {

	public static void main(String[] args) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(DatabaseConfig.class);
		for (String beanName : ctx.getBeanDefinitionNames() ){
			log.info("Bean on context with name {}", beanName);
		}

		TRunApp runApp = (TRunApp) ctx.getBean("runnerApp");
		runApp.run();

	}

}
@Component("runnerApp")
@Slf4j
@RequiredArgsConstructor
class TRunApp implements Runnable{
	private final DatabaseService databaseService;
	@Override
	public void run() {

		databaseService.insertCustomer(Customer.builder().name("charles").build());

		databaseService.getCustomersFromDb().forEach(customer -> {
			log.info("This customer has id {} and name {}", customer.id(), customer.name());
		});

	}
}

@Configuration
@ComponentScan
class DatabaseConfig {
	@Bean
	DataSource dataSource(){
		return DataSourceBuilder
				.create()
				.url("jdbc:mysql://localhost:3306/my_tests")
				.username("root")
				.password("root")
				.build();
	}

	@Bean
	JdbcTemplate jdbcTemplate(DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}
}

interface DatabaseService{
	String SQL_FIND_ALL = "SELECT * FROM `customer`";
	String SQL_CREATE = "INSERT INTO `customer` ( name ) values ( ? )";
	List<Customer> getCustomersFromDb();
	void insertCustomer(Customer customer);
}

@Slf4j
@RequiredArgsConstructor
@Service
class DatabaseServiceByJdbcTemplate implements DatabaseService{

	private final JdbcTemplate jdbcTemplate;
	@Override
	public List<Customer> getCustomersFromDb() {
		return jdbcTemplate.query(SQL_FIND_ALL, new RowMapper<Customer>() {
			@Override
			public Customer mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {

				return  Customer.builder()
							.id(rs.getInt("id"))
							.name(rs.getString("name"))
							.build();

			}
		});
	}
	@Override
	public void insertCustomer(Customer customer) {
		jdbcTemplate.update(SQL_CREATE, customer.name());
	}
}

@Builder
record Customer(Integer id, String name){}


