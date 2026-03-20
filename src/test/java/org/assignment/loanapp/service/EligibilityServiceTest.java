package org.assignment.loanapp.service;

import org.assignment.loanapp.models.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EligibilityServiceTest {

    private final EligibilityService service = new EligibilityService();

    @Test
    void testAgeTenureLimitExceeded() {

        Applicant applicant = new Applicant("Test", 60,
                BigDecimal.valueOf(50000),
                EmploymentType.SALARIED,
                700);

        Loan loan = new Loan(BigDecimal.valueOf(500000), 120, LoanPurpose.PERSONAL);
        // 120 months = 10 years → 60 + 10 = 70 > 65

        LoanApplication app = new LoanApplication(applicant, loan);

        List<RejectionReason> reasons =
                service.checkEligibility(app, BigDecimal.valueOf(10000));

        assertTrue(reasons.contains(RejectionReason.AGE_TENURE_LIMIT_EXCEEDED));
    }

    @Test
    void testEmiExceeds60Percent() {

        Applicant applicant = new Applicant("Test", 30,
                BigDecimal.valueOf(20000),
                EmploymentType.SALARIED,
                700);

        Loan loan = new Loan(BigDecimal.valueOf(500000), 36, LoanPurpose.PERSONAL);

        LoanApplication app = new LoanApplication(applicant, loan);

        // 60% of 20k = 12k → EMI = 15k (exceeds)
        List<RejectionReason> reasons =
                service.checkEligibility(app, BigDecimal.valueOf(15000));

        assertTrue(reasons.contains(RejectionReason.EMI_EXCEEDS_60_PERCENT));
    }

    @Test
    void testEligibleApplication() {

        Applicant applicant = new Applicant("Test", 30,
                BigDecimal.valueOf(50000),
                EmploymentType.SALARIED,
                750);

        Loan loan = new Loan(BigDecimal.valueOf(200000), 24, LoanPurpose.PERSONAL);

        LoanApplication app = new LoanApplication(applicant, loan);

        List<RejectionReason> reasons =
                service.checkEligibility(app, BigDecimal.valueOf(10000));

        assertTrue(reasons.isEmpty());
    }

    @Test
    void testMultipleRejections() {

        Applicant applicant = new Applicant("Test", 60,
                BigDecimal.valueOf(20000),
                EmploymentType.SALARIED,
                500);

        Loan loan = new Loan(BigDecimal.valueOf(500000), 120, LoanPurpose.PERSONAL);

        LoanApplication app = new LoanApplication(applicant, loan);

        List<RejectionReason> reasons =
                service.checkEligibility(app, BigDecimal.valueOf(20000));

        assertTrue(reasons.contains(RejectionReason.LOW_CREDIT_SCORE));
        assertTrue(reasons.contains(RejectionReason.AGE_TENURE_LIMIT_EXCEEDED));
        assertTrue(reasons.contains(RejectionReason.EMI_EXCEEDS_60_PERCENT));
    }
}