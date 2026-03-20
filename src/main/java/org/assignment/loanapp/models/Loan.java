package org.assignment.loanapp.models;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
public class Loan {
    @NotNull
    @DecimalMin("10000")
    @DecimalMax("5000000")
    private BigDecimal amount;

    @Min(6) @Max(360)
    private int tenureMonths;

    @NotNull
    private LoanPurpose purpose;

    public Loan(BigDecimal amount, int tenureMonths, LoanPurpose purpose) {
        this.amount = amount;
        this.tenureMonths = tenureMonths;
        this.purpose = purpose;
    }

}