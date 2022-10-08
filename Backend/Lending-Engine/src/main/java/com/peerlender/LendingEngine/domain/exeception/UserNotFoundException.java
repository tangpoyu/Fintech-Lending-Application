package com.peerlender.LendingEngine.domain.exeception;


//TODO: LEARN runtimeException.
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String username) {
        super("User with id: " +username + " not found.");
    }
}
