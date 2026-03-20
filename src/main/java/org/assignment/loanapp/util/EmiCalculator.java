package org.assignment.loanapp.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class EmiCalculator {

    public static BigDecimal calculate(BigDecimal principal, BigDecimal annualRate, int months) {

        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12 * 100), 6, RoundingMode.HALF_UP);

        BigDecimal onePlusR = monthlyRate.add(BigDecimal.ONE);
        BigDecimal power = onePlusR.pow(months);

        BigDecimal numerator = principal.multiply(monthlyRate).multiply(power);
        BigDecimal denominator = power.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }
}