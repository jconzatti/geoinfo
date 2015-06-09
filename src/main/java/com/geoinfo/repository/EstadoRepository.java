package com.geoinfo.repository;

import com.geoinfo.entity.Cidade;
import com.geoinfo.entity.Estado;
import com.geoinfo.entity.EstadoPK;
import com.geoinfo.entity.Pais;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class EstadoRepository extends Repository<Estado, EstadoPK> {

    public EstadoRepository(EntityManager manager) {
        super(manager, Estado.class);
    }
    
    public Estado findIBGE(Long cdIBGEEstado){
        Query query = this.manager.createQuery("select e from Estado e where e.cdIBGE = :cdIBGE").setParameter("cdIBGE", cdIBGEEstado);
        List<Estado> listaEstado = query.getResultList();
        if (listaEstado.isEmpty())
            return null;
        else
            return listaEstado.get(0);
    }
    
    public Estado findUF(Pais pais, String cdUFEstado){
        if(pais != null){
            Query query = this.manager.createQuery("select e from Estado e where e.estadoPK.pais = :pais and e.cdUF = :cdUF").
                    setParameter("cdUF", cdUFEstado).setParameter("pais", pais);
            List<Estado> listaEstado = query.getResultList();
            if (listaEstado.isEmpty())
                return null;
            else
                return listaEstado.get(0);
        }
        return null;
    }
    
    public Estado find(Pais pais, String dsEstado){
        if(pais != null){
            Query query = this.manager.createQuery("select e from Estado e where e.estadoPK.pais = :pais and e.dsEstado = :dsEstado").
                    setParameter("dsEstado", dsEstado).setParameter("pais", pais);
            List<Estado> listaEstado = query.getResultList();
            if (listaEstado.isEmpty())
                return null;
            else
                return listaEstado.get(0);
        }
        return null;
    }
    
    @Override
    public void delete(EstadoPK estadoPK){
        Estado estado = this.find(estadoPK);
        List<Cidade> listaCidade = getListCidade(estadoPK);
        for(Cidade c : listaCidade)
            this.manager.remove(c);
        this.manager.remove(estado);
    }
    
    public List<Cidade> getListCidade(EstadoPK estadoPK){
        Query query = this.manager.createQuery("select c from Cidade c where c.cidadePK.estado.estadoPK = :estadoPK").setParameter("estadoPK", estadoPK);
        return query.getResultList();
    }
    
}
