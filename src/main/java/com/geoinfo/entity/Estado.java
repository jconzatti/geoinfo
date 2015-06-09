package com.geoinfo.entity;

import com.geoinfo.model.RegiaoGeograficaPoligonal;
import com.geoinfo.util.IRepositorable;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Estado extends RegiaoGeograficaPoligonal implements Serializable, IRepositorable{
    @EmbeddedId
    private EstadoPK estadoPK;
    private Long cdIBGE;
    @Column(length=2)
    private String cdUF;
    private String dsEstado;
    private String cdFID;

    public EstadoPK getEstadoPK() {
        return estadoPK;
    }

    public void setEstadoPK(EstadoPK estadoPK) {
        this.estadoPK = estadoPK;
    }
    
    public Long getCdIBGE() {
        return cdIBGE;
    }

    public void setCdIBGE(Long cdIBGE) {
        this.cdIBGE = cdIBGE;
    }

    public String getCdUF() {
        return cdUF;
    }

    public void setCdUF(String cdUF) {
        this.cdUF = cdUF;
    }

    public String getDsEstado() {
        return dsEstado;
    }

    public void setDsEstado(String dsEstado) {
        this.dsEstado = dsEstado;
    }

    public String getCdFID() {
        return cdFID;
    }

    public void setCdFID(String cdFID) {
        this.cdFID = cdFID;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                Estado estado = (Estado)o;
                return (this.estadoPK.getCdEstado().equals(estado.getEstadoPK().getCdEstado()))
                        && (this.estadoPK.getPais().getCdPais().equals(estado.getEstadoPK().getPais().getCdPais()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.estadoPK.getCdEstado().hashCode());
        hash = 53 * hash + (this.estadoPK.getPais().getCdPais().hashCode());
        return hash;
    }
    
    @Override
    public String toString(){
        return dsEstado + ", " + estadoPK.getPais().getDsPais();
    }

    @Override
    public String getDsCodigo() {
        return this.estadoPK.getPais().getCdPais().toString() + "-" +
                this.estadoPK.getCdEstado().toString();
    }
    
}
