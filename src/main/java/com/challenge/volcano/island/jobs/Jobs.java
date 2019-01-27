package com.challenge.volcano.island.jobs;

import com.challenge.volcano.island.repositories.BookingRepository;
import com.challenge.volcano.island.services.AvailabilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Jobs {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AvailabilitiesService availabilitiesService;


    @Scheduled(cron = "${volcano.jobs.update-and-syncronize.cron}")
    public void updateBookingsAndSyncronizaCacheWithDataBase() {

        bookingRepository.completeBookings();
        availabilitiesService.refresh();
        System.out.println("Job is running");
    }
}
