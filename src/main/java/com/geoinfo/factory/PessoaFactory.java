package com.geoinfo.factory;

import com.geoinfo.entity.Pessoa;
import com.geoinfo.repository.AdministradorRepository;
import com.geoinfo.repository.ClienteRepository;
import com.geoinfo.repository.EstabelecimentoRepository;
import com.geoinfo.repository.VendedorRepository;
import javax.persistence.EntityManager;

public class PessoaFactory {
    
    public static Pessoa create(EntityManager entityManager, String dsUsuario, String dsSenha){
        Pessoa pessoa = new AdministradorRepository(entityManager).find(dsUsuario, dsSenha);
        if(pessoa == null)
            pessoa = new EstabelecimentoRepository(entityManager).find(dsUsuario, dsSenha);
        if(pessoa == null)
            pessoa = new VendedorRepository(entityManager).find(dsUsuario, dsSenha);
        if(pessoa == null)
            pessoa = new ClienteRepository(entityManager).find(dsUsuario, dsSenha);
        return pessoa;
    }
}
