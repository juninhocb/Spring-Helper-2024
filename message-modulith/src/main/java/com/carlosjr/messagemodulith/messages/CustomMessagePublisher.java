package com.carlosjr.messagemodulith.messages;

import com.carlosjr.messagemodulith.customer.Customer;
import com.carlosjr.messagemodulith.product.ProductType;

import java.util.Map;

public interface CustomMessagePublisher {
    void publishMessage(Map<Customer, ProductType> order);
}
