package org.assignment.loanapp.models;
import java.math.BigDecimal;
import java.util.List;

public class LoanResponse {

    private String applicationId;
    private ApplicationStatus status;
    private RiskBand riskBand;
    private Offer offer;
    private List<RejectionReason> rejectionReasons;


    private LoanResponse() {}


    public static LoanResponse approved(String applicationId,
                                        RiskBand riskBand,
                                        BigDecimal interestRate,
                                        int tenureMonths,
                                        BigDecimal emi,
                                        BigDecimal totalPayable) {

        LoanResponse response = new LoanResponse();
        response.applicationId = applicationId;
        response.status = ApplicationStatus.APPROVED;
        response.riskBand = riskBand;

        response.offer = new Offer(
                interestRate,
                tenureMonths,
                emi,
                totalPayable
        );

        response.rejectionReasons = null;

        return response;
    }


    public static LoanResponse rejected(String applicationId,
                                        List<RejectionReason> reasons) {

        LoanResponse response = new LoanResponse();
        response.applicationId = applicationId;
        response.status = ApplicationStatus.REJECTED;
        response.riskBand = null;
        response.offer = null;
        response.rejectionReasons = reasons;

        return response;
    }


    public String getApplicationId() {
        return applicationId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public RiskBand getRiskBand() {
        return riskBand;
    }

    public Offer getOffer() {
        return offer;
    }

    public List<RejectionReason> getRejectionReasons() {
        return rejectionReasons;
    }
}