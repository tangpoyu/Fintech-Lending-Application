package com.peerlender.LendingEngine.application.event;

import com.google.gson.Gson;
import com.peerlender.LendingEngine.domain.entity.Balance;
import com.peerlender.LendingEngine.domain.entity.User;
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
        user.setBalance(new Balance());
        LOGGER.info("user {} registered", user.getUsername());
        userRepository.save(user);
    }
}
