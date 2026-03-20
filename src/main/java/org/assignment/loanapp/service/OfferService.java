package org.assignment.loanapp.service;

import org.assignment.loanapp.models.EmploymentType;
import org.assignment.loanapp.models.RiskBand;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OfferService {

    private static final BigDecimal BASE_RATE = BigDecimal.valueOf(12);

    public RiskBand getRiskBand(int creditScore) {

        if (creditScore >= 750) return RiskBand.LOW;
        if (creditScore >= 650) return RiskBand.MEDIUM;
        return RiskBand.HIGH;
    }

    public BigDecimal calculateInterestRate(RiskBand band,
                                            EmploymentType employmentType,
                                            BigDecimal loanAmount) {

        BigDecimal rate = BASE_RATE;

        // Risk premium
        if (band == RiskBand.MEDIUM) {
            rate = rate.add(BigDecimal.valueOf(1.5));
        } else if (band == RiskBand.HIGH) {
            rate = rate.add(BigDecimal.valueOf(3));
        }

        // Employment premium
        if (employmentType == EmploymentType.SELF_EMPLOYED) {
            rate = rate.add(BigDecimal.ONE);
        }

        // Loan size premium
        if (loanAmount.compareTo(BigDecimal.valueOf(10_00_000)) > 0) {
            rate = rate.add(BigDecimal.valueOf(0.5));
        }

        return rate;
    }
}