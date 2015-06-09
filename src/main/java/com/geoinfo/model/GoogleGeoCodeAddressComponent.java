package com.geoinfo.model;

public class GoogleGeoCodeAddressComponent {
    private String dsNomeLongo;
    private String dsNomeCurto;
    private String[] listaTipo;

    public GoogleGeoCodeAddressComponent() {
        this.dsNomeLongo = null;
        this.dsNomeCurto = null;
        this.listaTipo = null;
    }

    public String getDsNomeLongo() {
        return dsNomeLongo;
    }

    public void setDsNomeLongo(String dsNomeLongo) {
        this.dsNomeLongo = dsNomeLongo;
    }

    public String getDsNomeCurto() {
        return dsNomeCurto;
    }

    public void setDsNomeCurto(String dsNomeCurto) {
        this.dsNomeCurto = dsNomeCurto;
    }

    public String[] getListaTipo() {
        return listaTipo;
    }

    public void setListaTipo(String[] listaTipo) {
        this.listaTipo = listaTipo;
    }
    
}
