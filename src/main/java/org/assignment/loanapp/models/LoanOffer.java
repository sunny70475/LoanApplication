package org.assignment.loanapp.models;

import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class LoanOffer {

    private BigDecimal interestRate;
    private int tenureMonths;
    private BigDecimal emi;
    private BigDecimal totalPayable;

    public LoanOffer(BigDecimal interestRate, int tenureMonths,
                     BigDecimal emi, BigDecimal totalPayable) {
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.emi = emi;
        this.totalPayable = totalPayable;
    }

}
