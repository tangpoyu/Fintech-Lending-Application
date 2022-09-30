package com.peerlender.Profile.domain.repository;

import com.peerlender.Profile.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
