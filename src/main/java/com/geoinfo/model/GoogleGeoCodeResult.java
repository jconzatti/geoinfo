package com.geoinfo.model;

public class GoogleGeoCodeResult {
    private String[] listaTipo;
    private String dsEndereco;
    private GoogleGeoCodeAddressComponent[] listaComponenteEndereco;
    private GoogleGeoCodeGeometry geometry;
    private String cdRegiao;

    public GoogleGeoCodeResult() {
        listaTipo = null;
        dsEndereco = null;
        listaComponenteEndereco = null;
        geometry = null;
    }

    public String[] getListaTipo() {
        return listaTipo;
    }

    public void setListaTipo(String[] listaTipo) {
        this.listaTipo = listaTipo;
    }

    public String getDsEndereco() {
        return dsEndereco;
    }

    public void setDsEndereco(String dsEndereco) {
        this.dsEndereco = dsEndereco;
    }

    public GoogleGeoCodeAddressComponent[] getListaComponenteEndereco() {
        return listaComponenteEndereco;
    }

    public void setListaComponenteEndereco(GoogleGeoCodeAddressComponent[] listaComponenteEndereco) {
        this.listaComponenteEndereco = listaComponenteEndereco;
    }

    public GoogleGeoCodeGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(GoogleGeoCodeGeometry geometry) {
        this.geometry = geometry;
    }

    public String getCdRegiao() {
        return cdRegiao;
    }

    public void setCdRegiao(String cdRegiao) {
        this.cdRegiao = cdRegiao;
    }
    
    
    
}
