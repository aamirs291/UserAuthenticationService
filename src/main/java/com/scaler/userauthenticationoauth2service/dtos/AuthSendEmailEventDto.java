package com.scaler.userauthenticationoauth2service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthSendEmailEventDto {

    private String username;
    private String email;
    
}
