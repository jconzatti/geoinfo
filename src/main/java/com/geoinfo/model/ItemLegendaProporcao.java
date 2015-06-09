package com.geoinfo.model;

import com.geoinfo.util.EObjetoFatoProporcao;
import java.awt.Color;

public class ItemLegendaProporcao extends ItemLegenda{
    private EObjetoFatoProporcao idObjetoFatoProporcao;

    public ItemLegendaProporcao(Color corItemLegenda, EObjetoFatoProporcao idObjetoFatoProporcao) {
        this.corItemLegenda = corItemLegenda;
        this.idObjetoFatoProporcao = idObjetoFatoProporcao;
    }

    public EObjetoFatoProporcao getIdObjetoFatoProporcao() {
        return idObjetoFatoProporcao;
    }

    public void setIdObjetoFatoProporcao(EObjetoFatoProporcao idObjetoFatoProporcao) {
        this.idObjetoFatoProporcao = idObjetoFatoProporcao;
    }
    
    @Override
    public String getDsItemLegenda() {
        return idObjetoFatoProporcao.toString();
    }
    
}
