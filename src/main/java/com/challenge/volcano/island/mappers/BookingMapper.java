package com.challenge.volcano.island.mappers;

import com.challenge.volcano.island.controllers.request.BookingRequest;
import com.challenge.volcano.island.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper( BookingMapper.class );

    @Mappings({
            @Mapping(source = "arrivalOn", target = "arrivalOn"),
            @Mapping(source = "departureOn", target = "departureOn"),
            @Mapping(source = "qtyPersons", target = "qtyPersons")
    })
    Booking bookingRequestToBooking(BookingRequest bookingRequest);
}
