package com.peerlender.LendingEngine.domain.exeception;


//TODO: LEARN runtimeException.
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(long userId) {
        super("User with id: " +userId + " not found.");
    }
}
