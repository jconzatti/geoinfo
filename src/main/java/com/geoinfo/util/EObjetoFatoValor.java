package com.geoinfo.util;

public enum EObjetoFatoValor {
    ATE, IGUAL, MAIORQUE, MENORQUE, MAIORIGUAL, MENORIGUAL, DIFERENTE;
    
    @Override
    public String toString(){
        switch(this){
            case ATE:
                return "Até";
            case IGUAL:
                return "Igual a";
            case MAIORQUE:
                return "Maior que";
            case MENORQUE:
                return "Menor que";
            case MAIORIGUAL:
                return "Maior ou igual a";
            case MENORIGUAL:
                return "Menor ou igual a";
            case DIFERENTE:
                return "Diferente de";
        }
        return "";
    }
    
}
