package com.geoinfo.repository;

import com.geoinfo.entity.CidadePK;
import com.geoinfo.entity.Localizacao;
import com.geoinfo.entity.LocalizacaoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class LocalizacaoRepository extends Repository<Localizacao, LocalizacaoPK> {

    public LocalizacaoRepository(EntityManager manager) {
        super(manager, Localizacao.class);
    }
    
    public List<Localizacao> getListLocalizacao(Long cdPessoa, CidadePK cidadePK){
        Query query = this.manager.createQuery("select l from Localizacao l "
                + "where l.localizacaoPK.pessoa.cdPessoa = :cdPessoa "
                + "and l.localizacaoPK.cidade.cidadePK = :cidadePK")
                .setParameter("cdPessoa", cdPessoa)
                .setParameter("cidadePK", cidadePK);
        return query.getResultList();
    }
    
}
