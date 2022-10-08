package com.peerlender.security2.service;

import com.peerlender.security2.dao.RoleRepository;
import com.peerlender.security2.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }
}
