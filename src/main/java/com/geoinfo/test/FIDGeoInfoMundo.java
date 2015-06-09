package com.geoinfo.test;

import com.geoinfo.entity.Pais;
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

public class FIDGeoInfoMundo {
    
    public static void main(String args[]) throws IOException{
        File shapeFileMundo = new File("C:\\Program Files (x86)\\GeoServer 2.3.3\\data_dir\\data\\geoinfo\\GeoInfo.Mundo.shp");
        if(shapeFileMundo.exists()){
            FileDataStore fdsMundo = FileDataStoreFinder.getDataStore(shapeFileMundo);
            SimpleFeatureSource sfsMundo = fdsMundo.getFeatureSource();
            SimpleFeatureCollection sfcMundo = sfsMundo.getFeatures();
            SimpleFeatureIterator sfiMundo = sfcMundo.features();
            
            EntityManagerFactory  entityManagerFactory = Persistence.createEntityManagerFactory("geoinfo-pu");

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            
            PaisRepository paisRepository = new PaisRepository(entityManager);
                
            while(sfiMundo.hasNext()){
                SimpleFeature sfMundo = sfiMundo.next();

                Pais pais = paisRepository.find((Long)sfMundo.getAttribute("CDPAIS"));
                if(pais != null){
                    pais.setCdFID(sfMundo.getID().replaceAll("GeoInfo.", ""));
                    paisRepository.edit(pais);
                    System.out.println("País: " + pais.getDsPais() + " com FID " + pais.getCdFID() + " atualizado com sucesso!");
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
