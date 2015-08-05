package com.geoinfo.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class LocalizacaoDestinoVendaPK implements Serializable {
    @OneToOne
    private Venda venda;

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                LocalizacaoDestinoVendaPK localizacaoDestinoVendaPK = (LocalizacaoDestinoVendaPK)o;
                return (this.venda.getVendaPK().equals(localizacaoDestinoVendaPK.getVenda().getVendaPK()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.venda.getVendaPK().hashCode());
        return hash;
    }
    
}
