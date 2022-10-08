package com.peerlender.security.user.service;

import com.peerlender.security.user.model.User;
import com.peerlender.security.user.model.UserDetailsImpl;
import com.peerlender.security.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserAuthenticationService authenticationService;

    public String register(UserDetailsImpl userDetails) {
        userRepository.save(new User(userDetails));
        return login(userDetails);
    }

    public String login(UserDetailsImpl userDetails) {
        return authenticationService.login(userDetails.getUsername(), userDetails.getPassword())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}
