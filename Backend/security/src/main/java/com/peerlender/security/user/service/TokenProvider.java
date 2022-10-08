package com.peerlender.security.user.service;

import com.peerlender.security.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class TokenProvider extends AbstractUserDetailsAuthenticationProvider {

    private final UserAuthenticationService userAuthenticationService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        final Object token = authentication.getCredentials();
        return Optional.ofNullable(token)
                .map(String::valueOf)
                .map(userAuthenticationService::findByToken)
                .map(User::getUserDetails)
                .get();
    }
}
