package com.challenge.volcano.island.mappers;

import com.challenge.volcano.island.controllers.request.PersonalInformation;
import com.challenge.volcano.island.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class );

    @Mappings({
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "identificationType", target = "identificationType"),
            @Mapping(source = "identificationNum", target = "identificationNum"),
            @Mapping(source = "email", target = "email")


    })
    Customer personalInformationToCustomer(PersonalInformation personalInformation);
}

