package com.geoinfo.model;

import com.geoinfo.entity.Fato;
import com.geoinfo.entity.Pessoa;
import com.geoinfo.exception.ObjetoFatoBuilderException;
import com.geoinfo.factory.EObjetoFatoGroupByFactory;
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

                            loadObjetoFatoPeriodo(fato, objetoFato.getObjetoFatoPeriodoA(), idObjetoFatoGroupBy, entityManager);
                            
                            if(objetoFato.isInCompararCom()){
                                loadObjetoFatoPeriodo(fato, objetoFato.getObjetoFatoPeriodoB(), idObjetoFatoGroupBy, entityManager);  
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
            EObjetoFatoGroupBy idObjetoFatoGroupBy, EntityManager entityManager){
        String dsSelect = "select ";
        String dsGroupBy = "group by ";
        switch(idObjetoFatoGroupBy){
            case GROUP_BY_CIDADE:
                dsSelect += "ci, ";
                dsGroupBy += "ci.cidadePK, ci.cdIBGE, ci.dsCidade, ci.cdFID";
                break;
            case GROUP_BY_ESTADO:
                dsSelect += "es, ";
                dsGroupBy += "es.estadoPK, es.cdIBGE, es.cdUF, es.dsEstado, es.cdFID";
                break;
            case GROUP_BY_PAIS:
                dsSelect += "pa, ";
                dsGroupBy += "pa.cdPais, pa.cdBacen, pa.cdGMI, pa.cdISO2, pa.cdISO3, pa.dsPais, pa.cdFID, pa.dsLayerEstado, pa.dsLayerCidade";
                break;
            case GROUP_BY_CLIENTE:
                dsSelect += "cl, ";
                dsGroupBy += "cl.cdPessoa, cl.cdExterno, cl.dsPessoa, cl.dsUsuario, cl.dsSenha, cl.gerente";
                break;
            case GROUP_BY_VENDEDOR:
                dsSelect += "ve, ";
                dsGroupBy += "ve.cdPessoa, ve.cdExterno, ve.dsPessoa, ve.dsUsuario, ve.dsSenha, ve.gerente, ve.vendedor";
                break;
            case GROUP_BY_ESTABELECIMENTO:
                dsSelect += "et, ";
                dsGroupBy += "et.cdPessoa, et.cdExterno, et.dsPessoa, et.dsUsuario, et.dsSenha, et.gerente";
                break;
            case GROUP_BY_PRODUTO:
                dsSelect += "pr, ";
                dsGroupBy += "pr.produtoPK, pr.dsProduto";
                break;
            case GROUP_BY_FORMAPAGAMENTO:
                dsSelect += "fp, ";
                dsGroupBy += "fp.formaPagamentoPK, fp.dsFormaPagamento";
                break;
            case GROUP_BY_VENDA:
                dsSelect += "v, ";
                dsGroupBy += "v.vendaPK, v.cliente, v.dtVenda, v.vlDesconto, v.idStatus";
                break;
        }
        if(fato.isInFatoVenda()){
            if(fato.getCdFato().equalsIgnoreCase("VLVENDA")){
                dsSelect += "sum(iv.vlProduto * iv.qtProduto) ";
                dsSelect += "from Localizacao loc, ItemVenda iv "
                          + "inner join iv.itemVendaPK.venda v ";
            }else if(fato.getCdFato().equalsIgnoreCase("QTVENDA")){
                dsSelect += "count(v.cdVenda) ";
                dsSelect += "from Localizacao loc, Venda v ";
            }else if(fato.getCdFato().equalsIgnoreCase("QTITEMVENDA")){
                dsSelect += "sum(iv.qtProduto) ";
                dsSelect += "from Localizacao loc, ItemVenda iv "
                          + "inner join iv.itemVendaPK.venda v ";
            }else if(fato.getCdFato().equalsIgnoreCase("QTCLIENTE")){
                dsSelect += "count(distinct v.cliente) ";
                dsSelect += "from Localizacao loc, Venda v ";
            }else if(fato.getCdFato().equalsIgnoreCase("VLFORMAPAGAMENTO")){
                dsSelect += "sum(fv.vlFormaPagamento) ";
                dsSelect += "from Localizacao loc, FormaPagamentoVenda fv "
                          + "inner join fv.formaPagamentoVendaPK.venda v ";
            }else if(fato.getCdFato().equalsIgnoreCase("VLCOMISSAO")){
                dsSelect += "sum(vv.vlComissao) ";
                dsSelect += "from Localizacao loc, VendedorVenda vv "
                          + "inner join vv.vendedorVendaPK.venda v ";
            }else if(fato.getCdFato().equalsIgnoreCase("VLDESCONTO")){
                dsSelect += "sum(v.vlDesconto) ";
                dsSelect += "from Localizacao loc, Venda v ";
            }
            dsSelect += "inner join v.cliente cl "
                      + "inner join loc.localizacaoPK.cidade ci "
                      + "inner join ci.cidadePK.estado es "
                      + "inner join es.estadoPK.pais pa "
                      + "where cl = loc.localizacaoPK.pessoa "
                      + "and loc.localizacaoPK.dtLocalizacao = "
                      + "(select max(loc1.localizacaoPK.dtLocalizacao) "
                      + " from Localizacao loc1 "
                      + " where loc1.localizacaoPK.pessoa = cl "
                      + " and loc1.localizacaoPK.dtLocalizacao <= v.dtVenda) ";
            switch(objetoFatoPeriodo.getPeriodoIntervalo().getIdPeriodo()){
                case ANO:
                    dsSelect += "and year(v.dtVenda)";
                    break;
                case MES:
                    dsSelect += "and cast((year(v.dtVenda) * 100) + month(v.dtVenda) as integer)";
                    break;
                case DATA:
                    dsSelect += "and cast((year(v.dtVenda) * 10000) + (month(v.dtVenda) * 100) + day(v.dtVenda) as integer)";
                    break;
            }
            
            if(objetoFatoPeriodo.getPeriodoIntervalo().isInAte())
                dsSelect += " between " + objetoFatoPeriodo.getPeriodoIntervalo().getCdPeriodoA() +
                        " and " + objetoFatoPeriodo.getPeriodoIntervalo().getCdPeriodoB();
            else
                dsSelect += " = " + objetoFatoPeriodo.getPeriodoIntervalo().getCdPeriodoA();
        }
        dsSelect += dsGroupBy;
        
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
