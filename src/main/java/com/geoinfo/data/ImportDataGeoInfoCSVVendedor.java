package com.geoinfo.data;

import com.geoinfo.entity.PessoaMaster;
import com.geoinfo.entity.Vendedor;
import javax.persistence.EntityManager;

public class ImportDataGeoInfoCSVVendedor extends ImportDataGeoInfoCSVPessoa{
    
    public ImportDataGeoInfoCSVVendedor(EntityManager entityManager, PessoaMaster gerente) {
        super(entityManager, gerente, Vendedor.class);
    }
    
}
