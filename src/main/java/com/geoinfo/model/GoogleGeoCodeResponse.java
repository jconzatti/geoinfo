package com.geoinfo.model;

public class GoogleGeoCodeResponse {
    private String dsResposta;
    private GoogleGeoCodeResult[] listaResultado;

    public GoogleGeoCodeResponse() {
        dsResposta = null;
        listaResultado = null;
    }

    public String getDsResposta() {
        return dsResposta;
    }

    public void setDsResposta(String dsResposta) {
        this.dsResposta = dsResposta;
    }

    public GoogleGeoCodeResult[] getListaResultado() {
        return listaResultado;
    }

    public void setListaResultado(GoogleGeoCodeResult[] listaResultado) {
        this.listaResultado = listaResultado;
    }
    
}
