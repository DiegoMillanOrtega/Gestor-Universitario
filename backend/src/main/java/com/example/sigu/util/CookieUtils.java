package com.example.sigu.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CookieUtils {

    private final Duration expiration = Duration.ofDays(5);

    public void setTokenCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from("auth_token", token)
                .httpOnly(true)
                .secure(false) //TODO: Cambiar a true en producción (HTTPS)
                .path("/")
                .maxAge(expiration.getSeconds()) // maxAge usa segundos
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void deleteTokenCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("auth_token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0) // Expira inmediatamente
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
