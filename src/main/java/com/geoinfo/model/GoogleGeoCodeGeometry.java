package com.geoinfo.model;

public class GoogleGeoCodeGeometry {
    private GoogleGeoCodeLocation locCentro;
    private String dsTipoLocalizacao;
    private GoogleGeoCodeViewPort viewport;
    private GoogleGeoCodeViewPort bounds;

    public GoogleGeoCodeGeometry() {
        locCentro = null;
        dsTipoLocalizacao = null;
        viewport = null;
        bounds = null;
    }

    public GoogleGeoCodeLocation getLocCentro() {
        return locCentro;
    }

    public void setLocCentro(GoogleGeoCodeLocation locCentro) {
        this.locCentro = locCentro;
    }

    public String getDsTipoLocalizacao() {
        return dsTipoLocalizacao;
    }

    public void setDsTipoLocalizacao(String dsTipoLocalizacao) {
        this.dsTipoLocalizacao = dsTipoLocalizacao;
    }

    public GoogleGeoCodeViewPort getViewport() {
        return viewport;
    }

    public void setViewport(GoogleGeoCodeViewPort viewport) {
        this.viewport = viewport;
    }

    public GoogleGeoCodeViewPort getBounds() {
        return bounds;
    }

    public void setBounds(GoogleGeoCodeViewPort bounds) {
        this.bounds = bounds;
    }
    
    
}
