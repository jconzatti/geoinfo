package com.geoinfo.entity;

import com.geoinfo.model.RegiaoGeograficaPontual;
import com.geoinfo.util.IRepositorable;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class LocalizacaoOrigemVenda extends RegiaoGeograficaPontual implements Serializable, IRepositorable {
    @EmbeddedId
    private LocalizacaoOrigemVendaPK localizacaoOrigemVendaPK;
    @ManyToOne
    private Cidade cidade;
    private String dsBairro;
    private String dsEndereco;
    @Column(length=30)
    private String dsNumero;

    public LocalizacaoOrigemVendaPK getLocalizacaoOrigemVendaPK() {
        return localizacaoOrigemVendaPK;
    }

    public void setLocalizacaoOrigemVendaPK(LocalizacaoOrigemVendaPK localizacaoOrigemVendaPK) {
        this.localizacaoOrigemVendaPK = localizacaoOrigemVendaPK;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getDsBairro() {
        return dsBairro;
    }

    public void setDsBairro(String dsBairro) {
        this.dsBairro = dsBairro;
    }

    public String getDsEndereco() {
        return dsEndereco;
    }

    public void setDsEndereco(String dsEndereco) {
        this.dsEndereco = dsEndereco;
    }

    public String getDsNumero() {
        return dsNumero;
    }

    public void setDsNumero(String dsNumero) {
        this.dsNumero = dsNumero;
    }

    @Override
    public String toString() {
        return dsEndereco + ", " + dsNumero + " - " + dsBairro + " " + cidade.toString();
    }
    
}
