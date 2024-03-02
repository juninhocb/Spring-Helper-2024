package com.carlosjr.sfsocket.customer;

import lombok.NonNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return Customer.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .age(rs.getInt("age"))
                .build();
    }
}
