package com.geoinfo.repository;

import com.geoinfo.entity.Cidade;
import com.geoinfo.entity.CidadePK;
import com.geoinfo.entity.Estado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CidadeRepository extends Repository<Cidade, CidadePK> {

    public CidadeRepository(EntityManager manager) {
        super(manager, Cidade.class);
    }
    
    public Cidade findIBGE(Long cdIBGECidade){
        Query query = this.manager.createQuery("select c from Cidade c where c.cdIBGE = :cdIBGE").setParameter("cdIBGE", cdIBGECidade);
        List<Cidade> listaCidade = query.getResultList();
        if (listaCidade.isEmpty())
            return null;
        else
            return listaCidade.get(0);
    }
    
    public Cidade find(Estado estado, String dsCidade){
        if(estado != null){
            Query query = this.manager.createQuery("select c from Cidade c where c.cidadePK.estado = :estado and c.dsCidade = :dsCidade").
                    setParameter("dsCidade", dsCidade);
            List<Cidade> listaCidade = query.getResultList();
            if (listaCidade.isEmpty())
                return null;
            else
                return listaCidade.get(0);
        }
        return null;
    }
    
    public List getListVlVenda(){
        Query query = this.manager.createQuery(
                "select ci, sum(iv.vlProduto * iv.qtProduto) "
                + "from Localizacao loc, ItemVenda iv "
                + "inner join iv.itemVendaPK.venda v "
                + "inner join v.cliente cl "
                + "inner join loc.localizacaoPK.cidade ci "
                + "where cl = loc.localizacaoPK.pessoa "
                + "and loc.localizacaoPK.dtLocalizacao = "
                + "(select max(loc1.localizacaoPK.dtLocalizacao) "
                + " from Localizacao loc1 "
                + " where loc1.localizacaoPK.pessoa = cl "
                + " and loc1.localizacaoPK.dtLocalizacao <= v.dtVenda) "
                + "group by ci.cidadePK, ci.cdIBGE, ci.dsCidade, ci.cdFID");
        return query.getResultList();
    }
    
}
