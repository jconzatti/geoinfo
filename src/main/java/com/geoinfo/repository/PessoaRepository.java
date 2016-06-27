package com.geoinfo.repository;

import com.geoinfo.entity.Pessoa;
import com.geoinfo.entity.PessoaMaster;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class PessoaRepository<K extends Pessoa> extends Repository<K, Long>{

    public PessoaRepository(EntityManager manager, Class<K> classEntity) {
        super(manager, classEntity);
    }
    
    public K find(PessoaMaster gerente, String cdExterno){
        Query query = this.manager.createQuery("select p from " + getClassEntityName() + " p where p.gerente = :gerente and p.cdExterno = :cdExterno").
                setParameter("gerente", gerente).setParameter("cdExterno", cdExterno);
        List<Pessoa> listaPessoa = query.getResultList();
        if (listaPessoa.isEmpty())
            return null;
        else
            return (K) listaPessoa.get(0);
    }
    
    public K find(String dsUsuario){
        Query query = this.manager.createQuery("select p from " + getClassEntityName() + " p where p.dsUsuario = :dsUsuario").
                setParameter("dsUsuario", dsUsuario);
        List<Pessoa> listaPessoa = query.getResultList();
        if (listaPessoa.isEmpty())
            return null;
        else
            return (K) listaPessoa.get(0);
    }
    
    public K find(String dsUsuario, String dsSenha){
        try {
            String dsSenhaMD5 = Pessoa.encriptDsSenha(dsSenha);
            
            Query query = this.manager.createQuery("select p from " + getClassEntityName() + " p where "
                    + "p.dsUsuario = :dsUsuario and p.dsSenha = :dsSenha").
                    setParameter("dsUsuario", dsUsuario).setParameter("dsSenha", dsSenhaMD5);
            List<Pessoa> listaPessoa = query.getResultList();
            if (!listaPessoa.isEmpty())
                return (K) listaPessoa.get(0);
        } catch (NoSuchAlgorithmException ex) { }
        return null;
    }
    
    
}
