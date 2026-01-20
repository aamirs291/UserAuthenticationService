package com.scaler.userauthenticationoauth2service.services.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.scaler.userauthenticationoauth2service.dtos.*;
import com.scaler.userauthenticationoauth2service.models.*;

@Component
public class AuthUserEventPublisher {
    
    private KafkaTemplate<String, NotificationEventDto> kafkaTemplate;

    public AuthUserEventPublisher(KafkaTemplate<String, NotificationEventDto> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishUserSignUp(User user){

        NotificationEventDto notificationEventDto = new NotificationEventDto();
        AuthSendEmailEventDto authSendEmailEventDto = new AuthSendEmailEventDto();
        authSendEmailEventDto.setUsername(user.getEmail());
        authSendEmailEventDto.setEmail(user.getEmail());

        notificationEventDto.setEventType("AUTH_EMAIL");
        notificationEventDto.setPayload(authSendEmailEventDto);

        kafkaTemplate.send("AuthEmailEvent", user.getEmail(), notificationEventDto);

    }

}
