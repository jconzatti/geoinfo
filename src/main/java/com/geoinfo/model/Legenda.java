package com.geoinfo.model;

import com.geoinfo.exception.LegendaException;
import com.geoinfo.util.EObjetoFatoProporcao;
import com.geoinfo.util.EObjetoFatoValor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Legenda<T extends ItemLegenda> {
    private String dsLegenda;
    private final List<T> listaItemLegenda;
    
    private Legenda(String dsLegenda) {
        this.dsLegenda = dsLegenda;
        this.listaItemLegenda = new ArrayList<T>();
    }

    public String getDsLegenda() {
        return dsLegenda;
    }

    public void setDsLegenda(String dsLegenda) {
        this.dsLegenda = dsLegenda;
    }

    public List<T> getListaItemLegenda() {
        return listaItemLegenda;
    }
    
    public Color getCor(Double vlItemA, Double vlItemB){
        ObjetoFatoProporcao objetoFatoProporcao = new ObjetoFatoProporcao(vlItemA, vlItemB);
        
        for(T itemLegenda : listaItemLegenda){
            if(itemLegenda instanceof ItemLegendaValor){
                ItemLegendaValor ilv = ((ItemLegendaValor)itemLegenda);
                switch(ilv.getIdObjetoFatoValor()){
                    case ATE:
                    case MENORIGUAL:
                        if(vlItemA.doubleValue() <= ilv.getVlItemLegenda().doubleValue()){
                            return ilv.getCorItemLegenda();
                        }
                        break;
                    case IGUAL:
                        if(vlItemA.doubleValue() == ilv.getVlItemLegenda().doubleValue()){
                            return ilv.getCorItemLegenda();
                        }
                        break;
                    case MAIORQUE:
                        if(vlItemA.doubleValue() > ilv.getVlItemLegenda().doubleValue()){
                            return ilv.getCorItemLegenda();
                        }
                        break;
                    case MENORQUE:
                        if(vlItemA.doubleValue() < ilv.getVlItemLegenda().doubleValue()){
                            return ilv.getCorItemLegenda();
                        }
                        break;
                    case MAIORIGUAL:
                        if(vlItemA.doubleValue() >= ilv.getVlItemLegenda().doubleValue()){
                            return ilv.getCorItemLegenda();
                        }
                        break;
                    case DIFERENTE:
                        if(vlItemA.doubleValue() != ilv.getVlItemLegenda().doubleValue()){
                            return ilv.getCorItemLegenda();
                        }
                        break;
                }
            }else{
                if(itemLegenda instanceof ItemLegendaProporcao){
                    ItemLegendaProporcao ilp = ((ItemLegendaProporcao)itemLegenda);
                    if(objetoFatoProporcao.getEObjetoFatoValorProporcao() == ilp.getIdObjetoFatoProporcao()){
                        return ilp.getCorItemLegenda();
                    }
                }
            }
        }
        return null;
    }
    
    public static Legenda create(List<Color> listaCor, ObjetoFato objetoFato) throws LegendaException{
        if(objetoFato != null){
            if((listaCor != null)&&(listaCor.size() > 0)){
                if(objetoFato.getObjetoFatoPeriodoA() != null){
                    if(objetoFato.getObjetoFatoPeriodoB() != null){
                        Legenda<ItemLegendaProporcao> legendaProporcao = new Legenda<ItemLegendaProporcao>(objetoFato.getFato().getDsFato());
                        for(int i = 0; (i < EObjetoFatoProporcao.values().length)&&(i < listaCor.size()); i++){
                            legendaProporcao.getListaItemLegenda().add(new ItemLegendaProporcao(listaCor.get(i), EObjetoFatoProporcao.values()[i]));
                        }
                        return legendaProporcao;
                    }else{
                        Legenda<ItemLegendaValor> legendaValor = new Legenda<ItemLegendaValor>(objetoFato.getFato().getDsFato());
                        
                        double vlIntervalo = 0;
                        if(listaCor.size() > 1){
                            vlIntervalo = objetoFato.getObjetoFatoPeriodoA().getVlMaximo()/(listaCor.size()-1);
                        }
                        
                        for(int i = 0; i < listaCor.size(); i++){
                            if(i == 0){
                                legendaValor.getListaItemLegenda().add(new ItemLegendaValor(listaCor.get(i), EObjetoFatoValor.IGUAL, 0d, objetoFato.getFato().getDecimalFormat()));
                            }else if(i == listaCor.size() - 1){
                                legendaValor.getListaItemLegenda().add(new ItemLegendaValor(listaCor.get(i), EObjetoFatoValor.MAIORQUE, (vlIntervalo * (i - 1)), objetoFato.getFato().getDecimalFormat()));
                            }else{
                                legendaValor.getListaItemLegenda().add(new ItemLegendaValor(listaCor.get(i), EObjetoFatoValor.ATE, (vlIntervalo * i), objetoFato.getFato().getDecimalFormat()));
                            }
                        }
                        return legendaValor;
                    }
                }else{
                    throw new LegendaException("Intervalo de período nulo para a criação da legenda!");
                }
            }else{
                throw new LegendaException("Lista de cores indefinida para a legenda!");
            }
        }else{
            throw new LegendaException("Objeto Fato nulo para a criação da legenda!");
        }
    }
    
}
