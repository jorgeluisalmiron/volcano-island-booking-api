package com.challenge.volcano.island.services.rules;

import com.challenge.volcano.island.model.Rule;
import com.challenge.volcano.island.services.AvailabilitiesService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class BookingRulesServiceTest {

    @Mock
    private RulesService rulesService;

    @Mock
    private AvailabilitiesService availabilitiesService;

    @InjectMocks
    private BookingRulesService bookingRulesService = new BookingRulesService();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void stayRuleTest_ok(){
        try {
            when(rulesService.getRule("STAY_RULE")).thenReturn(getRule(1,3,"STAY_RULE"));
            LocalDate arrivalOn = LocalDate.now().plus(1, ChronoUnit.DAYS);
            LocalDate departureOn = LocalDate.now().plus(4, ChronoUnit.DAYS);
            bookingRulesService.stayRule(arrivalOn,departureOn);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

    }

    @Test
    public void stayRuleTest_fail(){
        try {
            when(rulesService.getRule("STAY_RULE")).thenReturn(getRule(1,3,"STAY_RULE"));
            LocalDate arrivalOn = LocalDate.now().plus(1, ChronoUnit.DAYS);
            LocalDate departureOn = LocalDate.now().plus(5, ChronoUnit.DAYS);
            bookingRulesService.stayRule(arrivalOn,departureOn);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }

    }

    @Test
    public void reservedRuleTest_ok(){
        try {
            when(rulesService.getRule("RESERVED_DAYS_RULE")).thenReturn(getRule(1,30,"RESERVED_DAYS_RULE"));
            LocalDate arrivalOn = LocalDate.now().plus(1, ChronoUnit.DAYS);
            LocalDate departureOn = LocalDate.now().plus(4, ChronoUnit.DAYS);
            bookingRulesService.reservedDaysRule(arrivalOn,departureOn);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

    }

    @Test
    public void reservedRuleTest_fail(){
        try {
            when(rulesService.getRule("RESERVED_DAYS_RULE")).thenReturn(getRule(1,30,"RESERVED_DAYS_RULE"));
            LocalDate arrivalOn = LocalDate.now().plus(29, ChronoUnit.DAYS);
            LocalDate departureOn = LocalDate.now().plus(32, ChronoUnit.DAYS);
            bookingRulesService.reservedDaysRule(arrivalOn,departureOn);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }

    }

    @Test
    public void personsPerBookingRuleTest_ok(){
        try {
            when(rulesService.getRule("PERSONS_PER_BOOKING_RULE")).thenReturn(getRule(1,10,"PERSONS_PER_BOOKING_RULE"));
            bookingRulesService.qtyPersonsRule(5);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

    }

    @Test
    public void personsPerBookingRuleTest_fail(){
        try {
            when(rulesService.getRule("PERSONS_PER_BOOKING_RULE")).thenReturn(getRule(1,10,"PERSONS_PER_BOOKING_RULE"));
            bookingRulesService.qtyPersonsRule(15);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }

    }


    @Test
    public void availabilityRuleTest_ok(){
        try {
            LocalDate arrivalOn = LocalDate.now().plus(1, ChronoUnit.DAYS);
            LocalDate departureOn = LocalDate.now().plus(4, ChronoUnit.DAYS);
            when(availabilitiesService.checkAvailabilities(arrivalOn, 3, 1)).thenReturn(true);
            bookingRulesService.availabilityRule(arrivalOn, departureOn, 1);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

    }

    @Test
    public void availabilityRuleTest_fail(){
        try {

            LocalDate arrivalOn = LocalDate.now().plus(1, ChronoUnit.DAYS);
            LocalDate departureOn = LocalDate.now().plus(4, ChronoUnit.DAYS);
            when(availabilitiesService.checkAvailabilities(arrivalOn, 3, 1)).thenReturn(false);
            bookingRulesService.availabilityRule(arrivalOn, departureOn, 1);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }

    }


    private Rule getRule(int min, int max, String name){
        Rule rule = new Rule();
        rule.setName(name);
        rule.setMin(min);
        rule.setMax(max);
        return rule;
    }
}
