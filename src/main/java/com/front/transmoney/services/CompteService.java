package com.fil.transfert.services;

import com.fil.transfert.model.Compte;
import com.fil.transfert.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompteService {
    @Autowired
    CompteRepository compteRepository;

    public Compte save(Compte compte){

        return compteRepository.save(compte);
    }

    public List<Compte> findAll(){
        return compteRepository.findAll();
    }

     /*   public Optional<Compte> findOne(){
            return compteRepository.findOne(int );

        }*/

}
