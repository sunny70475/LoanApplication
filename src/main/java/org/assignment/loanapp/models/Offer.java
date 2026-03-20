package org.assignment.loanapp.models;
import java.math.BigDecimal;

public class Offer {

    private BigDecimal interestRate;
    private int tenureMonths;
    private BigDecimal emi;
    private BigDecimal totalPayable;

    public Offer(BigDecimal interestRate, int tenureMonths,
                 BigDecimal emi, BigDecimal totalPayable) {
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.emi = emi;
        this.totalPayable = totalPayable;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public BigDecimal getEmi() {
        return emi;
    }

    public BigDecimal getTotalPayable() {
        return totalPayable;
    }
}
