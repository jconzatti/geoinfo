package com.geoinfo.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class VendedorVendaPK implements Serializable {
    @ManyToOne
    private Venda venda;
    private Long cdVendedorVenda;

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Long getCdVendedorVenda() {
        return cdVendedorVenda;
    }

    public void setCdVendedorVenda(Long cdVendedorVenda) {
        this.cdVendedorVenda = cdVendedorVenda;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                VendedorVendaPK vendedorVendaPK = (VendedorVendaPK)o;
                return (this.venda.getVendaPK().getEstabelecimento().getCdPessoa().equals(vendedorVendaPK.getVenda().getVendaPK().getEstabelecimento().getCdPessoa()))
                        && (this.venda.getVendaPK().getCdVenda().equals(vendedorVendaPK.getVenda().getVendaPK().getCdVenda()))
                        && (this.cdVendedorVenda.equals(vendedorVendaPK.getCdVendedorVenda()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.venda.getVendaPK().getEstabelecimento().getCdPessoa().hashCode());
        hash = 53 * hash + (this.venda.getVendaPK().getCdVenda().hashCode());
        hash = 53 * hash + (this.cdVendedorVenda.hashCode());
        return hash;
    }
    
}
