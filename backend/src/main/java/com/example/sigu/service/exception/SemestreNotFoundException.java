package com.example.sigu.service.exception;

public class SemestreNotFoundException extends RuntimeException{
    public SemestreNotFoundException(Long semestreId){
        super(String.format("No existe semestre asociado al ID: %s", semestreId));
    }
}
