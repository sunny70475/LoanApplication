package org.assignment.loanapp.service;

import org.assignment.loanapp.models.EmploymentType;
import org.assignment.loanapp.models.RiskBand;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OfferServiceTest {

    private final OfferService service = new OfferService();

    @Test
    void testRiskBandBoundaries() {
        assertEquals(RiskBand.LOW, service.getRiskBand(750));   // boundary
        assertEquals(RiskBand.MEDIUM, service.getRiskBand(650)); // boundary
        assertEquals(RiskBand.HIGH, service.getRiskBand(649));  // edge
    }

    @Test
    void testLowRiskNoPremiums() {

        BigDecimal rate = service.calculateInterestRate(
                RiskBand.LOW,
                EmploymentType.SALARIED,
                BigDecimal.valueOf(500000)
        );

        assertEquals(BigDecimal.valueOf(12), rate);
    }

    @Test
    void testMediumRiskPremium() {

        BigDecimal rate = service.calculateInterestRate(
                RiskBand.MEDIUM,
                EmploymentType.SALARIED,
                BigDecimal.valueOf(500000)
        );

        assertEquals(BigDecimal.valueOf(13.5), rate); // 12 + 1.5
    }

    @Test
    void testHighRiskPremium() {

        BigDecimal rate = service.calculateInterestRate(
                RiskBand.HIGH,
                EmploymentType.SALARIED,
                BigDecimal.valueOf(500000)
        );

        assertEquals(BigDecimal.valueOf(15), rate); // 12 + 3
    }

    @Test
    void testSelfEmployedPremium() {

        BigDecimal rate = service.calculateInterestRate(
                RiskBand.LOW,
                EmploymentType.SELF_EMPLOYED,
                BigDecimal.valueOf(500000)
        );

        assertEquals(BigDecimal.valueOf(13), rate); // 12 + 1
    }

    @Test
    void testLoanAmountPremium() {

        BigDecimal rate = service.calculateInterestRate(
                RiskBand.LOW,
                EmploymentType.SALARIED,
                BigDecimal.valueOf(1500000)
        );

        assertEquals(BigDecimal.valueOf(12.5), rate); // 12 + 0.5
    }

    @Test
    void testAllPremiumsCombined() {

        BigDecimal rate = service.calculateInterestRate(
                RiskBand.HIGH,
                EmploymentType.SELF_EMPLOYED,
                BigDecimal.valueOf(1500000)
        );

        // 12 + 3 (high) + 1 (self-employed) + 0.5 (loan size)
        assertEquals(BigDecimal.valueOf(16.5), rate);
    }
}