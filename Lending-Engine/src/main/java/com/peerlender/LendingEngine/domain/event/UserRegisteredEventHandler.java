package com.peerlender.LendingEngine.domain.event;

import com.google.gson.Gson;
import com.peerlender.LendingEngine.domain.model.User;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredEventHandler {

    private Logger LOGGER = LoggerFactory.getLogger(UserRegisteredEventHandler.class);
    private static final Gson GSON = new Gson();
    private String methodName = "HandleUserRegistration";
    private final UserRepository userRepository;

    public UserRegisteredEventHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getMethodName() {
        return methodName;
    }

    public void HandleUserRegistration(String userJson){
        User user = GSON.fromJson(userJson, User.class);
        LOGGER.info("user {} registered", user.getUsername());
        userRepository.save(user);
    }
}