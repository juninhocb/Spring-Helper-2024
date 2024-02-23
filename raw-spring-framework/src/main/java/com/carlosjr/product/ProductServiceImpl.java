package com.carlosjr.product;


import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
@Service

public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    void afterInject(){
        Product product = new Product();
        product.setName("saved product!");
        Product storedProduct = productRepository.save(product);
        displayProduct(productRepository.findById(storedProduct.getId()).get());
    }


    public void displayProduct(Product product){
        System.out.println("Product ::: " + product);

    }



}
