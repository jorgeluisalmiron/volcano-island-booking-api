package com.challenge.volcano.island.services.rules;

import com.challenge.volcano.island.controllers.request.BookingInfo;
import com.challenge.volcano.island.model.Booking;
import org.springframework.stereotype.Service;


@Service
public class UpdatingBookingRulesService extends BookingRulesService {

    public void execute(Booking booking, BookingInfo bookingInfo) throws Exception {
        this.fromDateRule(booking.getArrivalOn(), "300", "Could not update an expired booking");
        super.execute(bookingInfo);
    }

}
