package com.peerlender.LendingEngine.application.dto;

import com.peerlender.LendingEngine.domain.entity.Currency;
import com.peerlender.LendingEngine.domain.entity.Money;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class LoanRepayRequest {
    private final long loanId;
    private final double amount;
}
