package com.peerlender.LendingEngine.domain.repository;


import com.peerlender.LendingEngine.domain.entity.LoanApplication;
import com.peerlender.LendingEngine.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;
import java.util.Optional;


public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    Optional<List<LoanApplication>> findAllByStatus(Status status);
}
