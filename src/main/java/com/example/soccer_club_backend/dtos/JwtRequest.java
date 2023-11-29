package com.example.soccer_club_backend.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
