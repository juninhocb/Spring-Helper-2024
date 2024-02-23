package com.carlosjr;

import com.carlosjr.product.Product;
import com.carlosjr.product.ProductService;

public class TRunner implements Runnable{

    private final ProductService productService;

    public TRunner(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run() {
        Product stubProduct = new Product(1, "Stub Product");
        productService.displayProduct(stubProduct);
    }
}
