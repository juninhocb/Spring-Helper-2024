package com.carlosjr.messagemodulith.messages;

import com.carlosjr.messagemodulith.customer.Customer;
import com.carlosjr.messagemodulith.product.ProductType;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

public class CustomMessageEvent extends ApplicationEvent {

    private final Map<Customer, ProductType> message;
    public CustomMessageEvent(Object source, Map<Customer, ProductType> messsage) {
        super(source);
        this.message = messsage;
    }
    public Map<Customer, ProductType> getMessage(){
        return this.message;
    }
}
