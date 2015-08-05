package com.geoinfo.repository;

import com.geoinfo.entity.LocalizacaoDestinoVenda;
import com.geoinfo.entity.Venda;
import javax.persistence.EntityManager;

public class LocalizacaoDestinoVendaRepository extends Repository<LocalizacaoDestinoVenda, Venda> {

    public LocalizacaoDestinoVendaRepository(EntityManager manager) {
        super(manager, LocalizacaoDestinoVenda.class);
    }
    
}
