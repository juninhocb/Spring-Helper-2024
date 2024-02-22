package com.carlosjr.messagemodulith.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "product.db")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ProductDataSourceConfig {
    private String url;
    private String username;
    private String password;
}
