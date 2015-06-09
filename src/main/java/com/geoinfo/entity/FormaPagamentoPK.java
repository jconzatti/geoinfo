package com.geoinfo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class FormaPagamentoPK implements Serializable{
    @ManyToOne
    private PessoaMaster gerente;
    @Column(length = 50)
    private String cdFormaPagamento;

    public PessoaMaster getGerente() {
        return gerente;
    }

    public void setGerente(PessoaMaster gerente) {
        this.gerente = gerente;
    }

    public String getCdFormaPagamento() {
        return cdFormaPagamento;
    }

    public void setCdFormaPagamento(String cdFormaPagamento) {
        this.cdFormaPagamento = cdFormaPagamento;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                FormaPagamentoPK formaPagamentoPK = (FormaPagamentoPK)o;
                return (this.gerente.getCdPessoa().equals(formaPagamentoPK.getGerente().getCdPessoa()))
                        && (this.cdFormaPagamento.equals(formaPagamentoPK.getCdFormaPagamento()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.gerente.getCdPessoa().hashCode());
        hash = 53 * hash + (this.cdFormaPagamento.hashCode());
        return hash;
    }
    
}
