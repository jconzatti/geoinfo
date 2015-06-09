package com.geoinfo.model;

import java.awt.Color;

public abstract class ItemLegenda {
    protected Color corItemLegenda;
    
    public Color getCorItemLegenda() {
        return corItemLegenda;
    }

    public void setCorItemLegenda(Color corItemLegenda) {
        this.corItemLegenda = corItemLegenda;
    }

    public abstract String getDsItemLegenda();
    
    public String getHexCorItemLegenda(){
        return getHexCor(corItemLegenda);
    }
    
    public static String getHexCor(Color cor){
        String dsHexCorItemLegenda = Integer.toHexString(cor.getRGB());
        dsHexCorItemLegenda = "#"+dsHexCorItemLegenda.substring(2, dsHexCorItemLegenda.length());
        return dsHexCorItemLegenda;
    }
    
}
