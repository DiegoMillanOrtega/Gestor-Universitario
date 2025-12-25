package com.example.sigu.presentation.controller;

import com.example.sigu.auth.AuthService;
import com.example.sigu.util.CookieUtils;
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
import java.security.GeneralSecurityException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GoogleAuthController {

    private final GoogleAuthorizationCodeFlow flow;
    private final AuthService authService;
    private final CookieUtils cookieUtils;

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
    public void callback(@RequestParam("code") String code, HttpServletResponse response) throws IOException, GeneralSecurityException {
        TokenResponse tokenResponse = flow.newTokenRequest(code)
                .setRedirectUri(redirectUri)
                .execute();

        String myJwt = authService.processGoogleLogin(tokenResponse);
        String email = authService.getEmailFromToken(tokenResponse);
        Credential credential = flow.createAndStoreCredential(tokenResponse, email);
        log.info("Credential created {}", credential);

        cookieUtils.setTokenCookie(response, myJwt);
        response.sendRedirect("http://localhost:4200/dashboard");
    }

}
