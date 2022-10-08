package com.peerlender.security2.dao;

import com.peerlender.security2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
