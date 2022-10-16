package com.peerlender.security2.service;

import com.google.gson.Gson;
import com.peerlender.security2.dto.UserDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    private final RabbitTemplate rabbitTemplate;
    private final static Gson GSON = new Gson();
    private String topic = "myUserRegisteredTopic";
    private String route = "user.registered";


    public NotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void SendMessage(UserDTO userDTO){
        userDTO.setPassword(null);
        String userJson = GSON.toJson(userDTO);
        rabbitTemplate.convertAndSend(topic,route,userJson);
    }
}

