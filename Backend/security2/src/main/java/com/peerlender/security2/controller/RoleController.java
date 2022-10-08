package com.peerlender.security2.controller;

import com.peerlender.security2.entity.Role;
import com.peerlender.security2.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("role")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("createNewRole")
    public Role createNewRole(@RequestBody Role role){
        return roleService.createNewRole(role);
    }
}
