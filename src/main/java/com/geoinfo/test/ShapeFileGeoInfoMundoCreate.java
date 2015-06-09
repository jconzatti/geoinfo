package com.geoinfo.test;

import com.geoinfo.entity.Pais;
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
public class ShapeFileGeoInfoMundoCreate {
    
    public static void main(String args[]) throws IOException{
        File shapeFileMundo = new File("C:\\Projetos\\Shape File\\wpp\\wpp.shp");
        if(shapeFileMundo.exists()){
            FileDataStore fdsMundo = FileDataStoreFinder.getDataStore(shapeFileMundo);
            for(int i = 0; i < fdsMundo.getSchema().getDescriptors().size(); i++){
                System.out.println(fdsMundo.getSchema().getDescriptor(i).getLocalName()+", "+ 
                        fdsMundo.getSchema().getDescriptor(i).getType().getBinding());
            }
            SimpleFeatureSource sfsMundo = fdsMundo.getFeatureSource();
            
            File shapeFileGeoInfoMundo = new File("C:\\Projetos\\Shape File\\wpp\\GeoInfo.Mundo.shp");
            ShapefileDataStoreFactory sdsfGeoInfoMundo = new ShapefileDataStoreFactory();
            
            Map<String, Serializable> mapGeoInfoMundo = new HashMap<String, Serializable>();
            mapGeoInfoMundo.put("url", shapeFileGeoInfoMundo.toURI().toURL());
            mapGeoInfoMundo.put("create spatial index", Boolean.TRUE);
            
            ShapefileDataStore sdsGeoInfoMundo = (ShapefileDataStore) sdsfGeoInfoMundo.createDataStore(mapGeoInfoMundo);
            
            SimpleFeatureTypeBuilder sftbGeoInfoMundo = new SimpleFeatureTypeBuilder();
            sftbGeoInfoMundo.setName("GeoInfo.Mundo");
            sftbGeoInfoMundo.setCRS(DefaultGeographicCRS.WGS84);
            sftbGeoInfoMundo.add("the_geom", MultiPolygon.class);
            sftbGeoInfoMundo.add("CDPAIS", Long.class);
            
            SimpleFeatureType sftGeoInfoMundo = sftbGeoInfoMundo.buildFeatureType();
            sdsGeoInfoMundo.createSchema(sftGeoInfoMundo);
            sdsGeoInfoMundo.forceSchemaCRS(DefaultGeographicCRS.WGS84);
            
            String dsTypeName = sdsGeoInfoMundo.getTypeNames()[0];
            SimpleFeatureSource sfsGeoInfoMundo = sdsGeoInfoMundo.getFeatureSource(dsTypeName);
            
            if(sfsGeoInfoMundo instanceof SimpleFeatureStore){
                EntityManagerFactory  entityManagerFactory = Persistence.createEntityManagerFactory("geoinfo-pu");

                EntityManager entityManager = entityManagerFactory.createEntityManager();
                entityManager.getTransaction().begin();
                
                SimpleFeatureStore sfstoreGeoInfoMundo = (SimpleFeatureStore) sfsGeoInfoMundo;
                SimpleFeatureCollection sfcGeoInfoMundo = FeatureCollections.newCollection();
                SimpleFeatureBuilder sfbGeoInfoMundo = new SimpleFeatureBuilder(sftGeoInfoMundo);
                SimpleFeatureCollection sfcMundo = sfsMundo.getFeatures();
                SimpleFeatureIterator sfiMundo = sfcMundo.features();
                    
                PaisRepository paisRepository = new PaisRepository(entityManager);
                
                System.out.println("Eliminando países cadastrados.");
                List<Pais> listaPais = paisRepository.getList();
                for(Pais p : listaPais){
                    paisRepository.delete(p.getCdPais());
                    System.out.println("Pais: " + p.getDsPais() + " eliminado com sucesso!");
                }
                
                System.out.println("Cadastrando países.");
                Long cdPais = new Long(0);
                while(sfiMundo.hasNext()){
                    cdPais++;
                    SimpleFeature sfMundo = sfiMundo.next();
                    sfbGeoInfoMundo.add(sfMundo.getAttribute("the_geom"));
                    sfbGeoInfoMundo.add(cdPais);
                    
                    Pais pais = new Pais();
                    pais.setCdPais(cdPais);
                    pais.setCdGMI((String)sfMundo.getAttribute("GMI_CNTRY"));
                    pais.setCdISO2((String)sfMundo.getAttribute("ISO_2DIGIT"));
                    pais.setCdISO3((String)sfMundo.getAttribute("ISO_3DIGIT"));
                    pais.setDsPais((String)sfMundo.getAttribute("LONG_NAME"));
                    paisRepository.insert(pais);
                    System.out.println("Pais: " + pais.getDsPais() + " inserido com sucesso!");
                    
                    sfcGeoInfoMundo.add(sfbGeoInfoMundo.buildFeature(null));
                }
                
                Transaction transactionGeoInfoMundo = new DefaultTransaction("create");
                sfstoreGeoInfoMundo.setTransaction(transactionGeoInfoMundo);
                sfstoreGeoInfoMundo.addFeatures(sfcGeoInfoMundo);
                try{
                    transactionGeoInfoMundo.commit();
                    System.out.println("GeoInfo.Mundo criado com sucesso!");
                }catch(IOException ioe){
                    transactionGeoInfoMundo.rollback();
                    System.out.println("Erro na criação de GeoInfo.Mundo!");
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
