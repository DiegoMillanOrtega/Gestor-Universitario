package com.example.sigu.auth;

import com.example.sigu.config.security.jwt.JwtService;
import com.example.sigu.persistence.entity.Usuario;
import com.example.sigu.service.interfaces.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final IUsuarioService usuarioService;

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

}
