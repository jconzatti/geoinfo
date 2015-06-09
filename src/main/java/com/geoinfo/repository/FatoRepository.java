package com.geoinfo.repository;

import com.geoinfo.entity.Fato;
import javax.persistence.EntityManager;

public class FatoRepository  extends Repository<Fato, String>{

    public FatoRepository(EntityManager manager) {
        super(manager, Fato.class);
    }
    
}
