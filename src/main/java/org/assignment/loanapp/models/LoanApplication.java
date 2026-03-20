package org.assignment.loanapp.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LoanApplication {

    private String applicationId = UUID.randomUUID().toString();

    @Valid
    @NotNull
    private Applicant applicant;

    @Valid
    @NotNull
    private Loan loan;

    private RiskBand riskBand;

    public LoanApplication(Applicant applicant, Loan loan) {
        this.applicant = applicant;
        this.loan = loan;
    }


}