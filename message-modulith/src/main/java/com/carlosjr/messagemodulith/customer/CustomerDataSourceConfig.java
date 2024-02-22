package com.carlosjr.messagemodulith.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "customer.db" )
@Component
public class CustomerDataSourceConfig {
    private String url;
    private String username;
    private String password;
}
