package com.geoinfo.entity;

import com.geoinfo.util.IGroupable;
import com.geoinfo.util.IRepositorable;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Venda implements Serializable, IGroupable, IRepositorable {
    @EmbeddedId
    private VendaPK vendaPK;
    @ManyToOne
    private Cliente cliente;
    @Temporal(TemporalType.DATE)
    private Date dtVenda;
    private Double vlDesconto;
    private Short idStatus;

    public VendaPK getVendaPK() {
        return vendaPK;
    }

    public void setVendaPK(VendaPK vendaPK) {
        this.vendaPK = vendaPK;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Date getDtVenda() {
        return dtVenda;
    }

    public void setDtVenda(Date dtVenda) {
        this.dtVenda = dtVenda;
    }

    public Double getVlDesconto() {
        return vlDesconto;
    }

    public void setVlDesconto(Double vlDesconto) {
        this.vlDesconto = vlDesconto;
    }

    public Short getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Short idStatus) {
        this.idStatus = idStatus;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                Venda venda = (Venda)o;
                return (this.vendaPK.getEstabelecimento().getCdPessoa().equals(venda.getVendaPK().getEstabelecimento().getCdPessoa()))
                        && (this.vendaPK.getCdVenda().equals(venda.getVendaPK().getCdVenda()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.vendaPK.getEstabelecimento().getCdPessoa().hashCode());
        hash = 97 * hash + (this.vendaPK.getCdVenda().hashCode());
        return hash;
    }
    
    @Override
    public String toString(){
        return "Estabelecimento: " + this.vendaPK.getEstabelecimento().getDsPessoa() +
                " Venda: " + this.vendaPK.getCdVenda().toString() +
                " Data: " + new SimpleDateFormat("dd/mm/yyyy").format(this.dtVenda) +
                " Cilente: " + this.cliente.getDsPessoa(); 
    }

    @Override
    public String getDsCodigo() {
        return this.getVendaPK().getEstabelecimento().getCdPessoa().toString() + "-" +
                this.getVendaPK().getCdVenda().toString();
    }
    
}
