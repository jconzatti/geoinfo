package com.geoinfo.model;

import com.geoinfo.exception.PeriodoIntervaloComparavelException;

public class PeriodoIntervaloComparavel{
    private PeriodoIntervalo periodoIntervaloA;
    private PeriodoIntervalo periodoIntervaloB;
    
    public static PeriodoIntervaloComparavel create(PeriodoIntervalo periodoIntervaloA, 
            PeriodoIntervalo periodoIntervaloB, boolean inComprarCom) throws PeriodoIntervaloComparavelException{
        if(inComprarCom){
            return new PeriodoIntervaloComparavel(periodoIntervaloA, periodoIntervaloB);
        }else{
            return new PeriodoIntervaloComparavel(periodoIntervaloA);
        }
    }
    
    private PeriodoIntervaloComparavel(PeriodoIntervalo periodoIntervaloA) throws PeriodoIntervaloComparavelException{
        setPeriodoIntervaloComparavel(periodoIntervaloA, null);
    }
    
    private PeriodoIntervaloComparavel(PeriodoIntervalo periodoIntervaloA, PeriodoIntervalo periodoIntervaloB) throws PeriodoIntervaloComparavelException{
        setPeriodoIntervaloComparavel(periodoIntervaloA, periodoIntervaloB);
    }
    
    private void setPeriodoIntervaloComparavel(PeriodoIntervalo periodoIntervaloA, PeriodoIntervalo periodoIntervaloB) throws PeriodoIntervaloComparavelException{
        if(periodoIntervaloA == null)
            throw new PeriodoIntervaloComparavelException("O intervalo de período inicial não pode ser nulo!");
        this.periodoIntervaloA = periodoIntervaloA;
        this.periodoIntervaloB = periodoIntervaloB;
    }

    public PeriodoIntervalo getPeriodoIntervaloA() {
        return periodoIntervaloA;
    }

    public boolean isInCompararCom() {
        return (this.periodoIntervaloA != null) && (this.periodoIntervaloB != null);
    }

    public PeriodoIntervalo getPeriodoIntervaloB() {
        return periodoIntervaloB;
    }
    
}
