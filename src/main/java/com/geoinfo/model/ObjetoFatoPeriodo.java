package com.geoinfo.model;

import com.geoinfo.util.IGroupable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ObjetoFatoPeriodo<T extends IGroupable> {
    private final PeriodoIntervalo periodoIntervalo;
    private final HashMap<T, Double> hashMapObjetoFatoValor;
    private double vlMaximo;
    private double vlMinimo;
    private double vlMedio;
    private double vlTotal;
    private int qtFato;
    private final List<String> listaDsMaximo;
    private final List<String> listaDsMinimo;
    private String dsMaximo;
    private String dsMinimo;
    private boolean inCalculou;
    
    private void calcular(){
        vlMaximo = 0.0;
        vlMinimo = 0.0;
        vlMedio  = 0.0;
        vlTotal  = 0.0;
        qtFato   = 0;
        listaDsMaximo.clear();
        listaDsMinimo.clear();
        dsMaximo = "NENHUM(A)";
        dsMinimo = "NENHUM(A)";
        if(hashMapObjetoFatoValor.size() > 0){
            vlMinimo = Double.MAX_VALUE;
            vlMaximo = Double.MIN_VALUE;
            vlTotal = 0;
            qtFato = 0;
            Iterator<T> itObjetoFatoValor = hashMapObjetoFatoValor.keySet().iterator();
            while(itObjetoFatoValor.hasNext()){
                T item = itObjetoFatoValor.next();
                Double vlItem = hashMapObjetoFatoValor.get(item);
                qtFato++;
                vlTotal += vlItem;
                if(vlItem > vlMaximo){
                    vlMaximo = vlItem;
                    listaDsMaximo.clear();
                    dsMaximo = item.toString();
                }
                if(vlItem == vlMaximo){
                    listaDsMaximo.add(item.toString());
                }
                if(vlItem < vlMinimo){
                    vlMinimo = vlItem;
                    listaDsMinimo.clear();
                    dsMinimo = item.toString();
                }
                if(vlItem == vlMinimo){
                    listaDsMinimo.add(item.toString());
                }
            }
            if(qtFato > 0)
                vlMedio = vlTotal/qtFato;
            else
                vlMedio = 0;
        }
        inCalculou = true;
    }

    public ObjetoFatoPeriodo(PeriodoIntervalo periodoIntervalo) {
        this.periodoIntervalo = periodoIntervalo;
        this.hashMapObjetoFatoValor = new HashMap<T, Double>();
        this.listaDsMaximo = new ArrayList<String>();
        this.listaDsMinimo = new ArrayList<String>();
        this.inCalculou = false;
    }

    public PeriodoIntervalo getPeriodoIntervalo() {
        return periodoIntervalo;
    }

    public void putVlItem(T item, Double vlItem) {
        this.hashMapObjetoFatoValor.put(item, vlItem);
        this.inCalculou = false;
    }

    public Double getVlItem(T item) {
        Double vlItem = this.hashMapObjetoFatoValor.get(item);
        if(vlItem==null)
            vlItem = new Double(0);
        return vlItem;
    }
    
    public Iterator<T> getIteratorItem(){
        return this.hashMapObjetoFatoValor.keySet().iterator();
    }

    public List<String> getListaDsMaximo() {
        if(!inCalculou)
            calcular();
        return listaDsMaximo;
    }

    public List<String> getListaDsMinimo() {
        if(!inCalculou)
            calcular();
        return listaDsMinimo;
    }

    public String getDsMaximo() {
        if(!inCalculou)
            calcular();
        return dsMaximo;
    }

    public String getDsMinimo() {
        if(!inCalculou)
            calcular();
        return dsMinimo;
    }

    public int getQtFato() {
        if(!inCalculou)
            calcular();
        return qtFato;
    }

    public double getVlMaximo() {
        if(!inCalculou)
            calcular();
        return vlMaximo;
    }

    public double getVlMedio() {
        if(!inCalculou)
            calcular();
        return vlMedio;
    }

    public double getVlMinimo() {
        if(!inCalculou)
            calcular();
        return vlMinimo;
    }

    public double getVlTotal() {
        if(!inCalculou)
            calcular();
        return vlTotal;
    }
    
}
