package com.peerlender.LendingEngine.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AmountDto {
    private double amount;
    private String currency;
}
