package com.example.sigu.service.implementation;

import com.example.sigu.persistence.entity.Usuario;
import com.example.sigu.persistence.repository.IUsuarioRepository;
import com.example.sigu.service.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Override
    public Usuario agregar(Usuario usuario) {
        return iUsuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return iUsuarioRepository.findById(id);
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
