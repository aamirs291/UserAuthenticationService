package com.scaler.userauthenticationoauth2service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
}
