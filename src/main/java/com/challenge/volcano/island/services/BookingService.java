package com.challenge.volcano.island.services;


import com.challenge.volcano.island.controllers.request.BookingRequest;
import com.challenge.volcano.island.controllers.request.BookingUpdateRequest;
import com.challenge.volcano.island.controllers.response.BookingResponse;




public interface BookingService {

    BookingResponse createBooking(BookingRequest bookingRequest) throws Exception;

    BookingResponse updateBooking(BookingUpdateRequest bookingUpdateRequest) throws Exception;

    void cancelBooking(Long id) throws Exception;

    BookingResponse getBooking(Long id) throws Exception;
}
