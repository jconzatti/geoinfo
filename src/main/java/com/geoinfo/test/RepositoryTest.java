package com.geoinfo.test;

import com.geoinfo.entity.Administrador;
import com.geoinfo.entity.Cidade;
import com.geoinfo.entity.Fato;
import com.geoinfo.exception.ObjetoFatoBuilderException;
import com.geoinfo.exception.PeriodoControlException;
import com.geoinfo.exception.PeriodoIntervaloComparavelException;
import com.geoinfo.model.ListaObjetoFato;
import com.geoinfo.model.ObjetoFato;
import com.geoinfo.model.ObjetoFatoPeriodo;
import com.geoinfo.model.PeriodoIntervalo;
import com.geoinfo.model.PeriodoIntervaloComparavel;
import com.geoinfo.repository.AdministradorRepository;
import com.geoinfo.repository.FatoRepository;
import com.geoinfo.util.EPeriodoMesType;
import com.geoinfo.util.EPeriodoType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Jhoni
 */
public class RepositoryTest {
    
    public static void main(String args[]){
        EntityManagerFactory  entityManagerFactory = Persistence.createEntityManagerFactory("geoinfo-pu");
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        FatoRepository fatoRepository = new FatoRepository(entityManager);
        Fato fato = fatoRepository.find("QTCLIENTE");
        List<Fato> listaFato = new ArrayList<Fato>();
        listaFato.add(fato);
        
        AdministradorRepository administradorRepository = new AdministradorRepository(entityManager);
        Administrador administrador = administradorRepository.find((long)1);
        
        try{
            PeriodoIntervaloComparavel periodoIntervaloComparavel = PeriodoIntervaloComparavel.create(PeriodoIntervalo.create(EPeriodoType.DATA, 2010, EPeriodoMesType.JAN, 27, 0, null, 0, false), null, false);

            ListaObjetoFato<Cidade> objetoFatoBuilderCidade = ListaObjetoFato.create(listaFato, periodoIntervaloComparavel, entityManager, Cidade.class, administrador);

            Iterator<ObjetoFato<Cidade>> itObjetoFato = objetoFatoBuilderCidade.getLista().iterator();
            while (itObjetoFato.hasNext()){
                ObjetoFatoPeriodo<Cidade> objetoFatoPeriodo = itObjetoFato.next().getObjetoFatoPeriodoA(); 
                Iterator<Cidade> itCidade = objetoFatoPeriodo.getIteratorItem();
                while(itCidade.hasNext()){
                    Cidade cidade = itCidade.next();
                    Double vlItem = objetoFatoPeriodo.getVlItem(cidade);
                    System.out.println(cidade.getDsCidade() + " - " + cidade.getCidadePK().getEstado().getCdUF() + " = " + vlItem.toString());
                }
            }
        }catch(ObjetoFatoBuilderException ofbe){
            System.out.println(ofbe.getMessage());
        }catch(PeriodoIntervaloComparavelException pice){
            System.out.println(pice.getMessage());
        }catch(PeriodoControlException pce){
            System.out.println(pce.getMessage());
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
