package com.carlosjr.messagemodulith.messages;

import com.carlosjr.messagemodulith.customer.Customer;
import com.carlosjr.messagemodulith.product.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomMessagePublisherImpl implements  CustomMessagePublisher{
    private final ApplicationEventPublisher eventPublisher;
    @Override
    public void publishMessage(Map<Customer, ProductType> message) {
        eventPublisher.publishEvent(new CustomMessageEvent(this, message));
    }
}
