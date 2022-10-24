package com.peerlender.security2.controller;

import com.peerlender.security2.configuration.CookieAuthenticationFilter;
import com.peerlender.security2.dto.UserDTO;
import com.peerlender.security2.entity.User;
import com.peerlender.security2.service.JwtService;
import com.peerlender.security2.service.NotificationService;
import com.peerlender.security2.service.UserService;
import com.peerlender.security2.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final NotificationService notificationService;



    @PostMapping("admin")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<String> forAdmin(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok("admin");
    }

    @PostMapping("user")
    @PreAuthorize("hasAnyRole('ROLE_Admin','ROLE_User')")
    public ResponseEntity<String> forUser(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userDetails.getUsername());
    }
}
