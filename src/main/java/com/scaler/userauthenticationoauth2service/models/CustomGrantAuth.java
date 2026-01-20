package com.scaler.userauthenticationoauth2service.models;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import lombok.NoArgsConstructor;

// @NoArgsConstructor
@JsonIgnoreType
public class CustomGrantAuth implements GrantedAuthority {
    
    private String authority;

    public CustomGrantAuth(String roleName) {
        this.authority = roleName;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
