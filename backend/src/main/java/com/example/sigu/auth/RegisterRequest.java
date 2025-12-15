package com.example.sigu.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "El usuario es obligatorio")
        String username,


        @Email(message = "Email inválido")
        @NotBlank(message = "La email es obligatoria")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "Minimo 8 caracteres")
        String password
) {
}
