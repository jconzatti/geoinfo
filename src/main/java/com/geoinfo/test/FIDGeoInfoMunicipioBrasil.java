package com.geoinfo.test;

import com.geoinfo.entity.Cidade;
import com.geoinfo.entity.CidadePK;
import com.geoinfo.entity.Estado;
import com.geoinfo.entity.EstadoPK;
import com.geoinfo.entity.Pais;
import com.geoinfo.repository.CidadeRepository;
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

public class FIDGeoInfoMunicipioBrasil {
    
    public static void main(String args[]) throws IOException{
        File shapeFileMunicipioBrasil = new File("C:\\Program Files (x86)\\GeoServer 2.4.0\\data_dir\\geoinfo\\bract\\GeoInfo.BRA.CT.shp");
        if(shapeFileMunicipioBrasil.exists()){
            FileDataStore fdsMunicipioBrasil = FileDataStoreFinder.getDataStore(shapeFileMunicipioBrasil);
            SimpleFeatureSource sfsMunicipioBrasil = fdsMunicipioBrasil.getFeatureSource();
            SimpleFeatureCollection sfcMunicipioBrasil = sfsMunicipioBrasil.getFeatures();
            SimpleFeatureIterator sfiMunicipioBrasil = sfcMunicipioBrasil.features();
            
            EntityManagerFactory  entityManagerFactory = Persistence.createEntityManagerFactory("geoinfo-pu");

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            
            CidadeRepository cidadeRepository = new CidadeRepository(entityManager);
                
            Long cdPais = new Long(103);
            Pais pais = new PaisRepository(entityManager).find(cdPais);
            
            while(sfiMunicipioBrasil.hasNext()){
                SimpleFeature sfMunicipioBrasil = sfiMunicipioBrasil.next();

                EstadoPK estadoPK = new EstadoPK();
                estadoPK.setCdEstado((Long)sfMunicipioBrasil.getAttribute("CDESTADO"));
                estadoPK.setPais(pais);
                Estado estado = new EstadoRepository(entityManager).find(estadoPK);
                if(estado != null){
                    CidadePK cidadePK = new CidadePK();
                    cidadePK.setCdCidade((Long)sfMunicipioBrasil.getAttribute("CDCIDADE"));
                    cidadePK.setEstado(estado);
                    Cidade cidade = cidadeRepository.find(cidadePK);
                    if(cidade != null){
                        cidade.setCdFID(sfMunicipioBrasil.getID().replaceAll("GeoInfo.", ""));
                        cidadeRepository.edit(cidade);
                        System.out.println("Cidade: " + cidade.getDsCidade() + " com FID " + cidade.getCdFID() + " atualizado com sucesso!");
                    }
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
