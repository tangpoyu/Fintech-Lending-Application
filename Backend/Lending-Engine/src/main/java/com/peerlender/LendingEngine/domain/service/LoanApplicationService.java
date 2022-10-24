package com.peerlender.LendingEngine.domain.service;


import com.peerlender.LendingEngine.application.dto.LoanApplicationDto;
import com.peerlender.LendingEngine.application.dto.LoanRequest;
import com.peerlender.LendingEngine.application.utils.Converter;
import com.peerlender.LendingEngine.domain.entity.*;
import com.peerlender.LendingEngine.domain.repository.LoanApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
// import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;


    public LoanApplication buildLoanApplication(LoanRequest req, User borrower){
        Currency currency = Currency.valueOf(req.getCurrencyType());
        return loanApplicationRepository.save(new LoanApplication(new Money(currency, req.getAmount()),borrower, req.getDaysToRepay(), req.getInterestRate()));
    }

    public List<LoanApplicationDto> findAllByStatus(Status status) {
        return loanApplicationRepository.findAllByStatus(status).orElse(new ArrayList<>()).stream().map(Converter::entityToDto).collect(Collectors.toList());
    }
}
