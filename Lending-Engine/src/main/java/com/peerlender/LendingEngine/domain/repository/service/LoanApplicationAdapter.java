package com.peerlender.LendingEngine.domain.repository.service;


import com.peerlender.LendingEngine.application.model.LoanRequest;
import com.peerlender.LendingEngine.domain.exeception.UserNotFoundException;
import com.peerlender.LendingEngine.domain.model.LoanApplication;
import com.peerlender.LendingEngine.domain.model.User;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class LoanApplicationAdapter {

    private final UserRepository userRepository;

    public LoanApplicationAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoanApplication transform(LoanRequest req, User borrower){
        return new LoanApplication(req.getAmount(),borrower, req.getDaysToRepay(), req.getInterestRate());
    }
}
