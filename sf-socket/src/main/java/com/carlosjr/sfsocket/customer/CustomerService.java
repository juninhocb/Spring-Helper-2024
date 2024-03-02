package com.carlosjr.sfsocket.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    List<Customer> getAll(){return customerRepository.getAll();}
    Customer getById(Integer id){
        var customerOptional = customerRepository
                .getById(id);
        if (customerOptional.isPresent()){
            return customerOptional.get();
        }
        throw new IllegalStateException("Not found");
    }
    Customer create(Customer customer){return customerRepository.create(customer);}
    void deleteById(Integer id){customerRepository.deleteById(id);}

}
