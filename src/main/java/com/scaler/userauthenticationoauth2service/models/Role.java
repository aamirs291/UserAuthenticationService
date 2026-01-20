package com.scaler.userauthenticationoauth2service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Role extends BaseModel {

    public Role(String roleName){
        this.roleName = roleName;
    }

    @Column(nullable = false, unique = true)
    private String roleName;
}
