package com.peerlender.security.user.controller;

import com.peerlender.security.user.model.UserDetailsImpl;
import com.peerlender.security.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("security")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("user/register")
    public String register(@RequestBody UserDetailsImpl userDetails){
       String token = userService.register(userDetails);
       return token;
    }

    @PostMapping("user/login")
    public String login(@RequestBody UserDetailsImpl userDetails){
        String token = userService.login(userDetails);
        return token;
    }
}
