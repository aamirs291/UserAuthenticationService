package com.scaler.userauthenticationoauth2service.configs;

import com.scaler.userauthenticationoauth2service.repos.ClientRepository;
import com.scaler.userauthenticationoauth2service.repos.JpaRegisteredClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private JpaRegisteredClientRepository registeredClientRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void createClient(){
        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("productService")
                .clientSecret(bCryptPasswordEncoder.encode("password"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//				.redirectUri("http://127.0.0.1:8081/login/oauth2/code/oidc-client")
                .redirectUri("https://oauth.pstmn.io/v1/browser-callback")
//				.postLogoutRedirectUri("http://127.0.0.1:8080/")
                .postLogoutRedirectUri("https://oauth.pstmn.io/v1/browser-callback")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        registeredClientRepository.save(client);

    }

}