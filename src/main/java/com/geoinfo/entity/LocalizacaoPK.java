package com.geoinfo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Jhoni
 */
@Embeddable
public class LocalizacaoPK implements Serializable {
    @ManyToOne
    private Pessoa pessoa;
    @ManyToOne
    private Cidade cidade;
    @Temporal(TemporalType.DATE)
    private Date dtLocalizacao;
    
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Date getDtLocalizacao() {
        return dtLocalizacao;
    }

    public void setDtLocalizacao(Date dtLocalizacao) {
        this.dtLocalizacao = dtLocalizacao;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                LocalizacaoPK localizacaoPK = (LocalizacaoPK)o;
                return (this.pessoa.getCdPessoa().equals(localizacaoPK.getPessoa().getCdPessoa()))
                        && (this.cidade.getCidadePK().getCdCidade().equals(localizacaoPK.getCidade().getCidadePK().getCdCidade()))
                        && (this.cidade.getCidadePK().getEstado().getEstadoPK().getCdEstado().equals(localizacaoPK.getCidade().getCidadePK().getEstado().getEstadoPK().getCdEstado()))
                        && (this.cidade.getCidadePK().getEstado().getEstadoPK().getPais().getCdPais().equals(localizacaoPK.getCidade().getCidadePK().getEstado().getEstadoPK().getPais().getCdPais()))
                        && (this.dtLocalizacao.equals(localizacaoPK.getDtLocalizacao()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.pessoa.getCdPessoa().hashCode());
        hash = 53 * hash + (this.cidade.getCidadePK().getCdCidade().hashCode());
        hash = 53 * hash + (this.cidade.getCidadePK().getEstado().getEstadoPK().getCdEstado().hashCode());
        hash = 53 * hash + (this.cidade.getCidadePK().getEstado().getEstadoPK().getPais().getCdPais().hashCode());
        hash = 53 * hash + (this.dtLocalizacao.hashCode());
        return hash;
    }
    
}
