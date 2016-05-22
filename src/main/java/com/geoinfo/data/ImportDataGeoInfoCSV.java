package com.geoinfo.data;

import com.geoinfo.entity.PessoaMaster;
import javax.persistence.EntityManager;

public abstract class ImportDataGeoInfoCSV extends ImportDataGeoInfo{

    public ImportDataGeoInfoCSV(EntityManager entityManager, PessoaMaster gerente) {
        super(entityManager, gerente);
    }
    
    public abstract boolean importar(long nrLinha, String dsLinha, boolean inUltima);
}
