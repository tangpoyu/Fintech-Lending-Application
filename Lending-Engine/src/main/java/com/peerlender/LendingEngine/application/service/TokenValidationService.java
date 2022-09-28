package com.peerlender.LendingEngine.application.service;

import com.peerlender.LendingEngine.domain.exeception.UserNotFoundException;
import com.peerlender.LendingEngine.domain.model.User;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TokenValidationService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String securityContextBaseUrl;

    public TokenValidationService(final UserRepository userRepository,
                                  @Value("${security.baseUrl}") final String securityContextBaseUrl) {
        this.userRepository = userRepository;
        this.securityContextBaseUrl = securityContextBaseUrl;
    }

    public User ValidateTokenAndGetUser(final String token){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<String> res = restTemplate.exchange(
                securityContextBaseUrl + "/user/validate",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        if(res.getStatusCode().equals(HttpStatus.OK)){
            String username = res.getBody();
            return userRepository.findById(username)
                    .orElseThrow(() -> new UserNotFoundException(username));
        }

        throw new RuntimeException("Invalid token");
    }
}
