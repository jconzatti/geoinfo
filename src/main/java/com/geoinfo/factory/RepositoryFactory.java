package com.geoinfo.factory;

import com.geoinfo.repository.*;
import com.geoinfo.util.IGroupable;
import com.geoinfo.util.IRepositorable;
import javax.persistence.EntityManager;

public class RepositoryFactory {

    public static Repository createByRepositorable(Class<? extends IRepositorable> classIRepositorable, EntityManager entityManager){
        if (classIRepositorable.getSimpleName().equalsIgnoreCase("Pais")){
            return new PaisRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("Estado")){
            return new EstadoRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("Cidade")){
            return new CidadeRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("Localizacao")){
            return new LocalizacaoRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("Administrador")){
            return new AdministradorRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("Estabelecimento")){
            return new EstabelecimentoRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("Vendedor")){
            return new VendedorRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("Cliente")){
            return new ClienteRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("Produto")){
            return new ProdutoRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("FormaPagamento")){
            return new FormaPagamentoRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("Venda")){
            return new VendaRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("VendedorVenda")){
            return new VendedorVendaRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("FormaPagamentoVenda")){
            return new FormaPagamentoVendaRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("ItemVenda")){
            return new ItemVendaRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("Fato")){
            return new FatoRepository(entityManager);
        }else if (classIRepositorable.getSimpleName().equalsIgnoreCase("LocalizacaoDestinoVenda")){
            return new LocalizacaoDestinoVendaRepository(entityManager);
        }
        return null;
    }

    public static Repository createByGroupable(Class<? extends IGroupable> classIGroupable, EntityManager entityManager){
        if (classIGroupable.getSimpleName().equalsIgnoreCase("Pais")){
            return new PaisRepository(entityManager);
        }else if (classIGroupable.getSimpleName().equalsIgnoreCase("Estado")){
            return new EstadoRepository(entityManager);
        }else if (classIGroupable.getSimpleName().equalsIgnoreCase("Cidade")){
            return new CidadeRepository(entityManager);
        }else if (classIGroupable.getSimpleName().equalsIgnoreCase("Administrador")){
            return new AdministradorRepository(entityManager);
        }else if (classIGroupable.getSimpleName().equalsIgnoreCase("Estabelecimento")){
            return new EstabelecimentoRepository(entityManager);
        }else if (classIGroupable.getSimpleName().equalsIgnoreCase("Vendedor")){
            return new VendedorRepository(entityManager);
        }else if (classIGroupable.getSimpleName().equalsIgnoreCase("Cliente")){
            return new ClienteRepository(entityManager);
        }else if (classIGroupable.getSimpleName().equalsIgnoreCase("Produto")){
            return new ProdutoRepository(entityManager);
        }else if (classIGroupable.getSimpleName().equalsIgnoreCase("FormaPagamento")){
            return new FormaPagamentoRepository(entityManager);
        }else if (classIGroupable.getSimpleName().equalsIgnoreCase("Venda")){
            return new VendaRepository(entityManager);
        }
        return null;
    }
}
