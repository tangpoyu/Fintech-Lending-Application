package com.peerlender.LendingEngine.domain.repository.service;



import com.peerlender.LendingEngine.domain.exeception.LoanApplicationNotFoundException;
import com.peerlender.LendingEngine.domain.exeception.UserNotFoundException;
import com.peerlender.LendingEngine.domain.model.Loan;
import com.peerlender.LendingEngine.domain.model.LoanApplication;
import com.peerlender.LendingEngine.domain.model.User;
import com.peerlender.LendingEngine.domain.repository.LoanApplicationRepository;
import com.peerlender.LendingEngine.domain.repository.LoanRepository;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoanService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    public LoanService(LoanApplicationRepository loanApplicationRepository, UserRepository userRepository, LoanRepository loanRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public void AcceptLoan(final long loanApplicationId, final User lender){
        LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new LoanApplicationNotFoundException(loanApplicationId));
        loanRepository.save(new Loan(lender,loanApplication));
    }

     public List<Loan> GetLoans(){
        return loanRepository.findAll();
     }
}
