package com.geoinfo.repository;

import com.geoinfo.entity.Cidade;
import com.geoinfo.entity.Estado;
import com.geoinfo.entity.Pais;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PaisRepository extends Repository<Pais, Long> {

    public PaisRepository(EntityManager manager) {
        super(manager, Pais.class);
    }
    
    @Override
    public void delete(Long cdPais){
        Pais pais = this.find(cdPais);
        List<Estado> listaEstado = getListEstado(cdPais);
        for(Estado e : listaEstado)
            this.manager.remove(e);
        this.manager.remove(pais);
    }
    
    public Pais findBACEN(Long cdBACENPais){
        Query query = this.manager.createQuery("select p from Pais p where p.cdBacen = :cdBacen").setParameter("cdBacen", cdBACENPais);
        List<Pais> listaPais = query.getResultList();
        if (listaPais.isEmpty())
            return null;
        else
            return listaPais.get(0);
    }
    
    public Pais findGMI(String cdGMIPais){
        Query query = this.manager.createQuery("select p from Pais p where p.cdGMI = :cdGMI").setParameter("cdGMI", cdGMIPais);
        List<Pais> listaPais = query.getResultList();
        if (listaPais.isEmpty())
            return null;
        else
            return listaPais.get(0);
    }
    
    public Pais find(String dsPais){
        Query query = this.manager.createQuery("select p from Pais p where p.dsPais = :dsPais").setParameter("dsPais", dsPais);
        List<Pais> listaPais = query.getResultList();
        if (listaPais.isEmpty())
            return null;
        else
            return listaPais.get(0);
    }
    
    public List<Estado> getListEstado(Long cdPais){
        Query query = this.manager.createQuery("select e from Estado e where e.estadoPK.pais.cdPais = :cdPais").setParameter("cdPais", cdPais);
        return query.getResultList();
    }
    
    public List<Cidade> getListCidade(Long cdPais){
        Query query = this.manager.createQuery("select c from Cidade c where c.cidadePK.estado.estadoPK.pais.cdPais = :cdPais").setParameter("cdPais", cdPais);
        return query.getResultList();
    }
    
}
