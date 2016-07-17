package com.geoinfo.entity;

import com.geoinfo.util.IRepositorable;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class VendedorVenda implements Serializable, IRepositorable {
    @EmbeddedId
    private VendedorVendaPK vendedorVendaPK;
    @ManyToOne
    private Vendedor vendedor;
    private Double vlComissao;

    public VendedorVendaPK getVendedorVendaPK() {
        return vendedorVendaPK;
    }

    public void setVendedorVendaPK(VendedorVendaPK vendedorVendaPK) {
        this.vendedorVendaPK = vendedorVendaPK;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Double getVlComissao() {
        return vlComissao;
    }

    public void setVlComissao(Double vlComissao) {
        this.vlComissao = vlComissao;
    }
    
}
