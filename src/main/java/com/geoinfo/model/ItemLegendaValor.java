package com.geoinfo.model;

import com.geoinfo.util.EObjetoFatoValor;
import java.awt.Color;
import java.text.DecimalFormat;

public class ItemLegendaValor extends ItemLegenda{
    private EObjetoFatoValor idObjetoFatoValor;
    private Double vlItemLegenda;
    private DecimalFormat dfItemLegenda;

    public ItemLegendaValor(Color corItemLegenda, EObjetoFatoValor idObjetoFatoValor, Double vlItemLegenda, DecimalFormat dfItemLegenda) {
        this.corItemLegenda = corItemLegenda;
        this.idObjetoFatoValor = idObjetoFatoValor;
        String dsValor = dfItemLegenda.format(vlItemLegenda);
        dsValor = dsValor.replace(".", "");
        dsValor = dsValor.replace(",", ".");
        this.vlItemLegenda = Double.valueOf(dsValor);
        this.dfItemLegenda = dfItemLegenda;
    }

    public EObjetoFatoValor getIdObjetoFatoValor() {
        return idObjetoFatoValor;
    }

    public void setIdObjetoFatoValor(EObjetoFatoValor idObjetoFatoValor) {
        this.idObjetoFatoValor = idObjetoFatoValor;
    }

    public Double getVlItemLegenda() {
        return vlItemLegenda;
    }

    public void setVlItemLegenda(Double vlItemLegenda) {
        this.vlItemLegenda = vlItemLegenda;
    }

    public DecimalFormat getDfItemLegenda() {
        return dfItemLegenda;
    }

    public void setDfItemLegenda(DecimalFormat dfItemLegenda) {
        this.dfItemLegenda = dfItemLegenda;
    }
    
    @Override
    public String getDsItemLegenda() {
        return idObjetoFatoValor.toString() + " " + dfItemLegenda.format(vlItemLegenda);
    }
    
}
