package com.geoinfo.util;

public enum EPeriodoMesType {
    JAN, FEV, MAR, ABR, MAI, JUN, JUL, AGO, SET, OUT, NOV, DEZ;

    @Override
    public String toString() {
        switch(this){
            case JAN:
                return "Janeiro";
            case FEV:
                return "Fevereiro";
            case MAR:
                return "Março";
            case ABR:
                return "Abril";
            case MAI:
                return "Maio";
            case JUN: 
                return "Junho";
            case JUL:
                return "Julho";
            case AGO:
                return "Agosto";
            case SET:
                return "Setembro";
            case OUT: 
                return "Outubro";
            case NOV:
                return "Novembro";
            case DEZ:
                return "Dezembro";
        }
        return this.name();
    }
}
