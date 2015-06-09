package com.geoinfo.test;

import com.geoinfo.entity.Estado;
import com.geoinfo.entity.EstadoPK;
import com.geoinfo.entity.Pais;
import com.geoinfo.repository.EstadoRepository;
import com.geoinfo.repository.PaisRepository;
import com.vividsolutions.jts.geom.MultiPolygon;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

/**
 *
 * @author Jhoni
 */
public class ShapeFileGeoInfoEstadoBrasilCreate {
    
    public static void main(String args[]) throws IOException{
        File shapeFileEstadoBrasil = new File("C:\\Projetos\\Shape File\\Brasil - Estados\\Brasil.shp");
        if(shapeFileEstadoBrasil.exists()){
            FileDataStore fdsEstadoBrasil = FileDataStoreFinder.getDataStore(shapeFileEstadoBrasil);
            for(int i = 0; i < fdsEstadoBrasil.getSchema().getDescriptors().size(); i++){
                System.out.println(fdsEstadoBrasil.getSchema().getDescriptor(i).getLocalName()+", "+ 
                        fdsEstadoBrasil.getSchema().getDescriptor(i).getType().getBinding());
            }
            SimpleFeatureSource sfsEstadoBrasil = fdsEstadoBrasil.getFeatureSource();
            
            File shapeFileGeoInfoEstadoBrasil = new File("C:\\Projetos\\Shape File\\Brasil - Estados\\GeoInfo.BRA.UF.shp");
            ShapefileDataStoreFactory sdsfGeoInfoEstadoBrasil = new ShapefileDataStoreFactory();
            
            Map<String, Serializable> mapGeoInfoEstadoBrasil = new HashMap<String, Serializable>();
            mapGeoInfoEstadoBrasil.put("url", shapeFileGeoInfoEstadoBrasil.toURI().toURL());
            mapGeoInfoEstadoBrasil.put("create spatial index", Boolean.TRUE);
            
            ShapefileDataStore sdsGeoInfoEstadoBrasil = (ShapefileDataStore) sdsfGeoInfoEstadoBrasil.createDataStore(mapGeoInfoEstadoBrasil);
            
            SimpleFeatureTypeBuilder sftbGeoInfoEstadoBrasil = new SimpleFeatureTypeBuilder();
            sftbGeoInfoEstadoBrasil.setName("GeoInfo.BRA.UF");
            sftbGeoInfoEstadoBrasil.setCRS(DefaultGeographicCRS.WGS84);
            sftbGeoInfoEstadoBrasil.add("the_geom", MultiPolygon.class);
            sftbGeoInfoEstadoBrasil.add("CDPAIS", Long.class);
            sftbGeoInfoEstadoBrasil.add("CDESTADO", Long.class);
            
            SimpleFeatureType sftGeoInfoEstadoBrasil = sftbGeoInfoEstadoBrasil.buildFeatureType();
            sdsGeoInfoEstadoBrasil.createSchema(sftGeoInfoEstadoBrasil);
            sdsGeoInfoEstadoBrasil.forceSchemaCRS(DefaultGeographicCRS.WGS84);
            
            String dsTypeName = sdsGeoInfoEstadoBrasil.getTypeNames()[0];
            SimpleFeatureSource sfsGeoInfoEstadoBrasil = sdsGeoInfoEstadoBrasil.getFeatureSource(dsTypeName);
            
            if(sfsGeoInfoEstadoBrasil instanceof SimpleFeatureStore){
                EntityManagerFactory  entityManagerFactory = Persistence.createEntityManagerFactory("geoinfo-pu");

                EntityManager entityManager = entityManagerFactory.createEntityManager();
                entityManager.getTransaction().begin();
                
                SimpleFeatureStore sfstoreGeoInfoEstadoBrasil = (SimpleFeatureStore) sfsGeoInfoEstadoBrasil;
                SimpleFeatureCollection sfcGeoInfoEstadoBrasil = FeatureCollections.newCollection();
                SimpleFeatureBuilder sfbGeoInfoEstadoBrasil = new SimpleFeatureBuilder(sftGeoInfoEstadoBrasil);
                SimpleFeatureCollection sfcEstadoBrasil = sfsEstadoBrasil.getFeatures();
                SimpleFeatureIterator sfiEstadoBrasil = sfcEstadoBrasil.features();
                    
                EstadoRepository estadoRepository = new EstadoRepository(entityManager);
                
                System.out.println("Eliminando estados cadastrados.");
                List<Estado> listaEstado = estadoRepository.getList();
                for(Estado e : listaEstado){
                    estadoRepository.delete(e.getEstadoPK());
                    System.out.println("Estado: " + e.getDsEstado() + " eliminado com sucesso!");
                }
                
                System.out.println("Cadastrando estados.");
                Long cdEstado = new Long(0);
                Long cdPais = new Long(103);
                Pais pais = new PaisRepository(entityManager).find(cdPais);
                while(sfiEstadoBrasil.hasNext()){
                    cdEstado++;
                    SimpleFeature sfEstadoBrasil = sfiEstadoBrasil.next();
                    sfbGeoInfoEstadoBrasil.add(sfEstadoBrasil.getAttribute("the_geom"));
                    sfbGeoInfoEstadoBrasil.add(cdPais);
                    sfbGeoInfoEstadoBrasil.add(cdEstado);
                    
                    Estado estado = new Estado();
                    EstadoPK estadoPK = new EstadoPK();
                    estadoPK.setCdEstado(cdEstado);
                    estadoPK.setPais(pais);
                    estado.setEstadoPK(estadoPK);
                    estado.setCdUF((String)sfEstadoBrasil.getAttribute("UF"));
                    estadoRepository.insert(estado);
                    System.out.println("Estado: " + estado.getCdUF() + " inserido com sucesso!");
                    
                    sfcGeoInfoEstadoBrasil.add(sfbGeoInfoEstadoBrasil.buildFeature(null));
                }
                
                Transaction transactionGeoInfoEstadoBrasil = new DefaultTransaction("create");
                sfstoreGeoInfoEstadoBrasil.setTransaction(transactionGeoInfoEstadoBrasil);
                sfstoreGeoInfoEstadoBrasil.addFeatures(sfcGeoInfoEstadoBrasil);
                try{
                    transactionGeoInfoEstadoBrasil.commit();
                    System.out.println("GeoInfo.BRA.UF criado com sucesso!");
                }catch(IOException ioe){
                    transactionGeoInfoEstadoBrasil.rollback();
                    System.out.println("Erro na criação de GeoInfo.BRA.UF!");
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
}
