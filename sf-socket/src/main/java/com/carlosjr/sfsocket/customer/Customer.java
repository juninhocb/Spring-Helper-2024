package com.carlosjr.sfsocket.customer;

import lombok.Builder;

@Builder
public record Customer(Integer id, String name, Integer age) {
}
