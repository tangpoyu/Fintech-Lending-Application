package com.peerlender.LendingEngine.application.utils;

import com.peerlender.LendingEngine.domain.entity.User;
import com.peerlender.LendingEngine.domain.exeception.UserNotFoundException;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class JwtService {

    private final UserRepository userRepository;

    public User getUserFromToken(KeycloakAuthenticationToken authenticationToken) {
        Object principal = authenticationToken.getPrincipal();
        String userIdByToken = "";
        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            String username = kPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername();
            User user = userRepository.findById(username).orElseThrow(() -> new UserNotFoundException(username));
            return user;
        }

        throw new RuntimeException("Token isn't KeycloakPrincipal");
    }
}
