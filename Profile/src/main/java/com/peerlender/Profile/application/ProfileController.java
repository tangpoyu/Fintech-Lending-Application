package com.peerlender.Profile.application;

import com.peerlender.Profile.domain.model.User;
import com.peerlender.Profile.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/profile")
public class ProfileController {
    private final UserRepository userRepository;

    @GetMapping(value = "/users")
    public List<User> FindAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/user")
    public void NewUser(@RequestBody final User user){
        user.setRegisteredSince(LocalDate.now());
        userRepository.save(user);
    }

    @PutMapping("/user")
    public void UpdateUser(@RequestBody final User user){
        userRepository.save(user);
    }
}
