package org.assignment.loanapp.service;

import org.assignment.loanapp.models.LoanApplication;
import org.assignment.loanapp.models.RejectionReason;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class EligibilityService {

    public List<RejectionReason> checkEligibility(LoanApplication app, BigDecimal emi) {

        List<RejectionReason> reasons = new ArrayList<>();

        // Rule 1: Credit Score
        if (app.getApplicant().getCreditScore() < 600) {
            reasons.add(RejectionReason.LOW_CREDIT_SCORE);
        }

        // Rule 2: Age + Tenure
        int age = app.getApplicant().getAge();
        int tenureYears = app.getLoan().getTenureMonths() / 12;

        if (age + tenureYears > 65) {
            reasons.add(RejectionReason.AGE_TENURE_LIMIT_EXCEEDED);
        }

        // Rule 3: EMI > 60% income
        BigDecimal income = app.getApplicant().getMonthlyIncome();

        if (emi.compareTo(income.multiply(BigDecimal.valueOf(0.6))) > 0) {
            reasons.add(RejectionReason.EMI_EXCEEDS_60_PERCENT);
        }

        return reasons;
    }
}