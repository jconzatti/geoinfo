package com.geoinfo.repository;

import com.geoinfo.entity.Cliente;
import javax.persistence.EntityManager;

public class ClienteRepository extends PessoaRepository<Cliente> {

    public ClienteRepository(EntityManager manager) {
        super(manager, Cliente.class);
    }
    
}
