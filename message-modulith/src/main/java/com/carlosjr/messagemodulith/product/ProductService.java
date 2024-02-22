package com.carlosjr.messagemodulith.product;

import java.util.List;

public interface ProductService {
    void storeBuy(Product product);
    List<Product> selledProducts();

}
