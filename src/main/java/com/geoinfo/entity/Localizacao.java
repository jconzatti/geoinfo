package com.geoinfo.entity;

import com.geoinfo.model.RegiaoGeograficaPontual;
import com.geoinfo.util.IRepositorable;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 *
 * @author Jhoni
 */
@Entity
public class Localizacao extends RegiaoGeograficaPontual implements Serializable, IRepositorable {
    @EmbeddedId
    private LocalizacaoPK localizacaoPK;
    private String dsBairro;
    private String dsEndereco;
    @Column(length=30)
    private String dsNumero;

    public LocalizacaoPK getLocalizacaoPK() {
        return localizacaoPK;
    }

    public void setLocalizacaoPK(LocalizacaoPK localizacaoPK) {
        this.localizacaoPK = localizacaoPK;
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
        return dsEndereco + ", " + dsNumero + " - " + dsBairro + " " + localizacaoPK.getCidade().toString();
    }
    
}
