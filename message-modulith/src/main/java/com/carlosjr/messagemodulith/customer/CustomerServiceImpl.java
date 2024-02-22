package com.carlosjr.messagemodulith.customer;

import com.carlosjr.messagemodulith.messages.CustomMessagePublisher;
import com.carlosjr.messagemodulith.product.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    @Qualifier("customerDataSource")
    private final CustomerRepository customerRepository;
    private final CustomMessagePublisher messagePublisher;
    @Override
    public void orderABuy(Customer customer, ProductType productType) {
        UUID id = storeCustomer(customer);
        customer.setId(id);
        Map<Customer, ProductType> mapMessage = Map.of(customer, productType);
        messagePublisher.publishMessage(mapMessage);

    }
    private UUID storeCustomer(Customer customer){
        Optional<Customer> customerOptional = customerRepository.findByName(customer.getName());
        if (customerOptional.isPresent()){
            return customerOptional.get().getId();
        }
        return customerRepository.save(customer).getId();
    }
}
