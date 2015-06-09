package com.geoinfo.model;

import com.geoinfo.util.EObjetoFatoProporcao;
import java.text.DecimalFormat;

public class ObjetoFatoProporcao {
    private Double vlA;
    private Double vlB;
    private EObjetoFatoProporcao eObjetoFatoValorProporcao;
    
    private void calcularEObjetoFatoValorProporcao(){
        if((vlA.doubleValue() == 0)&&(vlB.doubleValue() == 0))
            eObjetoFatoValorProporcao = EObjetoFatoProporcao.CONTINUA_NADA;
        else if((vlA.doubleValue() == 0)&&(vlB.doubleValue() > 0))
            eObjetoFatoValorProporcao = EObjetoFatoProporcao.PASSOU_EXISTIR;
        else if(vlA.doubleValue() == vlB.doubleValue())
            eObjetoFatoValorProporcao = EObjetoFatoProporcao.MANTEVE;
        else if((vlA.doubleValue() > 0)&&(vlB.doubleValue() == 0))
            eObjetoFatoValorProporcao = EObjetoFatoProporcao.DEIXOU_EXISTIR;
        else if(vlA.doubleValue() > vlB.doubleValue())
            eObjetoFatoValorProporcao = EObjetoFatoProporcao.DIMINUIU;
        else if(vlA.doubleValue() < vlB.doubleValue())
            eObjetoFatoValorProporcao = EObjetoFatoProporcao.AUMENTOU;
        else
            eObjetoFatoValorProporcao = EObjetoFatoProporcao.INDEFINIDO;
    }

    public ObjetoFatoProporcao(Double vlA, Double vlB) {
        this.vlA = vlA;
        this.vlB = vlB;
        calcularEObjetoFatoValorProporcao();
    }

    public Double getVlA() {
        return vlA;
    }

    public void setVlA(Double vlA) {
        this.vlA = vlA;
        calcularEObjetoFatoValorProporcao();
    }

    public Double getVlB() {
        return vlB;
    }

    public void setVlB(Double vlB) {
        this.vlB = vlB;
        calcularEObjetoFatoValorProporcao();
    }
    
    public EObjetoFatoProporcao getEObjetoFatoValorProporcao(){
        return eObjetoFatoValorProporcao;
    }
    
    public Double getVlProporcao(){
        switch(eObjetoFatoValorProporcao){
            case DEIXOU_EXISTIR:
            case CONTINUA_NADA:
                return Double.NEGATIVE_INFINITY;
            case PASSOU_EXISTIR:
                return Double.POSITIVE_INFINITY;
            case MANTEVE:
                return 0d;
            case DIMINUIU:
            case AUMENTOU:
                return (Double) Math.abs(100*((vlB.doubleValue()/vlA.doubleValue()) - 1));
            default:
                return Double.NaN;
        }
    }
    
    @Override
    public String toString(){
        switch(eObjetoFatoValorProporcao){
            case DEIXOU_EXISTIR:
            case CONTINUA_NADA:
            case PASSOU_EXISTIR:
            case MANTEVE:
                return eObjetoFatoValorProporcao.toString();
            case DIMINUIU:
            case AUMENTOU:
                DecimalFormat df = new DecimalFormat("#,##0.00");
                return eObjetoFatoValorProporcao.toString() + " " + df.format(getVlProporcao().doubleValue()) + "%";
            default:
                return null;
        }
    }
}
