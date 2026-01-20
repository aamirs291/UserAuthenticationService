package com.scaler.userauthenticationoauth2service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationEventDto {
    
    private String eventType;
    private AuthSendEmailEventDto payload;
}
