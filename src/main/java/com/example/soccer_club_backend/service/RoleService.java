package com.example.soccer_club_backend.service;

import com.example.soccer_club_backend.models.Role;
import com.example.soccer_club_backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        Optional<Role> roleOptional = roleRepository.findByName("ROLE_USER");
        return roleOptional.orElseThrow(() -> new RuntimeException("Role not found: ROLE_USER"));
    }
}
