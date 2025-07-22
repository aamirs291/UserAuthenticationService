package com.scaler.userauthenticationoauth2service.repos;

import com.scaler.userauthenticationoauth2service.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmailEquals(String email);
}


