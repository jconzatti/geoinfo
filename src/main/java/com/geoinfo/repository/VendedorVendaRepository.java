package com.geoinfo.repository;

import com.geoinfo.entity.VendedorVenda;
import com.geoinfo.entity.VendedorVendaPK;
import javax.persistence.EntityManager;

public class VendedorVendaRepository extends Repository<VendedorVenda, VendedorVendaPK> {

    public VendedorVendaRepository(EntityManager manager) {
        super(manager, VendedorVenda.class);
    }
    
}
