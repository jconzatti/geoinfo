package com.geoinfo.model;

public class GoogleGeoCodeViewPort {
    private GoogleGeoCodeLocation locSudoeste;
    private GoogleGeoCodeLocation locNordeste;

    public GoogleGeoCodeViewPort() {
        locSudoeste = null;
        locNordeste = null;
    }

    public GoogleGeoCodeLocation getLocSudoeste() {
        return locSudoeste;
    }

    public void setLocSudoeste(GoogleGeoCodeLocation locSudoeste) {
        this.locSudoeste = locSudoeste;
    }

    public GoogleGeoCodeLocation getLocNordeste() {
        return locNordeste;
    }

    public void setLocNordeste(GoogleGeoCodeLocation locNordeste) {
        this.locNordeste = locNordeste;
    }
    
}
