package com.example.sigu.service.implementation;

import com.example.sigu.persistence.entity.Usuario;
import com.example.sigu.persistence.repository.IUsuarioRepository;
import com.example.sigu.service.exception.UsuarioNotFoundException;
import com.example.sigu.service.interfaces.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioRepository iUsuarioRepository;

    @Override
    public Usuario agregar(Usuario usuario) {
        iUsuarioRepository.save(usuario);
        return usuario;
    }

    @Override
    public Usuario findById(Long id) {
        return iUsuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("No existe usuario asociado al ID: " + id));
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return iUsuarioRepository.findByUsername(username);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return iUsuarioRepository.findByEmail(email);
    }
}
