package com.geoinfo.entity;

import com.geoinfo.util.IGroupable;
import com.geoinfo.util.IRepositorable;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Produto implements Serializable, IGroupable, IRepositorable {
    @EmbeddedId
    private ProdutoPK produtoPK;
    private String dsProduto;

    public ProdutoPK getProdutoPK() {
        return produtoPK;
    }

    public void setProdutoPK(ProdutoPK produtoPK) {
        this.produtoPK = produtoPK;
    }
    
    public String getDsProduto() {
        return dsProduto;
    }

    public void setDsProduto(String dsProduto) {
        this.dsProduto = dsProduto;
    }
    
    @Override
    public boolean equals(Object o){
        if(o != null){
            if(o.getClass() == this.getClass()){
                Produto produto = (Produto)o;
                return (this.produtoPK.getGerente().getCdPessoa().equals(produto.getProdutoPK().getGerente().getCdPessoa()))
                        && (this.produtoPK.getCdProduto().equals(produto.getProdutoPK().getCdProduto()));
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.produtoPK.getGerente().getCdPessoa().hashCode());
        hash = 53 * hash + (this.produtoPK.getCdProduto().hashCode());
        return hash;
    }
    
    @Override
    public String toString(){
        return dsProduto;
    }

    @Override
    public String getDsCodigo() {
        return this.getProdutoPK().getGerente().getCdPessoa().toString() + "-" +
                this.getProdutoPK().getCdProduto();
    }
    
}
