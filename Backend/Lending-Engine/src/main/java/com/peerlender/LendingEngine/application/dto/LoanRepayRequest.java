package com.peerlender.LendingEngine.application.dto;

import com.peerlender.LendingEngine.domain.entity.Currency;
import com.peerlender.LendingEngine.domain.entity.Money;
import lombok.AllArgsConstructor;

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
