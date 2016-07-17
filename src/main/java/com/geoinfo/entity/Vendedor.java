package com.geoinfo.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Vendedor extends PessoaNMaster{
    
    @ManyToOne
    private Vendedor vendedor;

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }
    
}
