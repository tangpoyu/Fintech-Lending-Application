package com.peerlender.LendingEngine.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public final class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_application_generator")
    private long id;

    private Status status;
    @OneToOne(cascade = CascadeType.ALL)
    private Money amount;
    @ManyToOne
    private User borrower;
    private int repaymentTerm;
    private double interestRate;

    public LoanApplication(Money money, User borrower, int repaymentTerm, double interestRate) {
        this.status = Status.ONGOING;
        this.amount = money;
        this.borrower = borrower;
        this.repaymentTerm = repaymentTerm;
        this.interestRate = interestRate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanApplication that = (LoanApplication) o;
        return amount == that.amount && Double.compare(that.interestRate, interestRate) == 0 && Objects.equals(borrower, that.borrower) && Objects.equals(repaymentTerm, that.repaymentTerm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, borrower, repaymentTerm, interestRate);
    }

    @Override
    public String toString() {
        return "LoanRequest{" +
                "amount=" + amount +
                ", borrower=" + borrower +
                ", repaymentTerm=" + repaymentTerm +
                ", interestRate=" + interestRate +
                '}';
    }
}
