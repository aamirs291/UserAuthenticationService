package com.scaler.userauthenticationoauth2service.services;

import com.scaler.userauthenticationoauth2service.models.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import com.scaler.userauthenticationoauth2service.repos.*;
import com.scaler.userauthenticationoauth2service.models.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepo.findByEmailEquals(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

//        UserDetails userDetails = new CustomUserDetails(user.get());

        return new CustomUserDetails(user.get());
    }
}
