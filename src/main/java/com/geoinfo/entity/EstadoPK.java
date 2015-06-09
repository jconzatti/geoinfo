package com.geoinfo.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jhoni
 */
@Embeddable
public class EstadoPK implements Serializable {
    private Long cdEstado;
    @ManyToOne
    private Pais pais;

    public Long getCdEstado() {
        return cdEstado;
    }

    public void setCdEstado(Long cdEstado) {
        this.cdEstado = cdEstado;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                EstadoPK estadoPK = (EstadoPK)o;
                return (this.cdEstado.equals(estadoPK.getCdEstado()))
                        && (this.pais.getCdPais().equals(estadoPK.getPais().getCdPais()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.cdEstado.hashCode());
        hash = 53 * hash + (this.pais.getCdPais().hashCode());
        return hash;
    }
    
}
