package com.geoinfo.filter;

import com.geoinfo.entity.Fato;
import com.geoinfo.repository.FatoRepository;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(servletNames={"Faces Servlet"})
public class GeoInfoJPAFilter implements Filter{
    private EntityManagerFactory entityManagerFactory;
    
    @Override
    public void init(FilterConfig fc) throws ServletException {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("geoinfo-pu");
        
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        FatoRepository fatoRepository = new FatoRepository(entityManager);
        Fato fato = fatoRepository.find("VLVENDA");
        if(fato == null){
            fato = new Fato();
            fato.setCdFato("VLVENDA");
            fato.setDsFato("Valor de Vendas");
            fato.setIdComportamento((short)0);
            fato.setQtCasaDecimal(2);
            fato.setInFatoVenda(Boolean.TRUE);
            fato.setDsUnidade("real");
            fatoRepository.insert(fato);
        }
        fato = fatoRepository.find("QTVENDA");
        if(fato == null){
            fato = new Fato();
            fato.setCdFato("QTVENDA");
            fato.setDsFato("Quantidade de Vendas");
            fato.setIdComportamento((short)0);
            fato.setQtCasaDecimal(0);
            fato.setInFatoVenda(Boolean.TRUE);
            fato.setDsUnidade("venda");
            fatoRepository.insert(fato);
        }
        fato = fatoRepository.find("QTITEMVENDA");
        if(fato == null){
            fato = new Fato();
            fato.setCdFato("QTITEMVENDA");
            fato.setDsFato("Quantidade de Itens Vendidos");
            fato.setIdComportamento((short)0);
            fato.setQtCasaDecimal(0);
            fato.setInFatoVenda(Boolean.TRUE);
            fato.setDsUnidade("item vendido");
            fatoRepository.insert(fato);
        }
        fato = fatoRepository.find("QTCLIENTE");
        if(fato == null){
            fato = new Fato();
            fato.setCdFato("QTCLIENTE");
            fato.setDsFato("Quantidade de Clientes");
            fato.setIdComportamento((short)0);
            fato.setQtCasaDecimal(0);
            fato.setDsUnidade("cliente");
            fato.setInFatoVenda(Boolean.TRUE);
            
            fatoRepository.insert(fato);
        }
        fato = fatoRepository.find("VLFORMAPAGAMENTO");
        if(fato == null){
            fato = new Fato();
            fato.setCdFato("VLFORMAPAGAMENTO");
            fato.setDsFato("Valor de Formas de Pagamento");
            fato.setIdComportamento((short)0);
            fato.setQtCasaDecimal(2);
            fato.setInFatoVenda(Boolean.TRUE);
            fato.setDsUnidade("real");
            fatoRepository.insert(fato);
        }
        fato = fatoRepository.find("VLCOMISSAO");
        if(fato == null){
            fato = new Fato();
            fato.setCdFato("VLCOMISSAO");
            fato.setDsFato("Valor de Comissões");
            fato.setIdComportamento((short)0);
            fato.setQtCasaDecimal(2);
            fato.setInFatoVenda(Boolean.TRUE);
            fato.setDsUnidade("real");
            fatoRepository.insert(fato);
        }
        fato = fatoRepository.find("VLDESCONTO");
        if(fato == null){
            fato = new Fato();
            fato.setCdFato("VLDESCONTO");
            fato.setDsFato("Valor de Descontos");
            fato.setIdComportamento((short)0);
            fato.setQtCasaDecimal(2);
            fato.setInFatoVenda(Boolean.TRUE);
            fato.setDsUnidade("real");
            fatoRepository.insert(fato);
        }
        
        try{
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        servletRequest.setAttribute("entityManager", entityManager);
        
        filterChain.doFilter(servletRequest, servletResponse);
        
        entityManager.close();
    }

    @Override
    public void destroy() {
        this.entityManagerFactory.close();
    }
    
}
