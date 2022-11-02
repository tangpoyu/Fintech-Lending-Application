package com.peerlender.LendingEngine.application.event;

import com.google.gson.Gson;
import com.peerlender.LendingEngine.domain.entity.Balance;
import com.peerlender.LendingEngine.domain.entity.User;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
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

    public void HandleUserRegistration(byte[] eventByte) throws JSONException {
        JSONObject event = new JSONObject(new String(eventByte));
        String type = event.get("type").toString();
        if("REGISTER".equals(type)) {
            System.out.println(event);
            User user = GSON.fromJson(event.get("details").toString(), User.class);
            user.setBalance(new Balance());
            LOGGER.info("user {} registered", user.getUsername());
            userRepository.save(user);
        }else if("LOGIN".equals(type)) {
            JSONObject details = (JSONObject) event.get("details");
            if(!details.isNull("register_method")) {
                String username = details.get("username").toString();
                User user = userRepository.findById(username).orElse(null);
                if(user == null) {
                    user = new User();
                    user.setUsername(username);
                    user.setBalance(new Balance());
                    LOGGER.info("user {} registered", user.getUsername());
                    userRepository.save(user);
                }
            }
        }

    }
}
