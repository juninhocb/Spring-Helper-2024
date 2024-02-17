package com.carlosjr.jdbctenant;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("User {} with authorities {}", authentication.getName(), authentication.getAuthorities());

        String sql = "SELECT  id, name  FROM customer; ";
        List<Customer> customers = jdbcTemplate.query(sql, new RowMapper<Customer>() {
            @Override
            public Customer mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
                return Customer.builder()
                        .id(rs.getInt("id"))
                        .name( rs.getString("name"))
                        .build();
            }
        });

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(customers);
    }


}
