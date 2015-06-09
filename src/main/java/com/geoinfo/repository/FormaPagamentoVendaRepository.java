package com.geoinfo.repository;

import com.geoinfo.entity.FormaPagamentoVenda;
import com.geoinfo.entity.FormaPagamentoVendaPK;
import javax.persistence.EntityManager;

public class FormaPagamentoVendaRepository extends Repository<FormaPagamentoVenda, FormaPagamentoVendaPK>{

    public FormaPagamentoVendaRepository(EntityManager manager) {
        super(manager, FormaPagamentoVenda.class);
    }
    
}
