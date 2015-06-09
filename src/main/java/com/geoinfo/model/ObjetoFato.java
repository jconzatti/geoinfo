package com.geoinfo.model;

import com.geoinfo.entity.Fato;
import com.geoinfo.exception.LegendaException;
import com.geoinfo.util.IGroupable;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ObjetoFato<T extends IGroupable> {
    private final Fato fato;
    private final ObjetoFatoPeriodo<T> objetoFatoPeriodoA;
    private final ObjetoFatoPeriodo<T> objetoFatoPeriodoB;
    
    public static List<ObjetoFato<? extends IGroupable>> create(){
        return null;
    }

    public ObjetoFato(Fato fato, PeriodoIntervaloComparavel periodoIntervaloComparavel) {
        this.fato = fato;
        this.objetoFatoPeriodoA = new ObjetoFatoPeriodo<T>(periodoIntervaloComparavel.getPeriodoIntervaloA());
        
        if(periodoIntervaloComparavel.isInCompararCom())
            this.objetoFatoPeriodoB = new ObjetoFatoPeriodo<T>(periodoIntervaloComparavel.getPeriodoIntervaloB());
        else
            this.objetoFatoPeriodoB = null;
    }

    public Fato getFato() {
        return fato;
    }

    public ObjetoFatoPeriodo<T> getObjetoFatoPeriodoA() {
        return objetoFatoPeriodoA;
    }

    public ObjetoFatoPeriodo<T> getObjetoFatoPeriodoB() {
        return objetoFatoPeriodoB;
    }
    
    public boolean isInCompararCom(){
        return objetoFatoPeriodoB != null;
    }
    
    public Legenda getLegenda() throws LegendaException{
        List<Color> listaCor = new ArrayList<Color>();
        listaCor.add(Color.WHITE);
        listaCor.add(Color.RED);
        listaCor.add(new Color(255, 150, 0));
        listaCor.add(Color.YELLOW);
        listaCor.add(new Color(128, 255, 255));
        listaCor.add(Color.GREEN);
        return Legenda.create(listaCor, this);
    }
    
    public String getDsTitulo(){
        String dsTitulo = fato.getDsFato() + " " + objetoFatoPeriodoA.getPeriodoIntervalo().toString();
        if(isInCompararCom())
            dsTitulo += " comparado com " + objetoFatoPeriodoB.getPeriodoIntervalo().toString();
        return dsTitulo;
    }
    
}
