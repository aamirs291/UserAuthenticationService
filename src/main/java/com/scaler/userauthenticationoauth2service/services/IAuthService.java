package com.scaler.userauthenticationoauth2service.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.scaler.userauthenticationoauth2service.models.Role;
//import com.scaler.userauthenticationservice.models.Token;
import com.scaler.userauthenticationoauth2service.models.User;

import java.util.List;

public interface IAuthService {

    User signup(String name, String email, String password, String phoneNumber, List<Role> roleList) throws JsonProcessingException;

//    Token login(String email, String password);

//    User validateToken(String token);
}
