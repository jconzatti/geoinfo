package com.geoinfo.test;

import com.geoinfo.entity.Vendedor;
import com.geoinfo.repository.VendedorRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ListaSubVendedorTest {
    
    public static void main(String[] args) {
        EntityManagerFactory  entityManagerFactory = Persistence.createEntityManagerFactory("geoinfo-pu");
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        VendedorRepository vendedorRepository = new VendedorRepository(entityManager);
        Vendedor vendedor = vendedorRepository.find((long)7);
        
        List<Vendedor> listaVendedor = vendedorRepository.getListaSubVendedor(vendedor);
        
        for(Vendedor v : listaVendedor)
            System.out.println("Vendedor: " + v.toString());
        
        try{
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        
        entityManagerFactory.close();
    }
    
}
