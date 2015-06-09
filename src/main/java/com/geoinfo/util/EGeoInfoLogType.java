package com.geoinfo.util;

public enum EGeoInfoLogType {
    LOG_INFO,
    LOG_WARN,
    LOG_ERROR;
    
    @Override
    public String toString(){
        switch(this){
            case LOG_INFO:
                return "Informação";
            case LOG_WARN:
                return "Advertência";
            case LOG_ERROR:
                return "Erro";
        }
        return null;
    }
}
