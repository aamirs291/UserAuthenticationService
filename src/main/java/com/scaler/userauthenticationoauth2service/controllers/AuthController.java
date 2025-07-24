package com.scaler.userauthenticationoauth2service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.scaler.userauthenticationoauth2service.dtos.SignupRequestDto;
import com.scaler.userauthenticationoauth2service.dtos.UserDto;
import com.scaler.userauthenticationoauth2service.models.Role;
import com.scaler.userauthenticationoauth2service.models.User;
import com.scaler.userauthenticationoauth2service.services.IAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupRequestDto signupRequestDto) throws JsonProcessingException {
        List<Role> roleList = new ArrayList<>();
        Role role = new Role();
        role.setRoleName(signupRequestDto.getRole());
        roleList.add(role);
        User user = authService.signup(signupRequestDto.getName(), signupRequestDto.getEmail(), signupRequestDto.getPassword(), signupRequestDto.getPhoneNumber(), roleList);
        return convertToDto(user);
    }

    private UserDto convertToDto(User user){

        if (user==null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
}
