package com.peerlender.security2.dto;

import lombok.Getter;

@Getter
public class UserDTO {

    private String username;
    private String password;
    private String firstName, lastName;
    private int age;
    private String occupation;

    public void setPassword(String password) {
        this.password = password;
    }
}
