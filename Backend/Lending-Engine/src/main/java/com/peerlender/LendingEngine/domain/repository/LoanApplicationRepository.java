package com.peerlender.LendingEngine.domain.repository;


import com.peerlender.LendingEngine.domain.model.LoanApplication;
import com.peerlender.LendingEngine.domain.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findAllByStatus(Status status);
}
