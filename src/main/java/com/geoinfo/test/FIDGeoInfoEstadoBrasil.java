package com.geoinfo.test;

import com.geoinfo.entity.Estado;
import com.geoinfo.entity.EstadoPK;
import com.geoinfo.entity.Pais;
import com.geoinfo.repository.EstadoRepository;
import com.geoinfo.repository.PaisRepository;
import java.io.File;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.simple.SimpleFeature;

public class FIDGeoInfoEstadoBrasil {
    
    public static void main(String args[]) throws IOException{
        File shapeFileEstadoBrasil = new File("C:\\Program Files (x86)\\GeoServer 2.3.3\\data_dir\\data\\geoinfo\\GeoInfo.BRA.UF.shp");
        if(shapeFileEstadoBrasil.exists()){
            FileDataStore fdsEstadoBrasil = FileDataStoreFinder.getDataStore(shapeFileEstadoBrasil);
            SimpleFeatureSource sfsEstadoBrasil = fdsEstadoBrasil.getFeatureSource();
            SimpleFeatureCollection sfcEstadoBrasil = sfsEstadoBrasil.getFeatures();
            SimpleFeatureIterator sfiEstadoBrasil = sfcEstadoBrasil.features();
            
            EntityManagerFactory  entityManagerFactory = Persistence.createEntityManagerFactory("geoinfo-pu");

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            
            EstadoRepository estadoRepository = new EstadoRepository(entityManager);
                
            Long cdPais = new Long(103);
            Pais pais = new PaisRepository(entityManager).find(cdPais);
            
            while(sfiEstadoBrasil.hasNext()){
                SimpleFeature sfEstadoBrasil = sfiEstadoBrasil.next();

                EstadoPK estadoPK = new EstadoPK();
                estadoPK.setCdEstado((Long)sfEstadoBrasil.getAttribute("CDESTADO"));
                estadoPK.setPais(pais);
                Estado estado = estadoRepository.find(estadoPK);
                if(estado != null){
                    estado.setCdFID(sfEstadoBrasil.getID().replaceAll("GeoInfo.", ""));
                    estadoRepository.edit(estado);
                    System.out.println("Estado: " + estado.getCdUF() + " com FID " + estado.getCdFID() + " atualizado com sucesso!");
                }
            }
            
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
}
