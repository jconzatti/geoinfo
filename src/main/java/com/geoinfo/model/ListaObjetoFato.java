package com.geoinfo.model;

import com.geoinfo.entity.Cliente;
import com.geoinfo.entity.Estabelecimento;
import com.geoinfo.entity.Fato;
import com.geoinfo.entity.Pessoa;
import com.geoinfo.entity.Vendedor;
import com.geoinfo.exception.ObjetoFatoBuilderException;
import com.geoinfo.factory.EObjetoFatoGroupByFactory;
import com.geoinfo.repository.VendedorRepository;
import com.geoinfo.util.EObjetoFatoGroupBy;
import com.geoinfo.util.IGroupable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ListaObjetoFato<T extends IGroupable> {
    private final List<ObjetoFato<T>> listaObjetoFato;

    public static ListaObjetoFato create(List<Fato> listaFato, 
            PeriodoIntervaloComparavel periodoIntervaloComparavel, 
            EntityManager entityManager, 
            Class<? extends IGroupable> classObjetoFatoBuilder,
            Pessoa pessoaLogada) throws ObjetoFatoBuilderException {
        
        EObjetoFatoGroupBy idObjetoFatoGroupBy = EObjetoFatoGroupByFactory.create(classObjetoFatoBuilder);
        
        if(idObjetoFatoGroupBy != null){
            if(listaFato != null){
                if(periodoIntervaloComparavel != null){
                    if(entityManager != null){
                        ListaObjetoFato listaObjetoFato = new ListaObjetoFato();
                        for(Fato fato : listaFato){
                            ObjetoFato objetoFato = new ObjetoFato(fato, periodoIntervaloComparavel);

                            loadObjetoFatoPeriodo(fato, objetoFato.getObjetoFatoPeriodoA(), idObjetoFatoGroupBy, entityManager, pessoaLogada);
                            
                            if(objetoFato.isInCompararCom()){
                                loadObjetoFatoPeriodo(fato, objetoFato.getObjetoFatoPeriodoB(), idObjetoFatoGroupBy, entityManager, pessoaLogada);  
                            }
                            
                            listaObjetoFato.listaObjetoFato.add(objetoFato);
                        }
                        return listaObjetoFato;
                    }else{
                        throw new ObjetoFatoBuilderException("Gerenciador de endidade não pode ser nulo!");
                    }
                }else{
                    throw new ObjetoFatoBuilderException("Período de data comparável não pode ser nulo!");
                }
            }else{
                throw new ObjetoFatoBuilderException("Fato não pode ser nulo!");
            }
        }else{
            throw new ObjetoFatoBuilderException("O agrupador de fato não pode ser nulo!");
        }
    }
    
    private static void loadObjetoFatoPeriodo(Fato fato, ObjetoFatoPeriodo objetoFatoPeriodo,
            EObjetoFatoGroupBy idObjetoFatoGroupBy, EntityManager entityManager,
            Pessoa pessoaLogada){
        StringBuffer sbufferHQL = new StringBuffer();
        sbufferHQL.append("select ");
        String dsGroupBy = " group by ";
        switch(idObjetoFatoGroupBy){
            case GROUP_BY_CIDADE:
                sbufferHQL.append("ci, ");
                dsGroupBy += "ci.cidadePK, ci.cdIBGE, ci.dsCidade, ci.cdFID";
                break;
            case GROUP_BY_ESTADO:
                sbufferHQL.append("es, ");
                dsGroupBy += "es.estadoPK, es.cdIBGE, es.cdUF, es.dsEstado, es.cdFID";
                break;
            case GROUP_BY_PAIS:
                sbufferHQL.append("pa, ");
                dsGroupBy += "pa.cdPais, pa.cdBacen, pa.cdGMI, pa.cdISO2, pa.cdISO3, pa.dsPais, pa.cdFID, pa.dsLayerEstado, pa.dsLayerCidade";
                break;
            case GROUP_BY_CLIENTE:
                sbufferHQL.append("cl, ");
                dsGroupBy += "cl.cdPessoa, cl.cdExterno, cl.dsPessoa, cl.dsUsuario, cl.dsSenha, cl.gerente";
                break;
            case GROUP_BY_VENDEDOR:
                sbufferHQL.append("ve, ");
                dsGroupBy += "ve.cdPessoa, ve.cdExterno, ve.dsPessoa, ve.dsUsuario, ve.dsSenha, ve.gerente, ve.vendedor";
                break;
            case GROUP_BY_ESTABELECIMENTO:
                sbufferHQL.append("et, ");
                dsGroupBy += "et.cdPessoa, et.cdExterno, et.dsPessoa, et.dsUsuario, et.dsSenha, et.gerente";
                break;
            case GROUP_BY_PRODUTO:
                sbufferHQL.append("pr, ");
                dsGroupBy += "pr.produtoPK, pr.dsProduto";
                break;
            case GROUP_BY_FORMAPAGAMENTO:
                sbufferHQL.append("fp, ");
                dsGroupBy += "fp.formaPagamentoPK, fp.dsFormaPagamento";
                break;
            case GROUP_BY_VENDA:
                sbufferHQL.append("v, ");
                dsGroupBy += "v.vendaPK, v.cliente, v.dtVenda, v.vlDesconto, v.idStatus";
                break;
        }
        
        boolean inVendedorVenda = false;
        if(fato.isInFatoVenda()){
            if(fato.getCdFato().equalsIgnoreCase("VLVENDA")){
                sbufferHQL.append("sum(iv.vlProduto * iv.qtProduto) ");
                sbufferHQL.append("from ItemVenda iv ");
                sbufferHQL.append("inner join iv.itemVendaPK.venda v, ");
            }else if(fato.getCdFato().equalsIgnoreCase("QTVENDA")){
                sbufferHQL.append("count(v.vendaPK.cdVenda) ");
                sbufferHQL.append("from Venda v, ");
            }else if(fato.getCdFato().equalsIgnoreCase("QTITEMVENDA")){
                sbufferHQL.append("sum(iv.qtProduto) ");
                sbufferHQL.append("from ItemVenda iv ");
                sbufferHQL.append("inner join iv.itemVendaPK.venda v, ");
            }else if(fato.getCdFato().equalsIgnoreCase("QTCLIENTE")){
                sbufferHQL.append("count(distinct v.cliente) ");
                sbufferHQL.append("from Venda v, ");
            }else if(fato.getCdFato().equalsIgnoreCase("VLFORMAPAGAMENTO")){
                sbufferHQL.append("sum(fv.vlFormaPagamento) ");
                sbufferHQL.append("from FormaPagamentoVenda fv ");
                sbufferHQL.append("inner join fv.formaPagamentoVendaPK.venda v, ");
            }else if(fato.getCdFato().equalsIgnoreCase("VLCOMISSAO")){
                sbufferHQL.append("sum(vv.vlComissao) ");
                sbufferHQL.append("from VendedorVenda vv ");
                sbufferHQL.append("inner join vv.vendedorVendaPK.venda v, ");
                inVendedorVenda = true;
            }else if(fato.getCdFato().equalsIgnoreCase("VLDESCONTO")){
                sbufferHQL.append("sum(v.vlDesconto) ");
                sbufferHQL.append("from Venda v, ");
            }
            sbufferHQL.append("LocalizacaoDestinoVenda loc ");
            sbufferHQL.append("inner join loc.cidade ci ");
            sbufferHQL.append("inner join ci.cidadePK.estado es ");
            sbufferHQL.append("inner join es.estadoPK.pais pa ");
            
            if((pessoaLogada instanceof Vendedor)&&(!inVendedorVenda))
                sbufferHQL.append(", VendedorVenda vv ");
            
            sbufferHQL.append("where loc.localizacaoDestinoVendaPK.venda = v ");
            if((pessoaLogada instanceof Vendedor)&&(!inVendedorVenda))
                sbufferHQL.append("and vv.vendedorVendaPK.venda = v ");
            switch(objetoFatoPeriodo.getPeriodoIntervalo().getIdPeriodo()){
                case ANO:
                    sbufferHQL.append("and year(v.dtVenda)");
                    break;
                case MES:
                    sbufferHQL.append("and cast((year(v.dtVenda) * 100) + month(v.dtVenda) as integer)");
                    break;
                case DATA:
                    sbufferHQL.append("and cast((year(v.dtVenda) * 10000) + (month(v.dtVenda) * 100) + day(v.dtVenda) as integer)");
                    break;
            }
            
            if(objetoFatoPeriodo.getPeriodoIntervalo().isInAte()){
                sbufferHQL.append(" between ");
                sbufferHQL.append(objetoFatoPeriodo.getPeriodoIntervalo().getCdPeriodoA());
                sbufferHQL.append(" and ");
                sbufferHQL.append(objetoFatoPeriodo.getPeriodoIntervalo().getCdPeriodoB());
            }else{
                sbufferHQL.append(" = ");
                sbufferHQL.append(objetoFatoPeriodo.getPeriodoIntervalo().getCdPeriodoA());
            }
            sbufferHQL.append(" and v.idStatus = 0");
            if(pessoaLogada.getInGerente()){
                sbufferHQL.append(" and v.vendaPK.estabelecimento.gerente.cdPessoa = "); 
                sbufferHQL.append(pessoaLogada.getCdPessoa());
            }else{
                if(pessoaLogada instanceof Estabelecimento){
                    sbufferHQL.append(" and v.vendaPK.estabelecimento.cdPessoa = "); 
                    sbufferHQL.append(pessoaLogada.getCdPessoa());
                }else if(pessoaLogada instanceof Cliente){
                    sbufferHQL.append(" and v.cliente.cdPessoa = "); 
                    sbufferHQL.append(pessoaLogada.getCdPessoa());
                }else if(pessoaLogada instanceof Vendedor){
                    sbufferHQL.append(" and ("); 
                    VendedorRepository vendedorRepository = new VendedorRepository(entityManager);
                    List<Vendedor> listaVendedor = vendedorRepository.getListaSubVendedor((Vendedor)pessoaLogada);
                    int qtVendedor = 0;
                    for(Vendedor vendedor : listaVendedor){
                        qtVendedor++;
                        sbufferHQL.append("vv.vendedor.cdPessoa = "); 
                        sbufferHQL.append(vendedor.getCdPessoa());
                        if(qtVendedor < listaVendedor.size())
                            sbufferHQL.append(" or "); 
                    }
                    sbufferHQL.append(")"); 
                }
            }
        }
        sbufferHQL.append(dsGroupBy);
        String dsSelect = sbufferHQL.toString();
        
        Query query = entityManager.createQuery(dsSelect);
        Iterator<Object[]> itObjetoFatoValor = query.getResultList().iterator();
        while(itObjetoFatoValor.hasNext()){
            Object[] oLinha = itObjetoFatoValor.next();
            String dsValor = fato.getDecimalFormat().format(Double.parseDouble(String.valueOf(oLinha[1])));
            dsValor = dsValor.replace(".", "");
            dsValor = dsValor.replace(",", ".");
            objetoFatoPeriodo.putVlItem((IGroupable)oLinha[0], Double.valueOf(dsValor));
        }
    }

    private ListaObjetoFato() {
        this.listaObjetoFato = new ArrayList<ObjetoFato<T>>();
    }
    
    public List<ObjetoFato<T>> getLista(){
        return this.listaObjetoFato;
    }
    
}
