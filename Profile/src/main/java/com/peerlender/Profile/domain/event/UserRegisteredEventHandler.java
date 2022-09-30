package com.peerlender.Profile.domain.event;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peerlender.Profile.domain.model.User;
import com.peerlender.Profile.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.lang.reflect.Type;
import java.util.Map;

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
        Type mapTokenType = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> jsonMap = new Gson().fromJson(userJson, mapTokenType);
        String username = jsonMap.get("username");
        User user = new User(username);
        LOGGER.info("user {} registered", user.getUsername());
        userRepository.save(user);
    }
}
