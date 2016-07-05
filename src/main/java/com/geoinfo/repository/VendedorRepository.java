package com.geoinfo.repository;

import com.geoinfo.entity.Pessoa;
import com.geoinfo.entity.Vendedor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class VendedorRepository extends PessoaRepository<Vendedor> {

    public VendedorRepository(EntityManager manager) {
        super(manager, Vendedor.class);
    }
    
    private List<Vendedor> getListaSubVendedor(Vendedor vendedor, List<Vendedor> listaVendedor){
        Query query = this.manager.createQuery("select v from Vendedor v where v.vendedor.cdPessoa = :cdPessoa").setParameter("cdPessoa", vendedor.getCdPessoa());
        List<Vendedor> lv = query.getResultList();
        if(!lv.isEmpty()){
            for(Vendedor v : lv){
                if(!listaVendedor.contains(v)){
                    listaVendedor.add(v);
                    listaVendedor = getListaSubVendedor(v, listaVendedor);
                }
            }
        }
        return listaVendedor;
    }
    
    public List<Vendedor> getListaSubVendedor(Vendedor vendedor){
        if(vendedor != null){
            List<Vendedor> listaVendedor = new ArrayList<Vendedor>();
            listaVendedor.add(vendedor);
            return getListaSubVendedor(vendedor, listaVendedor);
        }
        return null;
    }
    
}
