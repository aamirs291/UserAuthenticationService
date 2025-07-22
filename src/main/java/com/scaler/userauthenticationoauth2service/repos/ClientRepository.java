package com.scaler.userauthenticationoauth2service.repos;

import java.util.Optional;

//import sample.jpa.entity.client.Client;
import com.scaler.userauthenticationoauth2service.models.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
	Optional<Client> findByClientId(String clientId);
}