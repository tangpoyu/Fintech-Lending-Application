package com.peerlender.security.user.service;

import com.google.common.collect.ImmutableMap;
import com.peerlender.security.user.model.User;
import com.peerlender.security.user.model.UserDetailsImpl;
import com.peerlender.security.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
public class TokenAuthenticationService implements UserAuthenticationService{

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public Optional<String> login(String username, String password) {
        Optional<Optional<User>> users =  Optional.ofNullable(userRepository.findByUserDetails_Username(username));
        Optional<String> token = users.filter(user -> user.get().getUserDetails().getPassword().equals(password))
                .map(user -> tokenService.expiring(ImmutableMap.of("username", username)));
        return token;
    }

    @Override
    public User findByToken(String token) {
        Map<String,String> result = tokenService.verify(token);
        User user = userRepository.findByUserDetails_Username(result.get("username")).get();
        return user;
    }

    @Override
    public void logout(UserDetailsImpl userDetails) {

    }
}
