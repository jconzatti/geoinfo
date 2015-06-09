package com.geoinfo.entity;

import com.geoinfo.util.IGroupable;
import com.geoinfo.util.IRepositorable;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class FormaPagamento implements Serializable, IGroupable, IRepositorable {
    @EmbeddedId
    private FormaPagamentoPK formaPagamentoPK;
    private String dsFormaPagamento;

    public FormaPagamentoPK getFormaPagamentoPK() {
        return formaPagamentoPK;
    }

    public void setFormaPagamentoPK(FormaPagamentoPK formaPagamentoPK) {
        this.formaPagamentoPK = formaPagamentoPK;
    }
    
    public String getDsFormaPagamento() {
        return dsFormaPagamento;
    }

    public void setDsFormaPagamento(String dsFormaPagamento) {
        this.dsFormaPagamento = dsFormaPagamento;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                FormaPagamento formaPagamento = (FormaPagamento)o;
                return (this.formaPagamentoPK.getGerente().getCdPessoa().equals(formaPagamento.getFormaPagamentoPK().getGerente().getCdPessoa()))
                        && (this.formaPagamentoPK.getCdFormaPagamento().equals(formaPagamento.getFormaPagamentoPK().getCdFormaPagamento()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.formaPagamentoPK.getGerente().getCdPessoa().hashCode());
        hash = 79 * hash + (this.formaPagamentoPK.getCdFormaPagamento().hashCode());
        return hash;
    }
    
    @Override
    public String toString(){
        return dsFormaPagamento;
    }

    @Override
    public String getDsCodigo() {
        return this.getFormaPagamentoPK().getGerente().getCdPessoa().toString() + "-" +
                this.getFormaPagamentoPK().getCdFormaPagamento();
    }

    
}
