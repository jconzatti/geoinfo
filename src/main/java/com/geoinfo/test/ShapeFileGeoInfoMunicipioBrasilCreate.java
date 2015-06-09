package com.geoinfo.test;

import com.geoinfo.entity.Cidade;
import com.geoinfo.entity.CidadePK;
import com.geoinfo.entity.Estado;
import com.geoinfo.repository.CidadeRepository;
import com.geoinfo.repository.EstadoRepository;
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
public class ShapeFileGeoInfoMunicipioBrasilCreate {
    
    public static void main(String args[]) throws IOException{
        File shapeFileMunicipioBrasil = new File("C:\\Program Files (x86)\\GeoServer 2.4.0\\data_dir\\geoinfo\\IBGE\\2007\\bract\\55mu2500gsd.shp");
        if(shapeFileMunicipioBrasil.exists()){
            FileDataStore fdsMunicipioBrasil = FileDataStoreFinder.getDataStore(shapeFileMunicipioBrasil);
            for(int i = 0; i < fdsMunicipioBrasil.getSchema().getDescriptors().size(); i++){
                System.out.println(fdsMunicipioBrasil.getSchema().getDescriptor(i).getLocalName()+", "+ 
                        fdsMunicipioBrasil.getSchema().getDescriptor(i).getType().getBinding());
            }
            SimpleFeatureSource sfsMunicipioBrasil = fdsMunicipioBrasil.getFeatureSource();
            
            File shapeFileGeoInfoMunicipioBrasil = new File("C:\\Program Files (x86)\\GeoServer 2.4.0\\data_dir\\geoinfo\\IBGE\\2007\\bract\\GeoInfo.BRA.CT.shp");
            ShapefileDataStoreFactory sdsfGeoInfoMunicipioBrasil = new ShapefileDataStoreFactory();
            
            Map<String, Serializable> mapGeoInfoMunicipioBrasil = new HashMap<String, Serializable>();
            mapGeoInfoMunicipioBrasil.put("url", shapeFileGeoInfoMunicipioBrasil.toURI().toURL());
            mapGeoInfoMunicipioBrasil.put("create spatial index", Boolean.TRUE);
            
            ShapefileDataStore sdsGeoInfoMunicipioBrasil = (ShapefileDataStore) sdsfGeoInfoMunicipioBrasil.createDataStore(mapGeoInfoMunicipioBrasil);
            
            SimpleFeatureTypeBuilder sftbGeoInfoMunicipioBrasil = new SimpleFeatureTypeBuilder();
            sftbGeoInfoMunicipioBrasil.setName("GeoInfo.BRA.CT");
            sftbGeoInfoMunicipioBrasil.setCRS(DefaultGeographicCRS.WGS84);
            sftbGeoInfoMunicipioBrasil.add("the_geom", MultiPolygon.class);
            sftbGeoInfoMunicipioBrasil.add("CDPAIS", Long.class);
            sftbGeoInfoMunicipioBrasil.add("CDESTADO", Long.class);
            sftbGeoInfoMunicipioBrasil.add("CDCIDADE", Long.class);
            
            SimpleFeatureType sftGeoInfoMunicipioBrasil = sftbGeoInfoMunicipioBrasil.buildFeatureType();
            sdsGeoInfoMunicipioBrasil.createSchema(sftGeoInfoMunicipioBrasil);
            sdsGeoInfoMunicipioBrasil.forceSchemaCRS(DefaultGeographicCRS.WGS84);
            
            String dsTypeName = sdsGeoInfoMunicipioBrasil.getTypeNames()[0];
            SimpleFeatureSource sfsGeoInfoMunicipioBrasil = sdsGeoInfoMunicipioBrasil.getFeatureSource(dsTypeName);
            
            if(sfsGeoInfoMunicipioBrasil instanceof SimpleFeatureStore){
                EntityManagerFactory  entityManagerFactory = Persistence.createEntityManagerFactory("geoinfo-pu");

                EntityManager entityManager = entityManagerFactory.createEntityManager();
                entityManager.getTransaction().begin();
                
                SimpleFeatureStore sfstoreGeoInfoMunicipioBrasil = (SimpleFeatureStore) sfsGeoInfoMunicipioBrasil;
                SimpleFeatureCollection sfcGeoInfoMunicipioBrasil = FeatureCollections.newCollection();
                SimpleFeatureBuilder sfbGeoInfoMunicipioBrasil = new SimpleFeatureBuilder(sftGeoInfoMunicipioBrasil);
                SimpleFeatureCollection sfcMunicipioBrasil = sfsMunicipioBrasil.getFeatures();
                SimpleFeatureIterator sfiMunicipioBrasil = sfcMunicipioBrasil.features();
                
                System.out.println("Cadastrando cidades.");
                Long cdCidade = new Long(5513);
                while(sfiMunicipioBrasil.hasNext()){
                    SimpleFeature sfMunicipioBrasil = sfiMunicipioBrasil.next();
                    
                    Long cdIBGEUF = new Long(0);
                    try{
                        cdIBGEUF = Long.valueOf((String)sfMunicipioBrasil.getAttribute("UF"));
                    }catch (Exception e){}
                    
                    Long cdIBGECidade = new Long(0);
                    try{
                        cdIBGECidade = (Long)sfMunicipioBrasil.getAttribute("GEOCODIG_M");
                    }catch (Exception e){}
                    
                    System.out.println("Cidade: " + (String)sfMunicipioBrasil.getAttribute("Nome_Munic") + 
                            " " + cdIBGECidade + " - " + cdIBGEUF + " localizado!");
                    
                    Estado estado = new EstadoRepository(entityManager).findIBGE(cdIBGEUF);
                    
                    if(estado == null){
                        sfbGeoInfoMunicipioBrasil.add(sfMunicipioBrasil.getAttribute("the_geom"));
                        sfbGeoInfoMunicipioBrasil.add(103);
                        sfbGeoInfoMunicipioBrasil.add(0);
                        sfbGeoInfoMunicipioBrasil.add(0);
                        System.out.println("Cidade: " + (String)sfMunicipioBrasil.getAttribute("Nome_Munic") + " não inserido!");
                    }else{
                        boolean inInsert = false;
                        CidadeRepository cidadeRepository = new CidadeRepository(entityManager);
                        Cidade cidade = cidadeRepository.findIBGE(cdIBGECidade);
                        if(cidade == null){
                            cdCidade++;
                            cidade = new Cidade();
                            CidadePK cidadePK = new CidadePK();
                            cidadePK.setCdCidade(cdCidade);
                            cidadePK.setEstado(estado);
                            cidade.setCidadePK(cidadePK);
                            cidade.setCdIBGE(cdIBGECidade);
                            inInsert = true;
                        }
                        cidade.setDsCidade(((String)sfMunicipioBrasil.getAttribute("Nome_Munic")).toUpperCase());
                        if(inInsert){
                            cidadeRepository.insert(cidade);
                            System.out.println("Cidade: " + cidade.getDsCidade() + " - " + estado.getCdUF() + " inserido com sucesso!");
                        }else{
                            cidadeRepository.edit(cidade);
                            System.out.println("Cidade: " + cidade.getDsCidade() + " - " + estado.getCdUF() + " alterado com sucesso!");
                        }
                        sfbGeoInfoMunicipioBrasil.add(sfMunicipioBrasil.getAttribute("the_geom"));
                        sfbGeoInfoMunicipioBrasil.add(cidade.getCidadePK().getEstado().getEstadoPK().getPais().getCdPais());
                        sfbGeoInfoMunicipioBrasil.add(cidade.getCidadePK().getEstado().getEstadoPK().getCdEstado());
                        sfbGeoInfoMunicipioBrasil.add(cidade.getCidadePK().getCdCidade());
                    }
                    
                    sfcGeoInfoMunicipioBrasil.add(sfbGeoInfoMunicipioBrasil.buildFeature(null));
                }
                
                Transaction transactionGeoInfoMunicipioBrasil = new DefaultTransaction("create");
                sfstoreGeoInfoMunicipioBrasil.setTransaction(transactionGeoInfoMunicipioBrasil);
                sfstoreGeoInfoMunicipioBrasil.addFeatures(sfcGeoInfoMunicipioBrasil);
                try{
                    transactionGeoInfoMunicipioBrasil.commit();
                    System.out.println("GeoInfo.BRA.CT criado com sucesso!");
                }catch(IOException ioe){
                    transactionGeoInfoMunicipioBrasil.rollback();
                    System.out.println("Erro na criação de GeoInfo.BRA.CT!");
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
