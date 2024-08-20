package com.example.rabbitmq_server;

import lombok.Builder;

@Builder
record PersonDto(String name, Integer points)
{
}
