package com.challenge.volcano.island.repositories;

import com.challenge.volcano.island.model.DatesByBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface DatesByBookingRepository extends JpaRepository<DatesByBooking,Long> {

    @Query(
            value = "SELECT * FROM dates_by_booking where date >= :from and date<= :to",
            nativeQuery = true)
    List<DatesByBooking> findAllByRangeOfDates(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
