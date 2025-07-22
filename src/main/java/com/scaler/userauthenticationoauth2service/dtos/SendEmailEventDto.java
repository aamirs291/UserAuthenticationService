package com.scaler.userauthenticationoauth2service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailEventDto {
    private String emailBody;
    private String emailSubject;
    private String emailTo;
}
