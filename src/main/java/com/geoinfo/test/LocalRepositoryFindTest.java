package com.geoinfo.test;

import com.geoinfo.entity.Estado;
import com.geoinfo.entity.Pais;
import com.geoinfo.repository.EstadoRepository;
import com.geoinfo.repository.PaisRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LocalRepositoryFindTest {
    
    public static void main(String args[]){
        EntityManagerFactory  entityManagerFactory = Persistence.createEntityManagerFactory("geoinfo-pu");
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        Pais pais = new PaisRepository(entityManager).find(new Long(103));
        Estado estado = new EstadoRepository(entityManager).findUF(pais, "SC");
        
        System.out.println(estado.getDsEstado());
        
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
