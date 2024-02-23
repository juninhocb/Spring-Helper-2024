package com.carlosjr.messagemodulith.product;

import com.carlosjr.messagemodulith.messages.CustomMessageEvent;
import com.carlosjr.messagemodulith.messages.CustomMessageListener;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class ProductServiceImpl implements CustomMessageListener, ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

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
        productRepository.save(product);
    }
    @Override
    public List<Product> selledProducts() {
        return productRepository.findAll();
    }
}
