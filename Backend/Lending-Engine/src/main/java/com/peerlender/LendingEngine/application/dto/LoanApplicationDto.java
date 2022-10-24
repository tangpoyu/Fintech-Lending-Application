package com.peerlender.LendingEngine.application.dto;

import com.peerlender.LendingEngine.domain.entity.LoanApplication;
import lombok.Data;

@Data
public class LoanApplicationDto {
    private long id;
    private double interestRate;
    private int repaymentTerm;
    private String username, fullName, occupation;
    private AmountDto amount;

    public LoanApplicationDto(LoanApplication loanApplication) {
        this.id = loanApplication.getId();
        this.interestRate = loanApplication.getInterestRate();
        this.repaymentTerm = loanApplication.getRepaymentTerm();
        this.username = loanApplication.getBorrower().getUsername();
        this.fullName = loanApplication.getBorrower().getFirst_name() + " " + loanApplication.getBorrower().getLast_name();
        this.occupation = loanApplication.getBorrower().getOccupation();
        this.amount = new AmountDto(loanApplication.getAmount().getAmount(),loanApplication.getAmount().getCurrency().name());
    }
}
