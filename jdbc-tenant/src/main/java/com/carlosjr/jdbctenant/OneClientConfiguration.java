package com.carlosjr.jdbctenant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "ocli.db")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class OneClientConfiguration {
    private String url;
    private String username;
    private String password;

}
