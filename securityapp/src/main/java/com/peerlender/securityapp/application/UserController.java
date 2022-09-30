package com.peerlender.securityapp.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peerlender.securityapp.user.exception.UserNotFoundException;
import com.peerlender.securityapp.user.model.User;
import com.peerlender.securityapp.user.model.repository.UserRepository;
import com.peerlender.securityapp.user.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @PostMapping("register")
    public void Register(HttpServletRequest req){
        try {
            byte[] inputStreamBytes = StreamUtils.copyToByteArray(req.getInputStream());
            Map<String, String> jsonRequest = new ObjectMapper().readValue(inputStreamBytes, Map.class);
            String requestBodyJsonString = jsonRequest.get("body");
            String username = jsonRequest.get("username");
            String password = jsonRequest.get("password");
            User user = new User(username,password);
            userRepository.save(user);
            notificationService.SendMessage(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/validate")
    public String ValidateTokenAndGetUsername(@RequestHeader("Authorization") String token){
        return userRepository.findById(token).orElseThrow(() -> new UserNotFoundException()).getUsername();
    }
}
