package com.peerlender.security2.service;

import com.peerlender.security2.dao.RoleRepository;
import com.peerlender.security2.dao.UserRepository;
import com.peerlender.security2.entity.Role;
import com.peerlender.security2.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRole() == null){
            Role role = roleRepository.findById("User").orElseThrow(() -> new RuntimeException());
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRole(roles);
        }
        return userRepository.save(user);
    }
}
