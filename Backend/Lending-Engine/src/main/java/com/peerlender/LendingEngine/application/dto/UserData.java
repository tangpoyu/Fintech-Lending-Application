package com.peerlender.LendingEngine.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
public class UserData {
    private ArrayList<Double> amount;

    public UserData() {
        this.amount = new ArrayList<>();
    }
}
