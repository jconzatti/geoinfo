package com.geoinfo.repository;

import com.geoinfo.entity.FormaPagamento;
import com.geoinfo.entity.FormaPagamentoPK;
import javax.persistence.EntityManager;

public class FormaPagamentoRepository extends Repository<FormaPagamento, FormaPagamentoPK> {

    public FormaPagamentoRepository(EntityManager manager) {
        super(manager, FormaPagamento.class);
    }
    
}
