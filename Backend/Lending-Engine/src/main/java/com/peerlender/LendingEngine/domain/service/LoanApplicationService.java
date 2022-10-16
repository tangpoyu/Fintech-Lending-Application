package com.peerlender.LendingEngine.domain.service;


import com.peerlender.LendingEngine.application.dto.LoanRequest;
import com.peerlender.LendingEngine.domain.entity.Currency;
import com.peerlender.LendingEngine.domain.entity.LoanApplication;
import com.peerlender.LendingEngine.domain.entity.Money;
import com.peerlender.LendingEngine.domain.entity.User;
import com.peerlender.LendingEngine.domain.repository.LoanApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
// import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class LoanApplicationService {


    private final LoanApplicationRepository loanApplicationRepository;


    public LoanApplication buildLoanApplication(LoanRequest req, User borrower){
//        Currency currency = Currency.valueOf(req.getCurrencyType());
//        borrower.subscribe(
//                value -> {
//                    loanApplicationRepository.save(new LoanApplication(new Money(currency, req.getAmount()),value, req.getDaysToRepay(), req.getInterestRate()));
//                }
//        );
        Currency currency = Currency.valueOf(req.getCurrencyType());
        return new LoanApplication(new Money(currency, req.getAmount()),borrower, req.getDaysToRepay(), req.getInterestRate());
    }
}
