package com.peerlender.security.user.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final static String BEARER = "Bearer";

    public TokenAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        final String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwtToken = Optional.ofNullable(authToken)
                .map(token -> StringUtils.remove(token, BEARER))
                .map(String::trim)
                .orElseThrow(() -> new BadCredentialsException("Missing Authentication token."));

        final Authentication auth = new UsernamePasswordAuthenticationToken(jwtToken,jwtToken);
        return getAuthenticationManager().authenticate(auth);
    }
}
