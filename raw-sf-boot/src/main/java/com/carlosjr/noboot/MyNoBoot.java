package com.carlosjr.noboot;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MyNoBoot {

	public static void main(String[] args) {

		TRunApp tRunApp = new TRunApp();
		Thread thread = new Thread(tRunApp);
		thread.start();

	}

}
@Slf4j
class TRunApp implements Runnable{
	public TRunApp(){}
	@Override
	public void run() {

		DatabaseService dsJdbc = new DatabaseServiceByJdbc();

		dsJdbc.getCustomersFromDb().forEach(customer -> {
			log.info("This customer has id {} and name {}", customer.id(), customer.name());
		});

		dsJdbc.insertCustomer(Customer.builder().name("joseph").build());

		dsJdbc.getCustomersFromDb().forEach(customer -> {
			log.info("This customer has id {} and name {}", customer.id(), customer.name());
		});

		DatabaseService dsRestTemplate = new DatabaseServiceByJdbcTemplate();

		dsRestTemplate.insertCustomer(Customer.builder().name("charles").build());

		dsRestTemplate.getCustomersFromDb().forEach(customer -> {
			log.info("This customer has id {} and name {}", customer.id(), customer.name());
		});



	}
}

@Getter
@Slf4j
class DatabaseConfig {

	private final DataSource dataSource;

	private final JdbcTemplate jdbcTemplate;

	public DatabaseConfig(){

		this.dataSource = DataSourceBuilder
				.create()
				.url("jdbc:mysql://localhost:3306/my_tests")
				.username("root")
				.password("root")
				.build();

		log.info("Datasource successfully set");

		this.jdbcTemplate = new JdbcTemplate();
		this.jdbcTemplate.setDataSource(this.dataSource);

	}



}

interface DatabaseService{
	String SQL_FIND_ALL = "SELECT * FROM `customer`";
	String SQL_CREATE = "INSERT INTO `customer` ( name ) values ( ? )";

	List<Customer> getCustomersFromDb();
	void insertCustomer(Customer customer);
}

@Slf4j
class DatabaseServiceByJdbcTemplate implements DatabaseService{

	private final DatabaseConfig configuration = new DatabaseConfig();
	@Override
	public List<Customer> getCustomersFromDb() {
		return configuration.getJdbcTemplate().query(SQL_FIND_ALL, new RowMapper<Customer>() {
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
		configuration.getJdbcTemplate().update(SQL_CREATE, customer.name());
	}
}

@Slf4j
class DatabaseServiceByJdbc implements DatabaseService{

	private final DatabaseConfig configuration = new DatabaseConfig();

	public DatabaseServiceByJdbc(){}


	public List<Customer> getCustomersFromDb(){
		List<Customer> customers = new ArrayList<>();
		try (Connection connection =  configuration.getDataSource().getConnection()){
			try (Statement stmt = connection.createStatement()){
				try (ResultSet rs = stmt.executeQuery(SQL_FIND_ALL)){
					while (rs.next()){
						Customer customer =  Customer.builder()
								.id(rs.getInt("id"))
								.name(rs.getString("name"))
								.build();
						customers.add(customer);
					}
				}
			}

		}catch (SQLException ex){
			log.error("Something went wrong {}", ex.getMessage());
		}
		return customers;

	}

	public void insertCustomer(Customer customer) {
		try (Connection connection = configuration.getDataSource().getConnection()) {
			try (PreparedStatement stmt = connection.prepareStatement(SQL_CREATE)) {
				stmt.setString(1, customer.name());
				stmt.executeUpdate();
			}
		} catch (SQLException ex) {
			log.error("Something went wrong {} ", ex.getMessage());
		}
	}

}

@Builder
record Customer(Integer id, String name){}





