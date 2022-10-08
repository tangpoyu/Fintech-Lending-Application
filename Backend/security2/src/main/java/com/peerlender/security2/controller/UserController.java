package com.peerlender.security2.controller;

import com.peerlender.security2.entity.User;
import com.peerlender.security2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;



    @PostMapping("register")
    public User registerNewUser(@RequestBody User user){
        return userService.registerNewUser(user);
    }

    @GetMapping("admin")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public String forAdmin(){
        return ("this is for admin");
    }

    @GetMapping("user")
    @PreAuthorize("hasAnyRole('ROLE_Admin','ROLE_User')")
    public String forUser(){
        return ("this is for user");
    }
}
