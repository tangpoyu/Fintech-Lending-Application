package com.peerlender.LendingEngine.domain.service;

import com.peerlender.LendingEngine.domain.entity.User;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAllUser() {
        return userRepository.findAll();
    }
}
