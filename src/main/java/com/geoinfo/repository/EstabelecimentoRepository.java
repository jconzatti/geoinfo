package com.geoinfo.repository;

import com.geoinfo.entity.Estabelecimento;
import javax.persistence.EntityManager;

public class EstabelecimentoRepository extends PessoaRepository<Estabelecimento> {

    public EstabelecimentoRepository(EntityManager manager) {
        super(manager, Estabelecimento.class);
    }
    
}
