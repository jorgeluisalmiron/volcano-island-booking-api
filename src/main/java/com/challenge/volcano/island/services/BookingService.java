package com.challenge.volcano.island.services;


import com.challenge.volcano.island.controllers.request.BookingRequest;
import com.challenge.volcano.island.controllers.request.BookingUpdateRequest;
import com.challenge.volcano.island.controllers.response.BookingResponse;
import com.challenge.volcano.island.exceptions.CustomException;



public interface BookingService {

    BookingResponse createBooking(BookingRequest bookingRequest) throws Exception;
    BookingResponse updateBooking(BookingUpdateRequest bookingUpdateRequest) throws CustomException;
    void cancelBooking(Long id) throws CustomException;
    BookingResponse getBooking(Long id) throws CustomException;
}
