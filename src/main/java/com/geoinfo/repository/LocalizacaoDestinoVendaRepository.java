package com.geoinfo.repository;

import com.geoinfo.entity.LocalizacaoDestinoVenda;
import com.geoinfo.entity.LocalizacaoDestinoVendaPK;
import javax.persistence.EntityManager;

public class LocalizacaoDestinoVendaRepository extends Repository<LocalizacaoDestinoVenda, LocalizacaoDestinoVendaPK> {

    public LocalizacaoDestinoVendaRepository(EntityManager manager) {
        super(manager, LocalizacaoDestinoVenda.class);
    }
    
}
