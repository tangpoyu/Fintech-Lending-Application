package com.peerlender.LendingEngine.domain.repository;

import com.peerlender.LendingEngine.domain.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan,Long> {

}
