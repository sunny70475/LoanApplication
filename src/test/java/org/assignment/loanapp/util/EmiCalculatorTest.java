package org.assignment.loanapp.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EmiCalculatorTest {

    @Test
    void testEmiCalculation() {

        BigDecimal emi = EmiCalculator.calculate(
                BigDecimal.valueOf(500000),
                BigDecimal.valueOf(12),
                36
        );

        assertNotNull(emi);
        assertTrue(emi.compareTo(BigDecimal.ZERO) > 0);
    }
}