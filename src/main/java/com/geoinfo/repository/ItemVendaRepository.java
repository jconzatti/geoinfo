package com.geoinfo.repository;

import com.geoinfo.entity.ItemVenda;
import com.geoinfo.entity.ItemVendaPK;
import javax.persistence.EntityManager;

public class ItemVendaRepository extends Repository<ItemVenda, ItemVendaPK> {

    public ItemVendaRepository(EntityManager manager) {
        super(manager, ItemVenda.class);
    }
    
}
