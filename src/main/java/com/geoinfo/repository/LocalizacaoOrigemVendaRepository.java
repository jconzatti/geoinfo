package com.geoinfo.repository;

import com.geoinfo.entity.LocalizacaoOrigemVenda;
import com.geoinfo.entity.LocalizacaoOrigemVendaPK;
import javax.persistence.EntityManager;

public class LocalizacaoOrigemVendaRepository extends Repository<LocalizacaoOrigemVenda, LocalizacaoOrigemVendaPK>{

    public LocalizacaoOrigemVendaRepository(EntityManager manager) {
        super(manager, LocalizacaoOrigemVenda.class);
    }
    
}
