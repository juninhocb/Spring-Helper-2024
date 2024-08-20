package com.example.rabbitmq_client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        var secBuilder = httpSecurity
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(endpoint -> {
                    endpoint.anyRequest().authenticated();
                });

        return secBuilder.build();
    }

    @Bean
    UserDetailsService customUserDetailsService(){
        UserDetails u1 = User.withUsername("John").password("{noop}jpass").build();
        UserDetails u2 = User.withUsername("Anne").password("{noop}apass").build();
        UserDetails u3 = User.withUsername("Charles").password("{noop}cpass").build();

        return new InMemoryUserDetailsManager(List.of(u1,u2,u3));

    }


}
