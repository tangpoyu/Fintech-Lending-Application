package com.peerlender.LendingEngine.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_generator")
    private long id;
    private Status status;
    @ManyToOne
    private User borrower, lender;
    @OneToOne(cascade = CascadeType.ALL)
    private Money amount;
    @OneToOne(cascade = CascadeType.ALL)
    private Money amountRepaid;
    private double interestRate;
    private LocalDate dateLent;
    private LocalDate dateDue;

    public Loan(User lender, LoanApplication loanApplication){
        this.status = Status.ONGOING;
        this.borrower = loanApplication.getBorrower();
        this.lender = lender;
        this.amount = loanApplication.getAmount();
        this.amountRepaid = new Money(loanApplication.getAmount().getCurrency(), 0);
        this.interestRate = loanApplication.getInterestRate();
        this.dateLent = LocalDate.now();
        this.dateDue = LocalDate.now().plusDays(loanApplication.getRepaymentTerm());
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Money getAmount() {
        return new Money(amount.getCurrency(), amount.getAmount() * (1 + interestRate / 100d) - amountRepaid.getAmount());
    }

    public void setAmountRepaid(Money amountRepaid) {
        this.amountRepaid = amountRepaid;
    }
}

