package com.peerlender.LendingEngine.application;

import com.peerlender.LendingEngine.application.service.TokenValidationService;
import com.peerlender.LendingEngine.domain.model.User;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final TokenValidationService tokenValidationService;
    private final UserRepository userRepository;

    @GetMapping(value = "/all")
    public List<User> FindUsers(HttpServletRequest request){
        tokenValidationService.ValidateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        return userRepository.findAll();
    }
}
