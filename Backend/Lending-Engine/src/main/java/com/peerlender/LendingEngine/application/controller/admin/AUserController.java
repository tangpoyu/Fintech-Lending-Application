package com.peerlender.LendingEngine.application.controller.admin;

import com.peerlender.LendingEngine.domain.entity.User;
import com.peerlender.LendingEngine.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
// import reactor.core.publisher.Flux;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "admin/userdata")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class AUserController {

    private final UserService userService;

    @GetMapping(value = "/all")
    public List<User> FindUsers(){
        return userService.findAllUser();
    }
}
