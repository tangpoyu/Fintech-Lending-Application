package com.peerlender.LendingEngine.application;


import com.peerlender.LendingEngine.application.model.LoanRequest;
import com.peerlender.LendingEngine.application.service.TokenValidationService;
import com.peerlender.LendingEngine.domain.model.Loan;
import com.peerlender.LendingEngine.domain.model.LoanApplication;
import com.peerlender.LendingEngine.domain.model.User;
import com.peerlender.LendingEngine.domain.repository.LoanApplicationRepository;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import com.peerlender.LendingEngine.domain.repository.service.LoanApplicationAdapter;
import com.peerlender.LendingEngine.domain.repository.service.LoanService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class LoanController {

    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final LoanApplicationAdapter loanApplicationAdapter;
    private final LoanService loanService;
    private final TokenValidationService tokenValidationService;

    public LoanController(LoanApplicationRepository loanApplicationRepository, UserRepository userRepository, LoanApplicationAdapter loanApplicationAdapter, LoanService loanService, TokenValidationService tokenValidationService) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
        this.loanApplicationAdapter = loanApplicationAdapter;
        this.loanService = loanService;
        this.tokenValidationService = tokenValidationService;
    }

    @GetMapping(value = "/users")
    public List<User> FindUsers(HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        tokenValidationService.ValidateTokenAndGetUser(token);
        return userRepository.findAll();
    }

    @PostMapping(value = "/loan/request")
    public void RequestLoan(@RequestBody final LoanRequest loanRequest, HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        User borrower = tokenValidationService.ValidateTokenAndGetUser(token);
        LoanApplication loanApplication = loanApplicationAdapter.transform(loanRequest, borrower);
        loanApplicationRepository.save(loanApplication);
    }

    @GetMapping(value = "loan/Application/all")
    public List<LoanApplication> FindAllLoanApplications(HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        tokenValidationService.ValidateTokenAndGetUser(token);
        List<LoanApplication> loanApplications = loanApplicationRepository.findAll();
        return loanApplications;
    }

    @PostMapping(value = "/loan/accept/{loanApplicationId}")
    public void AcceptLoan(@PathVariable final long loanApplicationId, HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        User lender = tokenValidationService.ValidateTokenAndGetUser(token);
        loanService.AcceptLoan(loanApplicationId, lender);
    }

    @GetMapping(value = "/loan/All")
    public List<Loan> GetAllLoans(HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        tokenValidationService.ValidateTokenAndGetUser(token);
        return loanService.GetLoans();
    }
}
