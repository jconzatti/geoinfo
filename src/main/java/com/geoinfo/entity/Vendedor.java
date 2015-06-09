package com.geoinfo.entity;

import com.geoinfo.util.IGroupable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jhoni
 */
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
