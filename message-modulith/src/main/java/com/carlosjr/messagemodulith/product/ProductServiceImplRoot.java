package com.carlosjr.messagemodulith.product;

import com.carlosjr.messagemodulith.messages.CustomMessageEvent;
import com.carlosjr.messagemodulith.messages.CustomMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImplRoot implements CustomMessageListener, ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImplRoot.class);
    private final JdbcTemplate jdbcTemplate;
    private final ProductRowMapper productRowMapper;

    // TODO: refactor to use EM setting properly ds at Bean configuration With Modulith
    public ProductServiceImplRoot(@Qualifier("productDataSource") DataSource dataSource, ProductRowMapper productRowMapper){
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        this.productRowMapper = productRowMapper;
    }

    @EventListener
    @Override
    public void onMessage(CustomMessageEvent event) {
        event.getMessage().forEach((customer, type) -> {
            logger.info("Received message ::: {} ::: {} ", customer, type );
            Product product = Product.builder()
                    .productType(type)
                    .customerId(customer.getId())
                    .build();
            storeBuy(product);
        });


    }

    @Override
    public void storeBuy(Product product) {
        String sql = "INSERT INTO `product` ( id, customer_id, product_type ) VALUES ( ?, ?, ? )";
        int productTypeValue = product.getProductType().ordinal();
        jdbcTemplate.update(sql, new Object[] {UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8),
                product.getCustomerId().toString().getBytes(StandardCharsets.UTF_8) ,productTypeValue});
    }
    @Override
    public List<Product> selledProducts() {
        String sql = "SELECT * FROM  `product`";
        return jdbcTemplate.query(sql, productRowMapper);
    }
}
