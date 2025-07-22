package com.scaler.userauthenticationoauth2service.models;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class User extends BaseModel {

    private String name;
    private String password;
    private String email;
    private String phoneNumber;
//    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
}
