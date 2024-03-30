package com.external;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component("externalPackage")
public class ExternalBean implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello external!!!!");
    }
}
