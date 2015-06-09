package com.geoinfo.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class CidadePK implements Serializable {
    private Long cdCidade;
    @ManyToOne
    private Estado estado;

    public Long getCdCidade() {
        return cdCidade;
    }

    public void setCdCidade(Long cdCidade) {
        this.cdCidade = cdCidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                CidadePK cidadePK = (CidadePK)o;
                return (this.cdCidade.equals(cidadePK.getCdCidade()))
                        && (this.estado.getEstadoPK().getCdEstado().equals(cidadePK.getEstado().getEstadoPK().getCdEstado()))
                        && (this.estado.getEstadoPK().getPais().getCdPais().equals(cidadePK.getEstado().getEstadoPK().getPais().getCdPais()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.cdCidade.hashCode());
        hash = 53 * hash + (this.estado.getEstadoPK().getCdEstado().hashCode());
        hash = 53 * hash + (this.estado.getEstadoPK().getPais().getCdPais().hashCode());
        return hash;
    }
    
}
