package com.peerlender.LendingEngine.application;


import com.peerlender.LendingEngine.application.model.LoanRequest;
import com.peerlender.LendingEngine.domain.model.Loan;
import com.peerlender.LendingEngine.domain.model.LoanApplication;
import com.peerlender.LendingEngine.domain.model.User;
import com.peerlender.LendingEngine.domain.repository.LoanApplicationRepository;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import com.peerlender.LendingEngine.domain.repository.service.LoanApplicationAdapter;
import com.peerlender.LendingEngine.domain.repository.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class LoanController {

    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final LoanApplicationAdapter loanApplicationAdapter;
    private final LoanService loanService;

    public LoanController(LoanApplicationRepository loanApplicationRepository, UserRepository userRepository, LoanApplicationAdapter loanApplicationAdapter, LoanService loanService) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
        this.loanApplicationAdapter = loanApplicationAdapter;
        this.loanService = loanService;
    }

    @GetMapping(value = "loan/Application/all")
    public List<LoanApplication> FindAllLoanApplications(){
        List<LoanApplication> loanApplications = loanApplicationRepository.findAll();
        return loanApplications;
    }

    @PostMapping(value = "/loan/request")
    public void RequestLoan(@RequestBody final LoanRequest loanRequest){
        LoanApplication loanApplication = loanApplicationAdapter.transform(loanRequest);
        loanApplicationRepository.save(loanApplication);
    }

    @GetMapping(value = "/users")
    public List<User> FindUsers(){
        return userRepository.findAll();
    }

    @PostMapping(value = "/loan/accept/{lenderId}/{loanApplicationId}")
    public void AcceptLoan(@PathVariable final long lenderId, @PathVariable final long loanApplicationId){
        loanService.AcceptLoan(loanApplicationId,lenderId);
    }

    @GetMapping(value = "/loan/All")
    public List<Loan> GetAllLoans(){
        return loanService.GetLoans();
    }
}
