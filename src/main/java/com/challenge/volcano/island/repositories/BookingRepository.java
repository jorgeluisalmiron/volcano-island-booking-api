package com.challenge.volcano.island.repositories;

import com.challenge.volcano.island.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE bookings set status = 'completed' where departure_on <= now() and status = 'booked'",
            nativeQuery = true)
    int completeBookings();
}
