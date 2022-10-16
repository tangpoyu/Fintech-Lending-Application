package com.peerlender.LendingEngine.application;

import com.peerlender.LendingEngine.application.service.TokenValidationService;
import com.peerlender.LendingEngine.domain.entity.User;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import reactor.core.publisher.Flux;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/user")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class UserController {

    private final TokenValidationService tokenValidationService;
    private final UserRepository userRepository;

    @GetMapping(value = "/all")
    public List<User> FindUsers(HttpServletRequest request){
        tokenValidationService.ValidateTokenAndGetUser(request, "admin");
        return userRepository.findAll();
    }
}
