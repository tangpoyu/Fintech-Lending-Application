package com.peerlender.LendingEngine.domain.repository;

import com.peerlender.LendingEngine.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
