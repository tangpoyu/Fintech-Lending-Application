package com.peerlender.LendingEngine.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class LoanRequest {

    private final int amount;
    private final int daysToRepay;
    private final double interestRate;
}
