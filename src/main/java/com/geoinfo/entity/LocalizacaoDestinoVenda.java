package com.geoinfo.entity;

import com.geoinfo.model.RegiaoGeograficaPontual;
import com.geoinfo.util.IRepositorable;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class LocalizacaoDestinoVenda extends RegiaoGeograficaPontual implements Serializable, IRepositorable {
    @Id @OneToOne
    private Venda venda;
    @ManyToOne
    private Cidade cidade;
    private String dsBairro;
    private String dsEndereco;
    @Column(length=30)
    private String dsNumero;

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
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

    @Override
    public String getDsCodigo() {
        return this.venda.getDsCodigo();
    }
    
    
    
}
