package org.assignment.loanapp.models;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
public class Applicant {
    @NotBlank
    private String name;

    @Min(21) @Max(60)
    private int age;

    @NotNull
    @DecimalMin("1")
    private BigDecimal monthlyIncome;

    @NotNull
    private EmploymentType employmentType;

    @Min(300) @Max(900)
    private int creditScore;

    public Applicant(String name, int age, BigDecimal monthlyIncome,
                     EmploymentType employmentType, int creditScore) {
        this.name = name;
        this.age = age;
        this.monthlyIncome = monthlyIncome;
        this.employmentType = employmentType;
        this.creditScore = creditScore;
    }

    

}