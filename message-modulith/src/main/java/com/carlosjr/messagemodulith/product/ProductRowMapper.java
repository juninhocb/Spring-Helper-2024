package com.carlosjr.messagemodulith.product;


import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        char type = 'z';
        switch ( rs.getInt("product_type")){
            case 0:
                type = 'a';
                break;
            case 1:
                type = 'b';
                break;
            case 2:
                type = 'c';
                break;
        }
        return Product
                .builder()
                .id(UUID.fromString(rs.getString("id")))
                .customerId(UUID.fromString(rs.getString("id")))
                .productType(ProductType.getTypeFromChar(type))
                .build();
    }
}
