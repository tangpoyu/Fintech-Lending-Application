package com.peerlender.LendingEngine.application;


import com.peerlender.LendingEngine.application.dto.LoanApplicationDto;
import com.peerlender.LendingEngine.application.dto.LoanDto;
import com.peerlender.LendingEngine.application.dto.LoanRepayRequest;
import com.peerlender.LendingEngine.application.dto.LoanRequest;
import com.peerlender.LendingEngine.application.service.TokenValidationService;
import com.peerlender.LendingEngine.application.utils.Converter;
import com.peerlender.LendingEngine.domain.entity.*;
import com.peerlender.LendingEngine.domain.repository.LoanApplicationRepository;
import com.peerlender.LendingEngine.domain.service.LoanApplicationService;
import com.peerlender.LendingEngine.domain.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import reactor.core.publisher.Mono;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


// TODO: make endpoint which can see all loan application either ONGOING(0) OR COMPLETED(1) by System Admin.

@RestController
@RequestMapping(value = "loan")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class LoanController {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanApplicationService loanApplicationService;
    private final LoanService loanService;
    private final TokenValidationService tokenValidationService;

    public LoanController(LoanApplicationRepository loanApplicationRepository, LoanApplicationService loanApplicationService, LoanService loanService, TokenValidationService tokenValidationService) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanApplicationService = loanApplicationService;
        this.loanService = loanService;
        this.tokenValidationService = tokenValidationService;
    }


    @PostMapping(value = "request")
    public ResponseEntity<Long> RequestLoan(@RequestBody final LoanRequest loanRequest, HttpServletRequest request){
        User borrower = tokenValidationService.ValidateTokenAndGetUser(request,"user");
        LoanApplication loanApplication = loanApplicationService.buildLoanApplication(loanRequest, borrower);
        return ResponseEntity.ok(loanApplicationRepository.save(loanApplication).getId());
    }

    @GetMapping(value = "borrowed/{status}/all")
    public ResponseEntity<List<LoanDto>> FindBorrowedLoans(HttpServletRequest request,
                                        @PathVariable Status status){
        User borrower = tokenValidationService.ValidateTokenAndGetUser(request,"user");
        List<LoanDto> loanDtos = loanService.FindAllBorrowedLoans(borrower,status).stream().map(Converter::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(loanDtos);
    }

    @GetMapping(value = "lent/{status}/all")
    public ResponseEntity<List<LoanDto>> FindLentLoans(HttpServletRequest request,
                                                       @PathVariable Status status){
        User lender = tokenValidationService.ValidateTokenAndGetUser(request,"user");
        List<LoanDto> loanDtos = loanService.FindAllLentLoans(lender,status).stream().map(Converter::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(loanDtos);
    }

    @PostMapping(value = "repay")
    public ResponseEntity<Void> RepayLoan(HttpServletRequest request,
                          @RequestBody final LoanRepayRequest loanRepayRequest){
        User user = tokenValidationService.ValidateTokenAndGetUser(request,"user");
        loanService.RepayLoan(loanRepayRequest.getAmount(), loanRepayRequest.getLoanId(), user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "Application/all")
    public ResponseEntity<List<LoanApplicationDto>> FindAllLoanApplications(HttpServletRequest request){
        tokenValidationService.ValidateTokenAndGetUser(request,"user");
        List<LoanApplication> loanApplications = loanApplicationRepository.findAllByStatus(Status.ONGOING);
        List<LoanApplicationDto> loanApplicationDtos = loanApplications.stream().map(Converter::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(loanApplicationDtos);
    }

    @PostMapping(value = "accept/{loanApplicationId}")
    public ResponseEntity<Void> AcceptLoan(@PathVariable final long loanApplicationId, HttpServletRequest request){
        User lender = tokenValidationService.ValidateTokenAndGetUser(request,"user");
        loanService.AcceptLoan(loanApplicationId, lender);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "all")
    public ResponseEntity<List<Loan>> GetAllLoans(HttpServletRequest request){
        tokenValidationService.ValidateTokenAndGetUser(request,"admin");
        return ResponseEntity.ok(loanService.GetLoans());
    }

}
