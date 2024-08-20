package com.example.rabbitmq_client;

import lombok.Builder;

@Builder
record PersonDto(
        String name,
        Integer points) {
}
