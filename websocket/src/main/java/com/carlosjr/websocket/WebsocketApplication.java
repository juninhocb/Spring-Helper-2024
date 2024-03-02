package com.carlosjr.websocket;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@SpringBootApplication
public class WebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketApplication.class, args);
	}

}

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
class WebSocketConfig implements WebSocketConfigurer{

	private final MyWebSocketHandler webSocketHandler;

	@Override
	public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {

		registry.addHandler(webSocketHandler,  "/customer")
				.setAllowedOrigins("*")
				.setAllowedOriginPatterns("*");
	}
}
@Slf4j
@Component
@RequiredArgsConstructor
class MyWebSocketHandler extends TextWebSocketHandler{
	private final CustomerRepository customerRepository;
	@Override
	protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws Exception {
		var payload = message.getPayload();
		int contractCode = Integer.parseInt(payload.trim().split("C")[0]);
		var args = payload.length() > 2 ? payload.trim().split("C")[1] : null;
		var responseBuilder = new StringBuilder();
		switch (contractCode){
			case 0 -> responseBuilder.append( customerRepository.getAll());
			case 1 -> {
                assert args != null;
                responseBuilder.append(customerRepository.getById(Integer.parseInt(args)));
			}
			case 2 -> {
				assert args != null;
				var id = Integer.parseInt(args.trim().split(":")[0]);
				var name = args.trim().split(":")[1];
				boolean isCreated = customerRepository.create(Customer.builder().id(id).name(name).build());
				if (isCreated){
					responseBuilder.append("Newly customer created");
				} else{
					responseBuilder.append("Already created");
				}
			}
			case 3 -> {
				assert args != null;
				customerRepository.deleteById(Integer.parseInt(args));
				responseBuilder.append("Deleted successfully");
			}

		}
		var data = new StringBuilder();
		customerRepository.getAll().forEach(c -> data.append(c).append("\n"));
		log.info("Current repository contains \n{}", data);
		session.sendMessage(new TextMessage(responseBuilder));
	}
}


@Slf4j
@Configuration
class AppConfig{

	@Bean
	CommandLineRunner clr(CustomerRepository repository){
		return args -> {
			log.info("Setting repository started");
			var c1 =  Customer.builder().id(1).name("elton").build();
			var c2 =  Customer.builder().id(2).name("philip").build();
			var c3 =  Customer.builder().id(3).name("john").build();
			var c4 =  Customer.builder().id(4).name("mark").build();
			repository.addAll(Set.of(c1,c2,c3,c4));
			var data = new StringBuilder();
			repository.getAll().forEach(c -> data.append(c).append("\n"));
			log.info("Repository set successfully with data \n{}", data);
		};
	}

}


@Repository
class CustomerRepository{

	private final Set<Customer> customers = new ConcurrentSkipListSet<>();

	Set<Customer> getAll(){
		return customers;
	}

	Customer getById(Integer id){
		return customers
				.stream()
				.filter((c) -> Objects.equals(c.id(), id))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("not found for this id " + id));
	}

	boolean create(Customer customer){
		int untilSize = customers.size();
		customers.add(customer);
		int afterSize = customers.size();
		return !(untilSize == afterSize);
	}

	void deleteById(Integer id){
		customers.removeIf((customer)-> Objects.equals(customer.id(), id));
	}

	void addAll(Set<Customer> customersAll){
		customers.addAll(customersAll);
	}
}

@Builder
record Customer (Integer id, String name) implements Comparable<Customer>{
	@Override
	public int compareTo(Customer o) {
		return this.id.compareTo(o.id);
	}
}