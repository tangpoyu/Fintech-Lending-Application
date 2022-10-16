package com.peerlender.security2.controller;

import com.peerlender.security2.configuration.CookieAuthenticationFilter;
import com.peerlender.security2.dto.UserDTO;
import com.peerlender.security2.entity.JwtRequest;
import com.peerlender.security2.entity.JwtResponse;
import com.peerlender.security2.entity.User;
import com.peerlender.security2.service.JwtService;
import com.peerlender.security2.service.NotificationService;
import com.peerlender.security2.service.UserService;
import com.peerlender.security2.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

@RestController
@CrossOrigin
@AllArgsConstructor
public class JwtController {

    private JwtUtil jwtUtil;
    private final UserService userService;
    private final NotificationService notificationService;

//    @PostMapping("authenticate")
//    public ResponseEntity<JwtResponse> createJwtToken(@RequestBody JwtRequest jwtRequest, HttpServletResponse response) throws Exception{
//        JwtResponse jwtResponse = jwtService.createJwtToken(jwtRequest);
//        Cookie cookie = new Cookie(CookieAuthenticationFilter.COOKIE_NAME, jwtResponse.getJwtToken());
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
//        cookie.setMaxAge(Duration.of(1, ChronoUnit.DAYS).toSecondsPart());
//        cookie.setPath("/");
//        response.addCookie(cookie);
//        return ResponseEntity.ok(jwtResponse);
//    }

    @PostMapping("register")
    public ResponseEntity<UserDTO> registerNewUser(@RequestBody UserDTO userDTO, HttpServletResponse response){
        User user = new User(userDTO.getUsername(), userDTO.getPassword());
        userService.registerNewUser(user);
        String token = jwtUtil.generateToken(user.getUsername());
        Cookie cookie = new Cookie(CookieAuthenticationFilter.COOKIE_NAME,token);
        cookie.setHttpOnly(true);
        // cookie.setSecure(true);
        cookie.setMaxAge(60*60*24);
        cookie.setPath("/login");
        response.addCookie(cookie);
        notificationService.SendMessage(userDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("login")
    public ResponseEntity<UserDetails> createJwtToken(@AuthenticationPrincipal UserDetails userDetails, HttpServletResponse response) throws Exception{
        String token = jwtUtil.generateToken(userDetails.getUsername());
        Cookie cookie = new Cookie(CookieAuthenticationFilter.COOKIE_NAME, token);
        cookie.setHttpOnly(true);
       // cookie.setSecure(true);
        cookie.setMaxAge(60*60*24);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(userDetails);
    }

    @PostMapping("logout")
    public ResponseEntity<Void> signOut(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        Optional<Cookie> authCookie = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0])).filter(
                cookie -> CookieAuthenticationFilter.COOKIE_NAME.equals(cookie.getName()))
                .findFirst();
        authCookie.ifPresent(cookie -> cookie.setMaxAge(0));

        return ResponseEntity.noContent().build();
    }

    @PostMapping("authenticate")
    public String ValidateTokenAndGetUsername(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails.getUsername();
    }

}
