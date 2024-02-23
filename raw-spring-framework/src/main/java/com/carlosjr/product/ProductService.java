package com.carlosjr.product;


import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public void displayProduct(Product product){

        System.out.println("Product ::: " + product);

    }



}
