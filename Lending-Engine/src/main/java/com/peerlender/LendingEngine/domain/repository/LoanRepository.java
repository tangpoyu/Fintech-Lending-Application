package com.peerlender.LendingEngine.domain.repository;

import com.peerlender.LendingEngine.domain.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan,Long> {
    Optional<Loan> findFirstBy();

}
