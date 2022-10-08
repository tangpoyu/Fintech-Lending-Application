package com.peerlender.securityapp.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.peerlender.securityapp.user.dto.UserDTO;
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
            UserDTO userDTO= new ObjectMapper().readValue(inputStreamBytes, UserDTO.class);
            User user = new User(userDTO.getUsername(),userDTO.getPassword());
            userRepository.save(user);
            notificationService.SendMessage(userDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/validate")
    public String ValidateTokenAndGetUsername(@RequestHeader("Authorization") String token){
        return userRepository.findById(token).orElseThrow(() -> new UserNotFoundException()).getUsername();
    }
}
