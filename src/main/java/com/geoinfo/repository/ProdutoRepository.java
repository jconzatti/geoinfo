package com.geoinfo.repository;

import com.geoinfo.entity.Produto;
import com.geoinfo.entity.ProdutoPK;
import javax.persistence.EntityManager;

public class ProdutoRepository extends Repository<Produto, ProdutoPK> {

    public ProdutoRepository(EntityManager manager) {
        super(manager, Produto.class);
    }
    
    
}
