package com.challenge.volcano.island.mappers;

import com.challenge.volcano.island.controllers.response.BookingUpdateResponse;
import com.challenge.volcano.island.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface BookingUpdateResponseMapper {
    BookingUpdateResponseMapper INSTANCE = Mappers.getMapper( BookingUpdateResponseMapper.class );

    @Mappings({
            @Mapping(source = "booking.id", target = "newBookingId"),
            @Mapping(source = "cancelledBookingId", target = "cancelledBookingId"),
            @Mapping(source = "booking.arrivalOn", target = "arrivalOn"),
            @Mapping(source = "booking.departureOn", target = "departureOn"),
            @Mapping(source = "booking.qtyPersons", target = "qtyPersons")
    })
    BookingUpdateResponse bookingToBookingResponse(Booking booking, Long cancelledBookingId);
}
