package com.geoinfo.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jhoni
 */
@Embeddable
public class ItemVendaPK implements Serializable {
    @ManyToOne
    private Venda venda;
    private Long cdItemVenda;

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Long getCdItemVenda() {
        return cdItemVenda;
    }

    public void setCdItemVenda(Long cdItemVenda) {
        this.cdItemVenda = cdItemVenda;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                ItemVendaPK itemVendaPK = (ItemVendaPK)o;
                return (this.venda.getVendaPK().getEstabelecimento().getCdPessoa().equals(itemVendaPK.getVenda().getVendaPK().getEstabelecimento().getCdPessoa()))
                        && (this.venda.getVendaPK().getCdVenda().equals(itemVendaPK.getVenda().getVendaPK().getCdVenda()))
                        && (this.cdItemVenda.equals(itemVendaPK.getCdItemVenda()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.venda.getVendaPK().getEstabelecimento().getCdPessoa().hashCode());
        hash = 53 * hash + (this.venda.getVendaPK().getCdVenda().hashCode());
        hash = 53 * hash + (this.cdItemVenda.hashCode());
        return hash;
    }
    
}
