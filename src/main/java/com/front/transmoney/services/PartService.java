package com.fil.transfert.services;

import com.fil.transfert.model.Partenaire;
import com.fil.transfert.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartService {
    @Autowired
    PartRepository partRepository;

    public Partenaire save(Partenaire partenaire){

        return partRepository.save(partenaire);
    }

    public List<Partenaire> findAll(){
        return partRepository.findAll();
    }


}
