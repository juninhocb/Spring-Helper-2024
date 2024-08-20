package com.example.rabbitmq_server;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface PersonMapper {
    PersonDto entityToDto(Person person);
    Person dtoToEntity(PersonDto personDto);

}
