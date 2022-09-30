package com.peerlender.securityapp.user.service;

import com.google.gson.Gson;
import com.peerlender.securityapp.user.model.User;
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

    public void SendMessage(User user){
        String userJson = GSON.toJson(user);
        rabbitTemplate.convertAndSend(topic,route,userJson);
    }
}