package com.geoinfo.data;

import com.geoinfo.entity.PessoaMaster;
import com.geoinfo.model.GeoInfoLogNode;
import java.util.List;
import javax.persistence.EntityManager;

public abstract class ImportDataGeoInfo {
    private EntityManager entityManager;
    private PessoaMaster gerente;
    private List<GeoInfoLogNode> listaGeoInfoLogNode;

    public ImportDataGeoInfo(EntityManager entityManager, PessoaMaster gerente) {
        this.entityManager = entityManager;
        this.gerente = gerente;
        this.listaGeoInfoLogNode = null;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PessoaMaster getGerente() {
        return gerente;
    }

    public void setGerente(PessoaMaster gerente) {
        this.gerente = gerente;
    }

    public List<GeoInfoLogNode> getListaGeoInfoLogNode() {
        return listaGeoInfoLogNode;
    }

    public void setListaGeoInfoLogNode(List<GeoInfoLogNode> listaGeoInfoLogNode) {
        this.listaGeoInfoLogNode = listaGeoInfoLogNode;
    }
    
}
