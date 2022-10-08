package com.peerlender.securityapp.user.model.repository;

import com.peerlender.securityapp.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
