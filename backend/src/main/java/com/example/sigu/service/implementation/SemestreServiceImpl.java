package com.example.sigu.service.implementation;

import com.example.sigu.persistence.entity.Semestre;
import com.example.sigu.persistence.repository.ISemestreRepository;
import com.example.sigu.service.interfaces.ISemestreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SemestreServiceImpl implements ISemestreService {

    @Autowired
    private ISemestreRepository semestreRepository;


    @Override
    public List<Semestre> findAll() {
        return semestreRepository.findAll();
    }

    @Override
    public Optional<Semestre> findById(Long id) {
        return semestreRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Semestre semestre = semestreRepository.findById(id).get();
        semestreRepository.delete(semestre);
    }

    @Override
    public Semestre save(Semestre semestre) {
        return null;
    }
}
