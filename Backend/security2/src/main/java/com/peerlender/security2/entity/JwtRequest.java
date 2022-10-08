package com.peerlender.security2.entity;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
