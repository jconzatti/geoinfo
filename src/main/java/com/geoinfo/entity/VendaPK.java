package com.geoinfo.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jhoni
 */
@Embeddable
public class VendaPK implements Serializable {
    @ManyToOne
    private Estabelecimento estabelecimento;
    private Long cdVenda;

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Long getCdVenda() {
        return cdVenda;
    }

    public void setCdVenda(Long cdVenda) {
        this.cdVenda = cdVenda;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                VendaPK vendaPK = (VendaPK)o;
                return (this.estabelecimento.getCdPessoa().equals(vendaPK.getEstabelecimento().getCdPessoa()))
                        && (this.cdVenda.equals(vendaPK.getCdVenda()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.estabelecimento.getCdPessoa().hashCode());
        hash = 53 * hash + (this.cdVenda.hashCode());
        return hash;
    }
    
}
