package com.scaler.userauthenticationoauth2service.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

// import lombok.NoArgsConstructor;

// @NoArgsConstructor
@JsonIgnoreType
public class CustomUser implements UserDetails {

    private Long userId;
    private String username;
    private String password;
    private List<CustomGrantAuth> authorities = new ArrayList<>();

    public CustomUser(User user) {
        
        this.userId = user.getId();
        this.username = user.getEmail();
        this.password = user.getPassword();

        for (Role role : user.getRoles()) {
            this.authorities.add(new CustomGrantAuth(role.getRoleName()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Long getUserId(){
        return this.userId;
    }

}
