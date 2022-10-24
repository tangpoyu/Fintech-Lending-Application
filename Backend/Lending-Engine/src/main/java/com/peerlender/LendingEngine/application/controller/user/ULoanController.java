package com.peerlender.LendingEngine.application.controller.user;


import com.peerlender.LendingEngine.application.dto.LoanApplicationDto;
import com.peerlender.LendingEngine.application.dto.LoanDto;
import com.peerlender.LendingEngine.application.dto.LoanRepayRequest;
import com.peerlender.LendingEngine.application.dto.LoanRequest;
import com.peerlender.LendingEngine.application.utils.Converter;
import com.peerlender.LendingEngine.application.utils.JwtService;
import com.peerlender.LendingEngine.domain.entity.*;
import com.peerlender.LendingEngine.domain.service.LoanApplicationService;
import com.peerlender.LendingEngine.domain.service.LoanService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import reactor.core.publisher.Mono;
import java.util.List;
import java.util.stream.Collectors;


// TODO: make endpoint which can see all loan application either ONGOING(0) OR COMPLETED(1) by System Admin.

@RestController
@RequestMapping(value = "user/loan")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class ULoanController {

    private final LoanApplicationService loanApplicationService;
    private final LoanService loanService;
    private final JwtService jwtService;


    public ULoanController(LoanApplicationService loanApplicationService,
                           LoanService loanService,
                           JwtService jwtService) {
        this.loanApplicationService = loanApplicationService;
        this.loanService = loanService;
        this.jwtService = jwtService;
    }


    @PostMapping(value = "request")
    public ResponseEntity<Long> RequestLoan(@RequestBody final LoanRequest loanRequest, KeycloakAuthenticationToken authenticationToken){
       User borrower = jwtService.getUserFromToken(authenticationToken);
       LoanApplication loanApplication = loanApplicationService.buildLoanApplication(loanRequest, borrower);
       return ResponseEntity.ok(loanApplication.getId());
    }

    @GetMapping(value = "borrowed/{status}/all")
    public ResponseEntity<List<LoanDto>> FindBorrowedLoans(@PathVariable Status status, KeycloakAuthenticationToken authenticationToken){
        User borrower = jwtService.getUserFromToken(authenticationToken);
        List<LoanDto> loanDtos = loanService.FindAllBorrowedLoans(borrower,status).stream().map(Converter::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(loanDtos);
    }

    @GetMapping(value = "lent/{status}/all")
    public ResponseEntity<List<LoanDto>> FindLentLoans(@PathVariable Status status, KeycloakAuthenticationToken authenticationToken){
        User lender = jwtService.getUserFromToken(authenticationToken);
        List<LoanDto> loanDtos = loanService.FindAllLentLoans(lender,status).stream().map(Converter::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(loanDtos);
    }

    @PostMapping(value = "repay")
    public ResponseEntity<Void> RepayLoan(@RequestBody final LoanRepayRequest loanRepayRequest, KeycloakAuthenticationToken authenticationToken){
        User user = jwtService.getUserFromToken(authenticationToken);
        loanService.RepayLoan(loanRepayRequest.getAmount(), loanRepayRequest.getLoanId(), user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "application/all")
    public ResponseEntity<List<LoanApplicationDto>> FindAllLoanApplications(KeycloakAuthenticationToken authenticationToken){
        User user = jwtService.getUserFromToken(authenticationToken);

        List<LoanApplicationDto> loanApplicationDtos = loanApplicationService.findAllByStatus(Status.ONGOING)
                .stream()
                .filter(loanApplication -> loanApplication.getUsername() != user.getUsername())
                .collect(Collectors.toList());

        return ResponseEntity.ok(loanApplicationDtos);
    }

    @PostMapping(value = "accept/{loanApplicationId}")
    public ResponseEntity<Void> AcceptLoan(@PathVariable final long loanApplicationId, KeycloakAuthenticationToken authenticationToken){
        User lender = jwtService.getUserFromToken(authenticationToken);
        loanService.AcceptLoan(loanApplicationId, lender);
        return ResponseEntity.noContent().build();
    }
}
