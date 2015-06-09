package com.geoinfo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ProdutoPK implements Serializable{
    @ManyToOne
    private PessoaMaster gerente;
    @Column(length=50)
    private String cdProduto;

    public PessoaMaster getGerente() {
        return gerente;
    }

    public void setGerente(PessoaMaster gerente) {
        this.gerente = gerente;
    }

    public String getCdProduto() {
        return cdProduto;
    }

    public void setCdProduto(String cdProduto) {
        this.cdProduto = cdProduto;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                ProdutoPK produtoPK = (ProdutoPK)o;
                return (this.gerente.getCdPessoa().equals(produtoPK.getGerente().getCdPessoa()))
                        && (this.cdProduto.equals(produtoPK.getCdProduto()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.gerente.getCdPessoa().hashCode());
        hash = 53 * hash + (this.cdProduto.hashCode());
        return hash;
    }
    
}
