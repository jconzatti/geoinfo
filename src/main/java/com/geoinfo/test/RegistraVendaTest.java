package com.geoinfo.test;

import com.geoinfo.entity.Cidade;
import com.geoinfo.entity.CidadePK;
import com.geoinfo.entity.Cliente;
import com.geoinfo.entity.Estabelecimento;
import com.geoinfo.entity.Estado;
import com.geoinfo.entity.EstadoPK;
import com.geoinfo.entity.FormaPagamento;
import com.geoinfo.entity.FormaPagamentoPK;
import com.geoinfo.entity.FormaPagamentoVenda;
import com.geoinfo.entity.FormaPagamentoVendaPK;
import com.geoinfo.entity.ItemVenda;
import com.geoinfo.entity.ItemVendaPK;
import com.geoinfo.entity.Localizacao;
import com.geoinfo.entity.LocalizacaoPK;
import com.geoinfo.entity.Pais;
import com.geoinfo.entity.Produto;
import com.geoinfo.entity.ProdutoPK;
import com.geoinfo.entity.Venda;
import com.geoinfo.entity.VendaPK;
import com.geoinfo.entity.Vendedor;
import com.geoinfo.entity.VendedorVenda;
import com.geoinfo.entity.VendedorVendaPK;
import com.geoinfo.repository.CidadeRepository;
import com.geoinfo.repository.ClienteRepository;
import com.geoinfo.repository.EstabelecimentoRepository;
import com.geoinfo.repository.EstadoRepository;
import com.geoinfo.repository.FormaPagamentoRepository;
import com.geoinfo.repository.FormaPagamentoVendaRepository;
import com.geoinfo.repository.ItemVendaRepository;
import com.geoinfo.repository.LocalizacaoRepository;
import com.geoinfo.repository.PaisRepository;
import com.geoinfo.repository.ProdutoRepository;
import com.geoinfo.repository.VendaRepository;
import com.geoinfo.repository.VendedorRepository;
import com.geoinfo.repository.VendedorVendaRepository;
import java.util.Calendar;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RegistraVendaTest {
    
    public static void main(String args[]){
        EntityManagerFactory  entityManagerFactory = Persistence.createEntityManagerFactory("geoinfo-pu");
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        Scanner entrada = new Scanner(System.in);
        
        Pais pais = new PaisRepository(entityManager).find(new Long(103));
        
        EstadoPK estadoPK =  new EstadoPK();
        estadoPK.setPais(pais);
        estadoPK.setCdEstado(new Long(24));
        Estado estado = new EstadoRepository(entityManager).find(estadoPK);
        
        EstabelecimentoRepository estabelecimentoRepository = new EstabelecimentoRepository(entityManager);
        Estabelecimento estabelecimento = estabelecimentoRepository.find(1L);
        if(estabelecimento == null){
            estabelecimento = new Estabelecimento();
            estabelecimento.setDsPessoa("JHC Comercio de Vestuario Ltda");
            estabelecimento.setDsUsuario("jhc");
            estabelecimento.setDsSenha("123456");
            estabelecimento.setGerente(estabelecimento);
            estabelecimentoRepository.insert(estabelecimento);

            CidadePK cidadePK = new CidadePK();
            cidadePK.setEstado(estado);
            cidadePK.setCdCidade(new Long(4810));
            Cidade cidade = new CidadeRepository(entityManager).find(cidadePK);

            LocalizacaoRepository localizacaoRepository = new LocalizacaoRepository(entityManager);
            LocalizacaoPK localizacaoPK = new LocalizacaoPK();
            localizacaoPK.setCidade(cidade);
            localizacaoPK.setPessoa(estabelecimento);
            localizacaoPK.setDtLocalizacao(Calendar.getInstance().getTime());

            Localizacao localizacao = new Localizacao();
            localizacao.setLocalizacaoPK(localizacaoPK);
            localizacao.setDsEndereco("Rua Quinze de Novembro, Centro");
            localizacao.setDsNumero("120");
            localizacaoRepository.insert(localizacao);
        }
        
        String inRegistraVenda = "S";
        
        do{
            ClienteRepository clienteRepository = new ClienteRepository(entityManager);
            System.out.println("Digite o código do cliente:");
            Cliente cliente = clienteRepository.find(Long.parseLong(entrada.nextLine()));
            if(cliente == null){
                cliente = new Cliente();
                cliente.setGerente(estabelecimento);
                System.out.println("Digite o nome do cliente:");
                cliente.setDsPessoa(entrada.nextLine());
                System.out.println("Digite o usuário do cliente:");
                cliente.setDsUsuario(entrada.nextLine());
                System.out.println("Digite a senha do cliente:");
                cliente.setDsSenha(entrada.nextLine());
                clienteRepository.insert(cliente);

                CidadePK cidadePK = new CidadePK();
                cidadePK.setEstado(estado);
                System.out.println("Digite o código da cidade do cliente:");
                cidadePK.setCdCidade(Long.parseLong(entrada.nextLine()));
                Cidade cidade = new CidadeRepository(entityManager).find(cidadePK);

                LocalizacaoRepository localizacaoRepository = new LocalizacaoRepository(entityManager);
                LocalizacaoPK localizacaoPK = new LocalizacaoPK();
                localizacaoPK.setCidade(cidade);
                localizacaoPK.setPessoa(cliente);
                localizacaoPK.setDtLocalizacao(Calendar.getInstance().getTime());

                Localizacao localizacao = new Localizacao();
                localizacao.setLocalizacaoPK(localizacaoPK);
                System.out.println("Digite o endereço do cliente:");
                localizacao.setDsEndereco(entrada.nextLine());
                System.out.println("Digite o número do endereço do cliente:");
                localizacao.setDsNumero(entrada.nextLine());
                localizacaoRepository.insert(localizacao);
            }
            
            VendaRepository vendaRepository = new VendaRepository(entityManager);
            VendaPK vendaPK = new VendaPK();
            vendaPK.setEstabelecimento(estabelecimento);
            System.out.println("Digite o código da venda:");
            vendaPK.setCdVenda(Long.parseLong(entrada.nextLine()));
            Venda venda = new Venda();
            venda.setVendaPK(vendaPK);
            venda.setCliente(cliente);
            venda.setDtVenda(Calendar.getInstance().getTime());
            venda.setIdStatus(new Short((short) 1));
            venda.setVlDesconto(new Double(0));
            vendaRepository.insert(venda);
            
            String inProximoItem = "";
            int cdItemVenda = 0;
            double vlVenda = 0;
            do{
                cdItemVenda++;
                ProdutoRepository produtoRepository = new ProdutoRepository(entityManager);
                System.out.println("Digite o código do produto:");
                String cdProduto = entrada.nextLine();
                ProdutoPK produtoPK = new ProdutoPK();
                produtoPK.setGerente(estabelecimento);
                produtoPK.setCdProduto(cdProduto);
                Produto produto = produtoRepository.find(produtoPK);
                if(produto == null){
                    produto = new Produto();
                    produto.setProdutoPK(produtoPK);
                    System.out.println("Digite a descrição do produto:");
                    produto.setDsProduto(entrada.nextLine());
                    produtoRepository.insert(produto);
                }
                
                ItemVendaRepository itemVendaRepository = new ItemVendaRepository(entityManager);
                ItemVendaPK itemVendaPK = new ItemVendaPK();
                itemVendaPK.setVenda(venda);
                itemVendaPK.setCdItemVenda(new Long(cdItemVenda));
                ItemVenda itemVenda = new ItemVenda();
                itemVenda.setItemVendaPK(itemVendaPK);
                itemVenda.setProduto(produto);
                System.out.println("Digite a quantidade de itens:");
                double qtProduto = Double.parseDouble(entrada.nextLine());
                itemVenda.setQtProduto(qtProduto);
                System.out.println("Digite o valor unitário do item:");
                double vlProduto = Double.parseDouble(entrada.nextLine());
                itemVenda.setVlProduto(vlProduto);
                itemVendaRepository.insert(itemVenda);
                
                vlVenda += qtProduto * vlProduto;
                
                System.out.println("Regitrar próximo item? [S/N]");
                inProximoItem = entrada.nextLine();
            }while(inProximoItem.equalsIgnoreCase("S"));
            
            FormaPagamentoRepository formaPagamentoRepository = new FormaPagamentoRepository(entityManager);
            FormaPagamentoPK formaPagamentoPK = new FormaPagamentoPK();
            formaPagamentoPK.setGerente(estabelecimento);
            formaPagamentoPK.setCdFormaPagamento("1");
            FormaPagamento formaPagamento = formaPagamentoRepository.find(formaPagamentoPK);
            if(formaPagamento == null){
                formaPagamento = new FormaPagamento();
                formaPagamento.setFormaPagamentoPK(formaPagamentoPK);
                formaPagamento.setDsFormaPagamento("Dinheiro");
                formaPagamentoRepository.insert(formaPagamento);
            }
            
            FormaPagamentoVendaRepository formaPagamentoVendaRepository = new  FormaPagamentoVendaRepository(entityManager);
            FormaPagamentoVendaPK formaPagamentoVendaPK = new FormaPagamentoVendaPK();
            formaPagamentoVendaPK.setVenda(venda);
            formaPagamentoVendaPK.setCdFormaPagamentoVenda(new Long(1));
            FormaPagamentoVenda formaPagamentoVenda = new FormaPagamentoVenda();
            formaPagamentoVenda.setFormaPagamentoVendaPK(formaPagamentoVendaPK);
            formaPagamentoVenda.setFormaPagamento(formaPagamento);
            formaPagamentoVenda.setVlFormaPagamento(vlVenda);
            formaPagamentoVendaRepository.insert(formaPagamentoVenda);
            
            VendedorRepository vendedorRepository = new VendedorRepository(entityManager);
            System.out.println("Digite o código do vendedor:");
            Vendedor vendedor = vendedorRepository.find(Long.parseLong(entrada.nextLine()));
            if(vendedor == null){
                vendedor = new Vendedor();
                vendedor.setGerente(estabelecimento);
                System.out.println("Digite o nome do vendedor:");
                vendedor.setDsPessoa(entrada.nextLine());
                System.out.println("Digite o usuário do vendedor:");
                vendedor.setDsUsuario(entrada.nextLine());
                System.out.println("Digite a senha do vendedor:");
                vendedor.setDsSenha(entrada.nextLine());
                vendedorRepository.insert(vendedor);

                CidadePK cidadePK = new CidadePK();
                cidadePK.setEstado(estado);
                System.out.println("Digite o código da cidade do vendedor:");
                cidadePK.setCdCidade(Long.parseLong(entrada.nextLine()));
                Cidade cidade = new CidadeRepository(entityManager).find(cidadePK);

                LocalizacaoRepository localizacaoRepository = new LocalizacaoRepository(entityManager);
                LocalizacaoPK localizacaoPK = new LocalizacaoPK();
                localizacaoPK.setCidade(cidade);
                localizacaoPK.setPessoa(vendedor);
                localizacaoPK.setDtLocalizacao(Calendar.getInstance().getTime());

                Localizacao localizacao = new Localizacao();
                localizacao.setLocalizacaoPK(localizacaoPK);
                System.out.println("Digite o endereço do vendedor:");
                localizacao.setDsEndereco(entrada.nextLine());
                System.out.println("Digite o número do endereço do vendedor:");
                localizacao.setDsNumero(entrada.nextLine());
                localizacaoRepository.insert(localizacao);
            }
   
            VendedorVendaRepository vendedorVendaRepository = new  VendedorVendaRepository(entityManager);
            VendedorVendaPK vendedorVendaPK = new VendedorVendaPK();
            vendedorVendaPK.setVenda(venda);
            vendedorVendaPK.setCdVendedorVenda(new Long(1));
            VendedorVenda vendedorVenda = new VendedorVenda();
            vendedorVenda.setVendedorVendaPK(vendedorVendaPK);
            vendedorVenda.setVendedor(vendedor);
            vendedorVenda.setVlComissao(vlVenda * 0.05);
            vendedorVendaRepository.insert(vendedorVenda);
            
            System.out.println("Regitrar próxima venda? [S/N]");
            inRegistraVenda = entrada.nextLine();
        }while(inRegistraVenda.equalsIgnoreCase("S"));
        
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
