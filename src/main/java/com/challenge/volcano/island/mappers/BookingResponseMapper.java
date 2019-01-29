package com.challenge.volcano.island.mappers;

import com.challenge.volcano.island.controllers.response.BookingResponse;
import com.challenge.volcano.island.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface BookingResponseMapper {
    BookingResponseMapper INSTANCE = Mappers.getMapper( BookingResponseMapper.class );

    @Mappings({
            @Mapping(source = "id", target = "bookingInfo.bookingId"),
            @Mapping(source = "arrivalOn", target = "bookingInfo.arrivalOn"),
            @Mapping(source = "departureOn", target = "bookingInfo.departureOn"),
            @Mapping(source = "qtyPersons", target = "bookingInfo.qtyPersons")
    })
    BookingResponse bookingToBookingResponse(Booking booking);
}
