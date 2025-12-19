package com.example.sigu.presentation.controller;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GoogleAuthController {

    private final GoogleAuthorizationCodeFlow flow;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    // 1. Entra aquí para loguearte: http://localhost:8080/api/oauth/login
    @GetMapping("/api/oauth/login")
    public void login(HttpServletResponse response) throws IOException {
        String url = flow.newAuthorizationUrl()
                .setRedirectUri(redirectUri)
                .build();
        response.sendRedirect(url);
    }

    // 2. Google te enviará aquí automáticamente
    @GetMapping("/oauth2/callback")
    public String callback(@RequestParam("code") String code) throws IOException {
        TokenResponse tokenResponse = flow.newTokenRequest(code)
                .setRedirectUri(redirectUri)
                .execute();

        // Guardamos el token con el ID "user-personal"
        Credential credential = flow.createAndStoreCredential(tokenResponse, "user");

        log.info("Credential created {}", credential);

        return "Autorización completada. Ya puedes usar Google Tasks/Drive.";
    }
}
