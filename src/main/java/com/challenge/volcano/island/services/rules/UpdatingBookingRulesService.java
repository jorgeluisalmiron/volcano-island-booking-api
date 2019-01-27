package com.challenge.volcano.island.services.rules;

import com.challenge.volcano.island.controllers.request.BookingInfo;
import com.challenge.volcano.island.controllers.request.BookingUpdateRequest;
import com.challenge.volcano.island.exceptions.CustomException;
import com.challenge.volcano.island.exceptions.RuleException;
import com.challenge.volcano.island.model.Booking;
import org.springframework.stereotype.Service;


@Service
public class UpdatingBookingRulesService extends BookingRulesService {

    public void execute(Booking booking, BookingInfo bookingInfo) throws CustomException {
        this.fromDateRule(booking.getArrivalOn(), "300", "Could not update an expired booking");
        this.validatePerson(booking, ((BookingUpdateRequest) bookingInfo).getEmail());
        super.execute(bookingInfo);
    }

    protected void validatePerson(Booking booking, String email) throws CustomException {
        if (!booking.getCustomer().getEmail().equals(email)) {
            throw new RuleException("301", "This person cannot perform an update to this booking");
        }
    }
}
