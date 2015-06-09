package com.geoinfo.util;

public enum EGoogleGeoCodeType {
    XML, JSON;

    @Override
    public String toString() {
        switch(this){
            case XML:
                return "xml";
            case JSON:
                return "json";
        }
        return this.name();
    }
    
}
