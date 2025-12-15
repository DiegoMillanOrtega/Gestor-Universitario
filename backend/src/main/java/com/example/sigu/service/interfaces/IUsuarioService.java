package com.example.sigu.service.interfaces;


import com.example.sigu.persistence.entity.Usuario;

import java.util.Optional;

public interface IUsuarioService {
    Usuario agregar(Usuario usuario);
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);

}
