package com.geoinfo.entity;

import com.geoinfo.util.IRepositorable;
import java.io.Serializable;
import java.text.DecimalFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Fato implements Serializable, IRepositorable {
    @Id @Column(length=70)
    private String cdFato;
    private String dsFato;
    private Short idComportamento;
    private String dsExpressao;
    private Integer qtCasaDecimal;
    private Boolean inFatoVenda;
    private String dsUnidade;

    public String getCdFato() {
        return cdFato;
    }

    public void setCdFato(String cdFato) {
        this.cdFato = cdFato;
    }

    public String getDsFato() {
        return dsFato;
    }

    public void setDsFato(String dsFato) {
        this.dsFato = dsFato;
    }

    public Short getIdComportamento() {
        return idComportamento;
    }

    public void setIdComportamento(Short idComportamento) {
        this.idComportamento = idComportamento;
    }

    public String getDsExpressao() {
        return dsExpressao;
    }

    public void setDsExpressao(String dsExpressao) {
        this.dsExpressao = dsExpressao;
    }

    public Integer getQtCasaDecimal() {
        return qtCasaDecimal;
    }

    public void setQtCasaDecimal(Integer qtCasaDecimal) {
        this.qtCasaDecimal = qtCasaDecimal;
    }

    public Boolean isInFatoVenda() {
        return inFatoVenda;
    }

    public void setInFatoVenda(Boolean inFatoVenda) {
        this.inFatoVenda = inFatoVenda;
    }

    public String getDsUnidade() {
        return dsUnidade;
    }

    public void setDsUnidade(String dsUnidade) {
        this.dsUnidade = dsUnidade;
    }
    
    public DecimalFormat getDecimalFormat(){
        String dsFormato = "###,###,###,###,###,###,##0";
        if(this.qtCasaDecimal > 0){
            dsFormato += ".";
            for(int i = 0; i < this.qtCasaDecimal; i++){
                dsFormato += "0";
            }
        }
        return new DecimalFormat(dsFormato);
    }

    public String getFormatedValue(double vlValor) {
        DecimalFormat df = this.getDecimalFormat();
        return df.format(vlValor);
    }
    
}
