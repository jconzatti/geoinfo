package com.geoinfo.repository;

import com.geoinfo.entity.Administrador;
import javax.persistence.EntityManager;

public class AdministradorRepository extends PessoaRepository<Administrador>{

    public AdministradorRepository(EntityManager manager) {
        super(manager, Administrador.class);
    }
    
}
