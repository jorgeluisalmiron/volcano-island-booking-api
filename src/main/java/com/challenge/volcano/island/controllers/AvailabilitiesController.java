package com.challenge.volcano.island.controllers;

import com.challenge.volcano.island.controllers.response.AvailabilitiesResponse;
import com.challenge.volcano.island.controllers.response.ErrorDetails;
import com.challenge.volcano.island.services.AvailabilitiesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@Api(value = "onlinebooking", description = "Operations pertaining get availabilities for Volcano Island")
@RequestMapping(value = "/api/availabilities")
public class AvailabilitiesController {


    @Autowired
    private AvailabilitiesService availabilitiesService;


    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get Availabilities for a range within the next 30 days")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetails.class)

    })
    public ResponseEntity<AvailabilitiesResponse> getAvailabilitiesByRange(@RequestParam(required = false)
                                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                                           @RequestParam(required = false)
                                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to)
            throws Exception {

        AvailabilitiesResponse availabilitiesResponse = availabilitiesService.getAvailabilities(from, to);
        return ResponseEntity.status(HttpStatus.OK).body(availabilitiesResponse);

    }

}
