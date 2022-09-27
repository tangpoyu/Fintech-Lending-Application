package com.peerlender.LendingEngine.domain.repository;


import com.peerlender.LendingEngine.domain.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
}
