package org.assignment.loanapp.service;

import org.assignment.loanapp.models.*;
import org.assignment.loanapp.repository.LoanRepository;
import org.assignment.loanapp.util.EmiCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.io.ObjectInputFilter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private EligibilityService eligibilityService;

    @Mock
    private OfferService offerService;

    @Mock
    private LoanRepository repository;

    @Test
    void testLoanApproved() {

        Applicant applicant = new Applicant("Test", 30,
                BigDecimal.valueOf(50000),
                EmploymentType.SALARIED,
                750);

        Loan loan = new Loan(BigDecimal.valueOf(200000), 12, LoanPurpose.PERSONAL);
        LoanApplication app = new LoanApplication(applicant, loan);

        // Mock behavior
        when(offerService.getRiskBand(anyInt())).thenReturn(RiskBand.LOW);
        when(offerService.calculateInterestRate(any(), any(), any()))
                .thenReturn(BigDecimal.valueOf(12));

        // Mock eligibility (no rejection)
        when(eligibilityService.checkEligibility(any(), any()))
                .thenReturn(Collections.emptyList());

        // ⚠️ EMI calculation is static → let it run OR control input accordingly
        LoanResponse response = loanService.process(app);

        assertEquals(ApplicationStatus.APPROVED, response.getStatus());
        assertNotNull(response.getApplicationId());

        verify(repository, times(1)).save(anyString(), any());
    }

    @Test
    void testRejectedDueToEligibility() {

        Applicant applicant = new Applicant("Test", 30,
                BigDecimal.valueOf(50000),
                EmploymentType.SALARIED,
                500);

        Loan loan = new Loan(BigDecimal.valueOf(200000), 12, LoanPurpose.PERSONAL);
        LoanApplication app = new LoanApplication(applicant, loan);

        when(offerService.getRiskBand(anyInt())).thenReturn(RiskBand.HIGH);
        when(offerService.calculateInterestRate(any(), any(), any()))
                .thenReturn(BigDecimal.valueOf(15));

        when(eligibilityService.checkEligibility(any(), any()))
                .thenReturn(List.of(RejectionReason.LOW_CREDIT_SCORE));

        LoanResponse response = loanService.process(app);

        assertEquals(ApplicationStatus.REJECTED, response.getStatus());
        assertTrue(response.getRejectionReasons().contains(RejectionReason.LOW_CREDIT_SCORE));

        verify(repository).save(anyString(), any());
    }

    @Test
    void testRejectedDueTo50PercentRule() {

        Applicant applicant = new Applicant("Test", 30,
                BigDecimal.valueOf(10000), // low income
                EmploymentType.SALARIED,
                750);

        Loan loan = new Loan(BigDecimal.valueOf(500000), 12, LoanPurpose.PERSONAL);
        LoanApplication app = new LoanApplication(applicant, loan);

        when(offerService.getRiskBand(anyInt())).thenReturn(RiskBand.LOW);
        when(offerService.calculateInterestRate(any(), any(), any()))
                .thenReturn(BigDecimal.valueOf(12));

        // No eligibility rejection initially
        when(eligibilityService.checkEligibility(any(), any()))
                .thenReturn(new ArrayList<>());

        LoanResponse response = loanService.process(app);

        assertEquals(ApplicationStatus.REJECTED, response.getStatus());
        assertTrue(response.getRejectionReasons()
                .contains(RejectionReason.EMI_EXCEEDS_50_PERCENT));

        verify(repository).save(anyString(), any());
    }

    @Test
    void testInteractions() {

        Applicant applicant = new Applicant("Test", 30,
                BigDecimal.valueOf(50000),
                EmploymentType.SALARIED,
                750);

        Loan loan = new Loan(BigDecimal.valueOf(200000), 12, LoanPurpose.PERSONAL);
        LoanApplication app = new LoanApplication(applicant, loan);

        when(offerService.getRiskBand(anyInt())).thenReturn(RiskBand.LOW);
        when(offerService.calculateInterestRate(any(), any(), any()))
                .thenReturn(BigDecimal.valueOf(12));

        when(eligibilityService.checkEligibility(any(), any()))
                .thenReturn(Collections.emptyList());

        loanService.process(app);

        verify(offerService).getRiskBand(anyInt());
        verify(offerService).calculateInterestRate(any(), any(), any());
        verify(eligibilityService).checkEligibility(any(), any());
        verify(repository).save(anyString(), any());
    }
}