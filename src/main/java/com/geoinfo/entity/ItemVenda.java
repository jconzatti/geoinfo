package com.geoinfo.entity;

import com.geoinfo.util.IRepositorable;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jhoni
 */
@Entity
public class ItemVenda implements Serializable, IRepositorable {
    @EmbeddedId
    private ItemVendaPK itemVendaPK;
    @ManyToOne
    private Produto produto;
    private Double qtProduto;
    private Double vlProduto;

    public ItemVendaPK getItemVendaPK() {
        return itemVendaPK;
    }

    public void setItemVendaPK(ItemVendaPK itemVendaPK) {
        this.itemVendaPK = itemVendaPK;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    public Double getQtProduto() {
        return qtProduto;
    }

    public void setQtProduto(Double qtProduto) {
        this.qtProduto = qtProduto;
    }

    public Double getVlProduto() {
        return vlProduto;
    }

    public void setVlProduto(Double vlProduto) {
        this.vlProduto = vlProduto;
    }
    
}
