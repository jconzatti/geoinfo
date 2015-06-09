package com.geoinfo.util;

public enum EObjetoFatoProporcao {
    CONTINUA_NADA, DEIXOU_EXISTIR, DIMINUIU, MANTEVE, AUMENTOU, PASSOU_EXISTIR, INDEFINIDO;
    
    @Override
    public String toString(){
        switch(this){
            case CONTINUA_NADA:
                return "Continua com nada";
            case DEIXOU_EXISTIR:
                return "Deixou de existir";
            case DIMINUIU:
                return "Dimunuiu";
            case MANTEVE:
                return "Manteve-se";
            case AUMENTOU:
                return "Aumentou";
            case PASSOU_EXISTIR:
                return "Passou a existir";
            case INDEFINIDO:
                return "Indefinido";
        }
        return "Indefinido";
    }
}
