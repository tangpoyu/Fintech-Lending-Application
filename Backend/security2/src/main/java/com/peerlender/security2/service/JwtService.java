package com.peerlender.security2.service;

import com.peerlender.security2.dao.UserRepository;
import com.peerlender.security2.entity.JwtRequest;
import com.peerlender.security2.entity.JwtResponse;
import com.peerlender.security2.entity.User;
import com.peerlender.security2.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


// 4
@Service
public class JwtService implements UserDetailsService {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

//    public String  createJwtToken(UserDetails userDetails) throws Exception {
////        String username = userDetails.getUsername();
////        String password = userDetails.getPassword();
////        authenticate(username, password);
////        UserDetails userDetails = loadUserByUsername(username);
//        String token = jwtUtil.generateToken(userDetails);
//        return token;
////        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username isn't valid"));
////        return new JwtResponse(user, token);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username isn't valid"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user));
    }

    public UserDetails loadUserByToken(String principal) {
        String username = jwtUtil.getUserNameFromToken(principal);
        return loadUserByUsername(username);
    }

    private Set getAuthorities(User user) {
        Set authorities = new HashSet();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(("ROLE_" + role.getRoleName())));
        });
        return authorities;
    }

    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User is disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad Credentials from user");
        }

    }


}
