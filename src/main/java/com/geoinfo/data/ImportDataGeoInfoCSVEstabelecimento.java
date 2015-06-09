package com.geoinfo.data;

import com.geoinfo.entity.Estabelecimento;
import com.geoinfo.entity.PessoaMaster;
import javax.persistence.EntityManager;

public class ImportDataGeoInfoCSVEstabelecimento extends ImportDataGeoInfoCSVPessoa{
   
    public ImportDataGeoInfoCSVEstabelecimento(EntityManager entityManager, PessoaMaster gerente) {
        super(entityManager, gerente, Estabelecimento.class);
    }
    
}
