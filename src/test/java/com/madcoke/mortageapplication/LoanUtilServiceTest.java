package com.madcoke.mortageapplication;
import org.junit.jupiter.api.Test;

import com.madcoke.mortageapplication.service.LoanUtilService;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LoanUtilServiceTest {

    @Test
    public void testCalculateInstallment() {
        // Test cases success
        Double amount1 = 100000.0;
        Double annualInterest1 = 5.0;
        Integer years1 = 10;
        String expectedInstallment1 = "1060,66";
        String actualInstallment1 = LoanUtilService.calculateInstallment(amount1, annualInterest1, years1);
        assertEquals(expectedInstallment1, actualInstallment1);

        Double amount2 = 200000.0;
        Double annualInterest2 = 4.5;
        Integer years2 = 15;
        String expectedInstallment2 = "1529,99";
        String actualInstallment2 = LoanUtilService.calculateInstallment(amount2, annualInterest2, years2);
        assertEquals(expectedInstallment2, actualInstallment2);
    }
}