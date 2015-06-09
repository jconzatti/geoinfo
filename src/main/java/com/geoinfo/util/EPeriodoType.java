package com.geoinfo.util;

public enum EPeriodoType {
    ANO, MES, DATA;

    @Override
    public String toString() {
        switch(this){
            case DATA:
                return "Data";
            case MES:
                return "Mês";
            case ANO:
                return "Ano";
        }
        return this.name();
    }
    
    
}
