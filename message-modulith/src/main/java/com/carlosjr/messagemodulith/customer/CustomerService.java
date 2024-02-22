package com.carlosjr.messagemodulith.customer;

import com.carlosjr.messagemodulith.product.ProductType;

public interface CustomerService {
    void orderABuy(Customer customer, ProductType productType);
}
