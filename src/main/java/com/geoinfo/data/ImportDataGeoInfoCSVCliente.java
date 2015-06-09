package com.geoinfo.data;

import com.geoinfo.entity.Cliente;
import com.geoinfo.entity.PessoaMaster;
import javax.persistence.EntityManager;

public class ImportDataGeoInfoCSVCliente extends ImportDataGeoInfoCSVPessoa{
    
    public ImportDataGeoInfoCSVCliente(EntityManager entityManager, PessoaMaster gerente) {
        super(entityManager, gerente, Cliente.class);
    }
    
}
