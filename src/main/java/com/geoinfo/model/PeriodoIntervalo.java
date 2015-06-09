package com.geoinfo.model;

import com.geoinfo.exception.PeriodoControlException;
import com.geoinfo.util.EPeriodoMesType;
import com.geoinfo.util.EPeriodoType;

public class PeriodoIntervalo {
    private PeriodoControl periodoA;
    private PeriodoControl periodoB;
    
    public static PeriodoIntervalo create(EPeriodoType idPeriodo, int idAnoA, EPeriodoMesType idMesA, int idDiaA, 
            int idAnoB, EPeriodoMesType idMesB, int idDiaB, boolean inAte) throws PeriodoControlException{
        if(inAte){
            switch(idPeriodo){
                case ANO:
                    return new PeriodoIntervalo(idAnoA, idAnoB);
                case MES:
                    return new PeriodoIntervalo(idAnoA, idMesA, idAnoB, idMesB);
                case DATA:
                    return new PeriodoIntervalo(idAnoA, idMesA, idDiaA, idAnoB, idMesB, idDiaB);
            }
        }else{
            switch(idPeriodo){
                case ANO:
                    return new PeriodoIntervalo(idAnoA);
                case MES:
                    return new PeriodoIntervalo(idAnoA, idMesA);
                case DATA:
                    return new PeriodoIntervalo(idAnoA, idMesA, idDiaA);
            }
        }
        throw new PeriodoControlException("Intervalo de período indefinido!");
    }
    
    private PeriodoIntervalo(int idAnoA, int idAnoB) throws PeriodoControlException{
        setPeriodoIntervaloAno(idAnoA, idAnoB, true);
    }
    
    private PeriodoIntervalo(int idAnoA, EPeriodoMesType idMesA, 
            int idAnoB, EPeriodoMesType idMesB) throws PeriodoControlException{
        setPeriodoIntervaloMes(idAnoA, idMesA, idAnoB, idMesB, true);
    }
    
    private PeriodoIntervalo(int idAnoA, EPeriodoMesType idMesA, int idDiaA,
            int idAnoB, EPeriodoMesType idMesB, int idDiaB) throws PeriodoControlException{
        setPeriodoIntervaloDia(idAnoA, idMesA, idDiaA, idAnoB, idMesB, idDiaB, true);
    }
    
    private PeriodoIntervalo(int idAnoA) throws PeriodoControlException{
        setPeriodoIntervaloAno(idAnoA, 0, false);
    }
    
    private PeriodoIntervalo(int idAnoA, EPeriodoMesType idMesA) throws PeriodoControlException{
        setPeriodoIntervaloMes(idAnoA, idMesA, 0, null, false);
    }
    
    private PeriodoIntervalo(int idAnoA, EPeriodoMesType idMesA, int idDiaA) throws PeriodoControlException{
        setPeriodoIntervaloDia(idAnoA, idMesA, idDiaA, 0, null, 0, false);
    }
    
    private void setPeriodoIntervaloAno(int idAnoA, int idAnoB, boolean inAte) throws PeriodoControlException{
        this.periodoB = null;
        this.periodoA = PeriodoControl.create(EPeriodoType.ANO, idAnoA, null, 0);
        if(inAte){
            this.periodoB = PeriodoControl.create(EPeriodoType.ANO, idAnoB, null, 0);
        }
    }
    
    private void setPeriodoIntervaloMes(int idAnoA, EPeriodoMesType idMesA, 
            int idAnoB, EPeriodoMesType idMesB, boolean inAte) throws PeriodoControlException{
        this.periodoB = null;
        this.periodoA = PeriodoControl.create(EPeriodoType.MES, idAnoA, idMesA, 0);
        if(inAte){
            this.periodoB = PeriodoControl.create(EPeriodoType.MES, idAnoB, idMesB, 0);
        }
    }
    
    private void setPeriodoIntervaloDia(int idAnoA, EPeriodoMesType idMesA, int idDiaA,
            int idAnoB, EPeriodoMesType idMesB, int idDiaB, boolean inAte) throws PeriodoControlException{
        this.periodoB = null;
        this.periodoA = PeriodoControl.create(EPeriodoType.DATA, idAnoA, idMesA, idDiaA);
        if(inAte){
            this.periodoB = PeriodoControl.create(EPeriodoType.DATA, idAnoB, idMesB, idDiaB);
        }
    }
    
    public String getCdPeriodoA(){
        if(this.periodoA != null)
            return this.periodoA.getCdPeriodo();
        return null;
    }
    
    public String getCdPeriodoB(){
        if(this.periodoB != null)
            return this.periodoB.getCdPeriodo();
        return null;
    }
    
    public EPeriodoType getIdPeriodo(){
        if(this.periodoA != null)
            return this.periodoA.getIdPeriodo();
        return null;
    }
    public boolean isInAte() {
        return (this.periodoA != null) && (this.periodoB != null);
    }
    
    @Override
    public String toString(){
        if(isInAte()){
            return "entre " + periodoA.toString() + " e " + periodoB.toString();
        }else{
            if(periodoA != null){
                return "em " + periodoA.toString();
            }else{
                return "em algum momento";
            }
        }
    }
}
