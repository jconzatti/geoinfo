package com.geoinfo.model;

public class GoogleGeoCodeLocation {
    private String dsLatitude;
    private String dsLongitude;

    public GoogleGeoCodeLocation() {
        dsLatitude = null;
        dsLongitude = null;
    }

    public String getDsLatitude() {
        return dsLatitude;
    }

    public void setDsLatitude(String dsLatitude) {
        this.dsLatitude = dsLatitude;
    }

    public String getDsLongitude() {
        return dsLongitude;
    }

    public void setDsLongitude(String dsLongitude) {
        this.dsLongitude = dsLongitude;
    }
    
    
}
