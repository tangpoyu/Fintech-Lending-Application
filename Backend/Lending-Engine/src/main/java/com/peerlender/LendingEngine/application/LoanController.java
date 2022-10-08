package com.peerlender.LendingEngine.application;


import com.peerlender.LendingEngine.application.model.LoanRepayRequest;
import com.peerlender.LendingEngine.application.model.LoanRequest;
import com.peerlender.LendingEngine.application.service.TokenValidationService;
import com.peerlender.LendingEngine.domain.model.*;
import com.peerlender.LendingEngine.domain.repository.LoanApplicationRepository;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import com.peerlender.LendingEngine.domain.service.LoanApplicationAdapter;
import com.peerlender.LendingEngine.domain.service.LoanService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


// TODO: make endpoint which can see all loan application either ONGOING(0) OR COMPLETED(1) by System Admin.

@RestController
@RequestMapping(value = "loan")
public class LoanController {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanApplicationAdapter loanApplicationAdapter;
    private final LoanService loanService;
    private final TokenValidationService tokenValidationService;

    public LoanController(LoanApplicationRepository loanApplicationRepository, LoanApplicationAdapter loanApplicationAdapter, LoanService loanService, TokenValidationService tokenValidationService) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanApplicationAdapter = loanApplicationAdapter;
        this.loanService = loanService;
        this.tokenValidationService = tokenValidationService;
    }



    @PostMapping(value = "request")
    public void RequestLoan(@RequestBody final LoanRequest loanRequest, HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        User borrower = tokenValidationService.ValidateTokenAndGetUser(token);
        LoanApplication loanApplication = loanApplicationAdapter.transform(loanRequest, borrower);
        loanApplicationRepository.save(loanApplication);
    }

    @GetMapping(value = "borrowed/{status}/all")
    public List<Loan> FindBorrowedLoans(@RequestHeader String authorization,
                                        @PathVariable Status status){
       User borrower = tokenValidationService.ValidateTokenAndGetUser(authorization);
       return loanService.FindAllBorrowedLoans(borrower,status);
    }

    @GetMapping(value = "lent/{status}/all")
    public List<Loan> FindLentLoans(@RequestHeader String authorization,
                                    @PathVariable Status status){
        User lender = tokenValidationService.ValidateTokenAndGetUser(authorization);
        return loanService.FindAllLentLoans(lender,status);
    }

    @PostMapping(value = "repay")
    public void RepayLoan(@RequestBody final LoanRepayRequest loanRepayRequest, @RequestHeader final String authorization){
        User user = tokenValidationService.ValidateTokenAndGetUser(authorization);
        loanService.RepayLoan(loanRepayRequest.getAmount(), loanRepayRequest.getLoanId(), user);
    }

    @GetMapping(value = "Application/all")
    public List<LoanApplication> FindAllLoanApplications(HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        tokenValidationService.ValidateTokenAndGetUser(token);
        List<LoanApplication> loanApplications = loanApplicationRepository.findAllByStatus(Status.ONGOING);
        return loanApplications;
    }

    @PostMapping(value = "accept/{loanApplicationId}")
    public void AcceptLoan(@PathVariable final long loanApplicationId, HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        User lender = tokenValidationService.ValidateTokenAndGetUser(token);
        loanService.AcceptLoan(loanApplicationId, lender);
    }

    @GetMapping(value = "all")
    public List<Loan> GetAllLoans(HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        tokenValidationService.ValidateTokenAndGetUser(token);
        return loanService.GetLoans();
    }
}
