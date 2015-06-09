package com.geoinfo.repository;

import com.geoinfo.entity.Vendedor;
import javax.persistence.EntityManager;

public class VendedorRepository extends PessoaRepository<Vendedor> {

    public VendedorRepository(EntityManager manager) {
        super(manager, Vendedor.class);
    }
    
}
