package com.peerlender.LendingEngine.domain.service;



import com.peerlender.LendingEngine.domain.exeception.LoanApplicationNotFoundException;
import com.peerlender.LendingEngine.domain.exeception.LoanNotFoundException;
import com.peerlender.LendingEngine.domain.entity.*;
import com.peerlender.LendingEngine.domain.repository.LoanApplicationRepository;
import com.peerlender.LendingEngine.domain.repository.LoanRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    private final LoanApplicationRepository loanApplicationRepository;

    private final LoanRepository loanRepository;

    public LoanService(LoanApplicationRepository loanApplicationRepository, LoanRepository loanRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional
    public void AcceptLoan(final long loanApplicationId, final User lender){
        LoanApplication loanApplication = FindLoanApplication(loanApplicationId);
        User borrower = loanApplication.getBorrower();
        Money money = loanApplication.getAmount();
        lender.getBalance().Withdraw(money);
        borrower.getBalance().TopUp(money);
        loanApplication.setStatus(Status.COMPLETED);
        loanRepository.save(new Loan(lender,loanApplication));
    }

    @Transactional
    public void RepayLoan(final Money moneyToRepay, final long loanId, final User borrower){
       Loan loan = loanRepository.findOneByIdAndBorrowerAndStatus(loanId, borrower, Status.ONGOING)
               .orElseThrow(() -> new LoanNotFoundException(loanId));
       Money actualPaidAmount = moneyToRepay.getAmount() > loan.getAmount().getAmount() ? loan.getAmount() : moneyToRepay;
       borrower.getBalance().Withdraw(actualPaidAmount);
       loan.getLender().getBalance().TopUp(actualPaidAmount);
       Money updatedAmountRepayed =  loan.getAmountRepayed().Add(actualPaidAmount);
       loan.setAmountRepayed(updatedAmountRepayed);
       if(loan.getAmount().getAmount() == 0) {
           loan.setStatus(Status.COMPLETED);
       }
    }

    private LoanApplication FindLoanApplication(long loanApplicationId) {
        return loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new LoanApplicationNotFoundException(loanApplicationId));
    }

    public List<Loan> FindAllBorrowedLoans(User borrower, Status status){
        return loanRepository.findAllByBorrowerAndStatus(borrower, status);
    }

    public List<Loan> FindAllLentLoans(User lender, Status status){
        return loanRepository.findAllByLenderAndStatus(lender, status);
    }

    public List<Loan> GetLoans(Status status){
        return loanRepository.findAllByStatus(status).orElse(new ArrayList<>());
     }
}
