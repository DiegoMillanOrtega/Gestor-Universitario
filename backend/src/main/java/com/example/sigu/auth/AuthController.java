package com.example.sigu.auth;

import com.example.sigu.persistence.entity.Usuario;
import com.example.sigu.service.exception.AuthException;
import com.example.sigu.util.CookieUtils;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieUtils cookieUtils;

//    @Value("${google.redirect-uri}")
//    private String redirectUri;

    @PostMapping( "/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        String jwt = authService.login(request);
        cookieUtils.setTokenCookie(response, jwt);
        return ResponseEntity.ok(Map.of("message", "login existoso"));
    }

//    @GetMapping("/api/auth/oauth/login")
//    public ResponseEntity<?> getGoogleUrl() {
//        String url = flow.newAuthorizationUrl()
//                .setRedirectUri(redirectUri)
//                .build();
//        return ResponseEntity.ok(Map.of("url", url));
//    }


//    @GetMapping("/oauth2/callback")
//    public void googleCallback(@RequestParam("code") String code, HttpServletResponse response) throws IOException, GeneralSecurityException {
//        TokenResponse tokenResponse = flow.newTokenRequest(code)
//                .setRedirectUri(redirectUri)
//                .execute();
//
//        String jwt = authService.processGoogleLogin(tokenResponse);
//        setCookie(response, jwt);
//        response.sendRedirect("http://localhost:4200/dashboard");
//    }

    @PostMapping( "/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        // Obtenemos el usuario autenticado del contexto de Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthException("Authentication required");
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();

        return ResponseEntity.ok(Map.of(
                "email", usuario.getUsername(),
                "userId", usuario.getId(),
                "roles", usuario.getAuthorities()
        ));
    }
}
