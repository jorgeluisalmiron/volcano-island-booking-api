package com.challenge.volcano.island.controllers;

import com.challenge.volcano.island.controllers.request.AvailabilitiesResponse;
import com.challenge.volcano.island.controllers.request.MessageResponse;
import com.challenge.volcano.island.exceptions.RuleException;
import com.challenge.volcano.island.services.AvailabilitiesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value="onlinebooking", description="Operations pertaining get availabilities for Volcano Island")
@RequestMapping(value = "/api/availabilities")
public class AvailabilitiesController {


    @Autowired
    private AvailabilitiesService availabilitiesService;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get Availabilities for next 30 days")
    public ResponseEntity<Object> getAvailabilities(){
        try{
            AvailabilitiesResponse availabilitiesResponse = availabilitiesService.getAvailabilities();
            return ResponseEntity.status(HttpStatus.OK).body(availabilitiesResponse);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("1","Error " + e.getMessage()));
        }
    }

    @RequestMapping(value = "/by-range", method = RequestMethod.GET, params = { "from", "to" })
    @ApiOperation(value = "Get Availabilities for a range within 30 days")
    public ResponseEntity<Object> getAvailabilitiesByRange(@RequestParam(value = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                           @RequestParam(value = "to")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to){
        try{
             AvailabilitiesResponse availabilitiesResponse = availabilitiesService.getAvailabilities(from, to);
             return ResponseEntity.status(HttpStatus.OK).body(availabilitiesResponse);
         }
        catch (RuleException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getCode(),e.getMessage()));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("1","Error " + e.getMessage()));
        }
    }

}
