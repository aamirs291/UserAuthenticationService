package com.scaler.userauthenticationoauth2service.services;

// import com.scaler.userauthenticationoauth2service.models.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import com.scaler.userauthenticationoauth2service.repos.*;
import com.scaler.userauthenticationoauth2service.models.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    //     Optional<User> user = userRepo.findByEmailWithRoles(username);

    //     if (user.isEmpty()) {
    //         throw new UsernameNotFoundException("User not found");
    //     }

    //     // UserDetails userDetails = new CustomUserDetails(user.get());

    //     // return new CustomUserDetails(user.get());
    //     return new CustomUser(user.get());
    // }

    public UserDetails loadUserByUsername(String email) {
        User dbUser = userRepo.findByEmailWithRoles(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Collection<? extends GrantedAuthority> authorities = dbUser.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                .toList();

        return org.springframework.security.core.userdetails.User
                .withUsername(dbUser.getEmail())
                .password(dbUser.getPassword())
                .authorities(authorities)
                .build();
    }
}
