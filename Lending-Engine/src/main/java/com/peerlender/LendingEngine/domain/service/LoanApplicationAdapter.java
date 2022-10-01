package com.peerlender.LendingEngine.domain.service;


import com.peerlender.LendingEngine.application.model.LoanRequest;
import com.peerlender.LendingEngine.domain.model.Currency;
import com.peerlender.LendingEngine.domain.model.LoanApplication;
import com.peerlender.LendingEngine.domain.model.Money;
import com.peerlender.LendingEngine.domain.model.User;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanApplicationAdapter {

    private final UserRepository userRepository;

    public LoanApplicationAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoanApplication transform(LoanRequest req, User borrower){
        Currency currency = Currency.valueOf(req.getCurrencyType());
        return new LoanApplication(new Money(currency, req.getAmount()),borrower, req.getDaysToRepay(), req.getInterestRate());
    }
}
