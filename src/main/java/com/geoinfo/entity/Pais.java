package com.geoinfo.entity;

import com.geoinfo.model.RegiaoGeograficaPoligonal;
import com.geoinfo.util.IRepositorable;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Jhoni
 */
@Entity
public class Pais extends RegiaoGeograficaPoligonal implements Serializable, IRepositorable {
    @Id
    private Long cdPais;
    private Long cdBacen;
    @Column(length=10)
    private String cdGMI;
    @Column(length=2)
    private String cdISO2;
    @Column(length=3)
    private String cdISO3;
    private String dsPais;
    private String cdFID;
    private String dsLayerEstado;
    private String dsLayerCidade;

    public Long getCdPais() {
        return cdPais;
    }

    public void setCdPais(Long cdPais) {
        this.cdPais = cdPais;
    }

    public Long getCdBacen() {
        return cdBacen;
    }

    public void setCdBacen(Long cdBacen) {
        this.cdBacen = cdBacen;
    }

    public String getCdGMI() {
        return cdGMI;
    }

    public void setCdGMI(String cdGMI) {
        this.cdGMI = cdGMI;
    }

    public String getCdISO2() {
        return cdISO2;
    }

    public void setCdISO2(String cdISO2) {
        this.cdISO2 = cdISO2;
    }

    public String getCdISO3() {
        return cdISO3;
    }

    public void setCdISO3(String cdISO3) {
        this.cdISO3 = cdISO3;
    }

    public String getDsPais() {
        return dsPais;
    }

    public void setDsPais(String dsPais) {
        this.dsPais = dsPais;
    }

    public String getCdFID() {
        return cdFID;
    }

    public void setCdFID(String cdFID) {
        this.cdFID = cdFID;
    }

    public String getDsLayerEstado() {
        return dsLayerEstado;
    }

    public void setDsLayerEstado(String dsLayerEstado) {
        this.dsLayerEstado = dsLayerEstado;
    }

    public String getDsLayerCidade() {
        return dsLayerCidade;
    }

    public void setDsLayerCidade(String dsLayerCidade) {
        this.dsLayerCidade = dsLayerCidade;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                Pais pais = (Pais)o;
                return (this.cdPais.equals(pais.getCdPais()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.cdPais.hashCode());
        return hash;
    }
    
    @Override
    public String toString(){
        return dsPais;
    }

    @Override
    public String getDsCodigo() {
        return this.cdPais.toString();
    }
    
}
