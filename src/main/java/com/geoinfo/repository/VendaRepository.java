package com.geoinfo.repository;

import com.geoinfo.entity.Venda;
import com.geoinfo.entity.VendaPK;
import javax.persistence.EntityManager;

public class VendaRepository extends Repository<Venda, VendaPK> {

    public VendaRepository(EntityManager manager) {
        super(manager, Venda.class);
    }
    
}
