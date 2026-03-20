package org.assignment.loanapp.service;

import org.assignment.loanapp.models.*;
import org.assignment.loanapp.repository.LoanRepository;
import org.assignment.loanapp.util.EmiCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class LoanService {

    @Autowired
    private EligibilityService eligibilityService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private LoanRepository repository;

    public LoanResponse process(LoanApplication app) {

        String applicationId = UUID.randomUUID().toString();

        // Step 1: Risk Band
        RiskBand band = offerService.getRiskBand(app.getApplicant().getCreditScore());

        // Step 2: Interest Rate
        BigDecimal interestRate = offerService.calculateInterestRate(
                band,
                app.getApplicant().getEmploymentType(),
                app.getLoan().getAmount()
        );

        // Step 3: EMI
        BigDecimal emi = EmiCalculator.calculate(
                app.getLoan().getAmount(),
                interestRate,
                app.getLoan().getTenureMonths()
        );

        // Step 4: Eligibility Check
        List<RejectionReason> reasons = eligibilityService.checkEligibility(app, emi);

        // Step 5: Offer rule (50%)
        if (emi.compareTo(app.getApplicant().getMonthlyIncome()
                .multiply(BigDecimal.valueOf(0.5))) > 0) {
            reasons.add(RejectionReason.EMI_EXCEEDS_50_PERCENT);
        }

        LoanResponse response;

        if (!reasons.isEmpty()) {
            response = LoanResponse.rejected(applicationId, reasons);
        } else {
            response = LoanResponse.approved(
                    applicationId,
                    band,
                    interestRate,
                    app.getLoan().getTenureMonths(),
                    emi,
                    emi.multiply(BigDecimal.valueOf(app.getLoan().getTenureMonths()))
            );
        }

        // Save for audit
        repository.save(applicationId, response);

        return response;
    }
}