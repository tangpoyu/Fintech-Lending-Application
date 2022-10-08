package com.peerlender.LendingEngine.application.model;

import com.peerlender.LendingEngine.domain.model.Currency;
import com.peerlender.LendingEngine.domain.model.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class LoanRepayRequest {
    private final long loanId;
    private final double amount;

    public long getLoanId() {
        return loanId;
    }

    public Money getAmount() {
        return new Money(Currency.NT,amount);
    }
}
