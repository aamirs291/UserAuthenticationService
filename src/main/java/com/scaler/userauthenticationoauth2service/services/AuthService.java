package com.scaler.userauthenticationoauth2service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.userauthenticationoauth2service.dtos.AuthSendEmailEventDto;
import com.scaler.userauthenticationoauth2service.dtos.NotificationEventDto;
import com.scaler.userauthenticationoauth2service.dtos.SendEmailEventDto;
import com.scaler.userauthenticationoauth2service.dtos.SignupRequestDto;
import com.scaler.userauthenticationoauth2service.dtos.SignupResponseDto;
import com.scaler.userauthenticationoauth2service.exceptions.UserAlreadyPresentException;
import com.scaler.userauthenticationoauth2service.models.Role;
//import com.scaler.userauthenticationservice.models.Token;
import com.scaler.userauthenticationoauth2service.models.User;
//import com.scaler.userauthenticationservice.repos.TokenRepo;
//import com.scaler.userauthenticationservice.repos.TokenRepo;
import com.scaler.userauthenticationoauth2service.repos.RoleRepository;
import com.scaler.userauthenticationoauth2service.repos.UserRepo;
import com.scaler.userauthenticationoauth2service.services.event.AuthUserEventPublisher;

import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService implements IAuthService {

    private UserRepo userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepo;
    private KafkaTemplate<String, NotificationEventDto> kafkaTemplate;
    private ObjectMapper objectMapper;
    private AuthUserEventPublisher authUserEventPublisher;

//     public AuthService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepo, KafkaTemplate<String, AuthSendEmailEventDto> kafkaTemplate, ObjectMapper objectMapper) {
//         this.userRepo = userRepo;
// //        this.tokenRepo = tokenRepo;
//         this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//         this.roleRepo = roleRepo;
//         this.kafkaTemplate = kafkaTemplate;
//         this.objectMapper = objectMapper;
//     }

    public AuthService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepo, KafkaTemplate<String, NotificationEventDto> kafkaTemplate, AuthUserEventPublisher authUserEventPublisher) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepo = roleRepo;
        this.kafkaTemplate = kafkaTemplate;
        this.authUserEventPublisher = authUserEventPublisher;
    }

    @Override
    public User signup(String name, String email, String password, String phoneNumber, List<Role> roleList) throws JsonProcessingException {

        Optional<User> optionalUser = userRepo.findByEmailEquals(email);

        if (optionalUser.isPresent()) {
            throw new UserAlreadyPresentException("User already present. Please Login");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password)); // Bcrypt here
        user.setPhoneNumber(phoneNumber);

        List<Role> roles = new ArrayList<>();

        for (Role role : roleList) {
            Optional<Role> roleOptional = roleRepo.findByRoleName(role.getRoleName());
            if (roleOptional.isEmpty()) {
                roles.add(roleRepo.save(role));
            }
            else {
                roles.add(roleOptional.get());
            }
        }

        user.setRoles(roles);

        User emailUser = userRepo.save(user);

        SendEmailEventDto sendEmailEventDto = new SendEmailEventDto();
        sendEmailEventDto.setTo(emailUser.getEmail());
        sendEmailEventDto.setBody("Welcome to Scaler "+ emailUser.getName());
        sendEmailEventDto.setSubject("Onboarding email for " + emailUser.getName());

        // kafkaTemplate.send("sendEmailEvent", objectMapper.writeValueAsString(sendEmailEventDto));

        return emailUser;
    }

    @Override
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        
        if(userRepo.existsByEmail(signupRequestDto.getEmail())){
            throw new UserAlreadyPresentException("User already exists. Proceed to login");
        }

        User user = new User();
        user.setName(signupRequestDto.getName());
        user.setEmail(signupRequestDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(signupRequestDto.getPassword()));
        user.setPhoneNumber(signupRequestDto.getPhoneNumber());

        List<Role> roleList = new ArrayList<>();

        for(String roleName:signupRequestDto.getRoles()){
            
            Role role = roleRepo.findByRoleName(roleName).orElseGet(() -> roleRepo.save(new Role(roleName)));

            roleList.add(role);
        }

        user.setRoles(roleList);

        User savedUser = userRepo.save(user);

        authUserEventPublisher.publishUserSignUp(savedUser);

        SignupResponseDto signupResponseDto = convertToSignupResponseDto(savedUser);

        return signupResponseDto;
        
    }

    private SignupResponseDto convertToSignupResponseDto(User user){

        SignupResponseDto signupResponseDto = new SignupResponseDto();
        signupResponseDto.setUsername(user.getEmail());
        signupResponseDto.setStatus("COMPLETED");
        signupResponseDto.setMessage("User signup completed successfully");

        return signupResponseDto;
    }

//    @Override
//    public Token login(String email, String password) {
//
//        Optional<User> optionalUser = userRepo.findByEmailEquals(email);
//
//        if (optionalUser.isEmpty()) {
//            throw new UserNotSignedUpException("User account not present. Sign up first");
//        }
//
//        //Code for incorrect password
//        if (!bCryptPasswordEncoder.matches(password, optionalUser.get().getPassword())) {
//            throw new PasswordMismatchException("Incorrect password entered. Please try again");
//        }
//
//        Token token = new Token();
//        token.setUser(optionalUser.get());
//        token.setValue(RandomStringUtils.randomAlphanumeric(128));
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_MONTH, 30);
//        Date dateAfter30days = calendar.getTime();
//        token.setExpiresAt(dateAfter30days);
//
//        return tokenRepo.save(token);
//    }
//
//    @Override
//    public User validateToken(String token) {
//
//        Optional<Token> optionalToken = tokenRepo.
//                findByValueAndExpiresAtAfter(token, new Date());
//
//        if (optionalToken.isEmpty()) {
//            return null;
//        }
//
//        return optionalToken.get().getUser();
//    }
}
