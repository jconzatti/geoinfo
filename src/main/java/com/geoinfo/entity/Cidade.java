package com.geoinfo.entity;

import com.geoinfo.model.RegiaoGeograficaPoligonal;
import com.geoinfo.util.IRepositorable;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Cidade extends RegiaoGeograficaPoligonal implements Serializable, IRepositorable {
    @EmbeddedId
    private CidadePK cidadePK;
    private Long cdIBGE;
    private String dsCidade;
    private String cdFID;

    public CidadePK getCidadePK() {
        return cidadePK;
    }

    public void setCidadePK(CidadePK cidadePK) {
        this.cidadePK = cidadePK;
    }
    
    public Long getCdIBGE() {
        return cdIBGE;
    }

    public void setCdIBGE(Long cdIBGE) {
        this.cdIBGE = cdIBGE;
    }

    public String getDsCidade() {
        return dsCidade;
    }

    public void setDsCidade(String dsCidade) {
        this.dsCidade = dsCidade;
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
                Cidade cidade = (Cidade)o;
                return (this.cidadePK.getCdCidade().equals(cidade.getCidadePK().getCdCidade()))
                        && (this.cidadePK.getEstado().getEstadoPK().getCdEstado().equals(cidade.getCidadePK().getEstado().getEstadoPK().getCdEstado()))
                        && (this.cidadePK.getEstado().getEstadoPK().getPais().getCdPais().equals(cidade.getCidadePK().getEstado().getEstadoPK().getPais().getCdPais()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.cidadePK.getCdCidade().hashCode());
        hash = 59 * hash + (this.cidadePK.getEstado().getEstadoPK().getCdEstado().hashCode());
        hash = 59 * hash + (this.cidadePK.getEstado().getEstadoPK().getPais().getCdPais().hashCode());
        return hash;
    }
    
    @Override
    public String toString(){
        if((cidadePK.getEstado().getCdUF() != null)&&(!cidadePK.getEstado().getCdUF().equalsIgnoreCase(""))){
            return dsCidade + " - " + cidadePK.getEstado().getCdUF() + ", " + cidadePK.getEstado().getEstadoPK().getPais().getDsPais();
        }else{
            return dsCidade + " - " + cidadePK.getEstado().getDsEstado() + ", " + cidadePK.getEstado().getEstadoPK().getPais().getDsPais();
        }
    }

    @Override
    public String getDsCodigo() {
        return this.cidadePK.getEstado().getEstadoPK().getPais().getCdPais().toString() + "-" +
                this.cidadePK.getEstado().getEstadoPK().getCdEstado().toString() + "-" +
                this.cidadePK.getCdCidade().toString();
    }
}
