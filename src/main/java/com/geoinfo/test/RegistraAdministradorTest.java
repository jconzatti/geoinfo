package com.geoinfo.test;

import com.geoinfo.entity.Cidade;
import com.geoinfo.entity.CidadePK;
import com.geoinfo.entity.Administrador;
import com.geoinfo.entity.Estado;
import com.geoinfo.entity.EstadoPK;
import com.geoinfo.entity.Localizacao;
import com.geoinfo.entity.LocalizacaoPK;
import com.geoinfo.entity.Pais;
import com.geoinfo.repository.CidadeRepository;
import com.geoinfo.repository.AdministradorRepository;
import com.geoinfo.repository.EstadoRepository;
import com.geoinfo.repository.LocalizacaoRepository;
import com.geoinfo.repository.PaisRepository;
import java.util.Calendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RegistraAdministradorTest {
    
    public static void main(String args[]){
        EntityManagerFactory  entityManagerFactory = Persistence.createEntityManagerFactory("geoinfo-pu");
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        Pais pais = new PaisRepository(entityManager).find(new Long(103));
        
        EstadoPK estadoPK =  new EstadoPK();
        estadoPK.setPais(pais);
        estadoPK.setCdEstado(new Long(24));
        Estado estado = new EstadoRepository(entityManager).find(estadoPK);
        
        AdministradorRepository administradorRepository = new AdministradorRepository(entityManager);
        Administrador administrador = administradorRepository.find(1L);
        if(administrador == null){
            administrador = new Administrador();
            administrador.setDsPessoa("Jhoni Conzatti");
            administrador.setDsUsuario("jconzatti");
            administrador.setDsSenha("080690");
            administrador.setGerente(null);
            administradorRepository.insert(administrador);

            CidadePK cidadePK = new CidadePK();
            cidadePK.setEstado(estado);
            cidadePK.setCdCidade(new Long(4810));
            Cidade cidade = new CidadeRepository(entityManager).find(cidadePK);

            LocalizacaoRepository localizacaoRepository = new LocalizacaoRepository(entityManager);
            LocalizacaoPK localizacaoPK = new LocalizacaoPK();
            localizacaoPK.setCidade(cidade);
            localizacaoPK.setPessoa(administrador);
            localizacaoPK.setDtLocalizacao(Calendar.getInstance().getTime());

            Localizacao localizacao = new Localizacao();
            localizacao.setLocalizacaoPK(localizacaoPK);
            localizacao.setDsEndereco("Rua Quinze de Novembro, Centro");
            localizacao.setDsNumero("120");
            localizacaoRepository.insert(localizacao);
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
