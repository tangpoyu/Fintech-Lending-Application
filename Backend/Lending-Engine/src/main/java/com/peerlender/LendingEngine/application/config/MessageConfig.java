package com.peerlender.LendingEngine.application.config;


import com.peerlender.LendingEngine.application.event.UserRegisteredEventHandler;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

    public static final String QUEUE_NAME = "oauth2-.REGISTER";

    @Bean
    public MessageListenerAdapter UserRegisteredEventListener(UserRegisteredEventHandler userRegisteredEventHandler){
        return new MessageListenerAdapter(userRegisteredEventHandler,userRegisteredEventHandler.getMethodName());
    }

    @Bean
    public SimpleMessageListenerContainer Container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter messageListenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(messageListenerAdapter);
        return container;
    }


    // ==========================  create queue and bind topic ================================================================

    public static final String TOPIC = "myUserRegisteredTopic";

    @Bean
    public Queue UserRegisteredQueue(){
        System.out.println("create register queue in spring bean repository.");
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange UserRegisteredTopic(){
        System.out.println("create register topic in spring bean repository.");
        return new TopicExchange(TOPIC);
    }

    @Bean
    public Binding Binding(Queue queue, TopicExchange topicExchange){
        System.out.println("create binding in spring bean repository.");
        // the message sent to TopicExchange can be distributed to all queue of TopicExchange which name starts with user.
        return BindingBuilder.bind(queue).to(topicExchange).with("KK.EVENT.CLIENT.oauth2.SUCCESS.oauth2-demo-pkce-client.REGISTER");
    }

}