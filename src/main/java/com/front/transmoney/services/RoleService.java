package com.fil.transfert.services;

import com.fil.transfert.model.Role;
import com.fil.transfert.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    public List<Role> findAll(){
        return roleRepository.findAll();
    }
}
