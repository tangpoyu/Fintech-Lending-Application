package com.peerlender.LendingEngine.domain.repository;

import com.peerlender.LendingEngine.domain.entity.Loan;
import com.peerlender.LendingEngine.domain.entity.Status;
import com.peerlender.LendingEngine.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LoanRepository extends JpaRepository<Loan,Long> {
    List<Loan> findAllByBorrowerAndStatus(User borrower, Status status);
    List<Loan> findAllByLenderAndStatus(User lender, Status status);
    Optional<Loan> findOneByIdAndBorrowerAndStatus(long id, User borrower, Status status);
}
