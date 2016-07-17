package com.geoinfo.entity;

import com.geoinfo.util.IRepositorable;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class FormaPagamentoVenda implements Serializable, IRepositorable {
    @EmbeddedId
    private FormaPagamentoVendaPK formaPagamentoVendaPK;
    @ManyToOne
    private FormaPagamento formaPagamento;
    private Double vlFormaPagamento;

    public FormaPagamentoVendaPK getFormaPagamentoVendaPK() {
        return formaPagamentoVendaPK;
    }

    public void setFormaPagamentoVendaPK(FormaPagamentoVendaPK formaPagamentoVendaPK) {
        this.formaPagamentoVendaPK = formaPagamentoVendaPK;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Double getVlFormaPagamento() {
        return vlFormaPagamento;
    }

    public void setVlFormaPagamento(Double vlFormaPagamento) {
        this.vlFormaPagamento = vlFormaPagamento;
    }
    
}
