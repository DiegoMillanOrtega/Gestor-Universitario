package com.example.sigu.auth;

import com.example.sigu.config.security.jwt.JwtService;
import com.example.sigu.persistence.entity.Usuario;
import com.example.sigu.service.interfaces.IUsuarioService;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final IUsuarioService usuarioService;
    private final GoogleIdTokenVerifier verifier;

    public String processGoogleLogin(TokenResponse tokenResponse) throws GeneralSecurityException, IOException {
        GoogleIdToken.Payload payload = getPayloadFromToken(tokenResponse);

        if (!payload.getEmailVerified()) throw new RuntimeException("La cuenta de Google no tiene el email verificado");
        String email = payload.getEmail();

        Usuario usuario = usuarioService.findByEmail(email)
                .orElseGet(() -> registraNuevoUsuarioGoogle(payload));

        return jwtService.generateToken(usuario.getUsername());
    }

    private Usuario registraNuevoUsuarioGoogle(GoogleIdToken.Payload payload) {
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setEmail(payload.getEmail());
        usuarioNuevo.setUsername((String) payload.get("name"));
        usuarioNuevo.setPassword(encoder.encode(payload.getEmail()));
        return usuarioService.agregar(usuarioNuevo);
    }

    public String login(LoginRequest request) {
        Usuario usuario = usuarioService.findByEmail(request.email()).orElseThrow();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                usuario.getUsername(), request.password()));

        return jwtService.generateToken(usuario.getUsername());
    }

    public String register(RegisterRequest request) {

        Usuario usuarioRegistrado = new Usuario();
        usuarioRegistrado.setUsername(request.username());
        usuarioRegistrado.setEmail(request.email());
        usuarioRegistrado.setPassword(encoder.encode(request.password()));

        usuarioService.agregar(usuarioRegistrado);

        return jwtService.generateToken(usuarioRegistrado.getUsername());
    }

    public String getEmailFromToken(TokenResponse tokenResponse) throws GeneralSecurityException, IOException {
        return getPayloadFromToken(tokenResponse).getEmail();
    }

    private GoogleIdToken.Payload getPayloadFromToken(TokenResponse tokenResponse) throws GeneralSecurityException, IOException {
        String idTokenString = (String) tokenResponse.get("id_token");
        if (idTokenString == null) throw new AuthException("Invalid id_token");
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken == null) throw new RuntimeException("Token de Google no válido o manipulado");
        return idToken.getPayload();
    }
}
