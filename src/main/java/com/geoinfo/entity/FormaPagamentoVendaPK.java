package com.geoinfo.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class FormaPagamentoVendaPK implements Serializable {
    @ManyToOne
    private Venda venda;
    private Long cdFormaPagamentoVenda;

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Long getCdFormaPagamentoVenda() {
        return cdFormaPagamentoVenda;
    }

    public void setCdFormaPagamentoVenda(Long cdFormaPagamentoVenda) {
        this.cdFormaPagamentoVenda = cdFormaPagamentoVenda;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                FormaPagamentoVendaPK formaPagamentoVendaPK = (FormaPagamentoVendaPK)o;
                return (this.venda.getVendaPK().getEstabelecimento().getCdPessoa().equals(formaPagamentoVendaPK.getVenda().getVendaPK().getEstabelecimento().getCdPessoa()))
                        && (this.venda.getVendaPK().getCdVenda().equals(formaPagamentoVendaPK.getVenda().getVendaPK().getCdVenda()))
                        && (this.cdFormaPagamentoVenda.equals(formaPagamentoVendaPK.getCdFormaPagamentoVenda()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.venda.getVendaPK().getEstabelecimento().getCdPessoa().hashCode());
        hash = 53 * hash + (this.venda.getVendaPK().getCdVenda().hashCode());
        hash = 53 * hash + (this.cdFormaPagamentoVenda.hashCode());
        return hash;
    }
    
}
