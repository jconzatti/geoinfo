package com.geoinfo.model;

import com.geoinfo.exception.PeriodoControlException;
import com.geoinfo.util.EPeriodoMesType;
import com.geoinfo.util.EPeriodoType;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PeriodoControl {
    private int idAno;
    private EPeriodoMesType idMes;
    private int idDia;
    private String cdPeriodo;
    
    public static PeriodoControl create(String cdPeriodo) throws PeriodoControlException{
        EPeriodoType idPeriodo = null;
        switch(cdPeriodo.length()){
            case 4:
                idPeriodo = EPeriodoType.ANO;
                break;
            case 6:
                idPeriodo = EPeriodoType.MES;
                break;
            case 8:
                idPeriodo = EPeriodoType.DATA;
                break;
        }
        if(idPeriodo == null){
            throw new PeriodoControlException("Código de período inválido!");
        }else{
            int idAno = 0;
            EPeriodoMesType idMes = null;
            int idDia = 0;
            try{
                switch(idPeriodo){
                    case ANO:
                        idAno = Integer.valueOf(cdPeriodo.substring(0, 4));
                    case MES:
                        idMes = EPeriodoMesType.values()[Integer.valueOf(cdPeriodo.substring(4, 6))];
                    case DATA:
                        idDia = Integer.valueOf(cdPeriodo.substring(6, 8));
                }
                return PeriodoControl.create(idPeriodo, idAno, idMes, idDia);
            }catch(Exception e){
                throw new PeriodoControlException("Código de período inválido!\n" + e.getMessage());
            }
        }
    }
    
    public static PeriodoControl create(EPeriodoType idPeriodo, int idAno, EPeriodoMesType idMes, int idDia) throws PeriodoControlException{
        switch(idPeriodo){
            case ANO:
                return new PeriodoControl(idAno);
            case MES:
                return new PeriodoControl(idAno, idMes);
            case DATA:
                return new PeriodoControl(idAno, idMes, idDia);
        }
        throw new PeriodoControlException("Tipo de período inválido!");
    }
    
    private PeriodoControl(int idAno) throws PeriodoControlException{
        setIdAno(idAno);
    }
    
    private PeriodoControl(int idAno, EPeriodoMesType idMes) throws PeriodoControlException{
        setMes(idAno, idMes);
    }
    
    private PeriodoControl(int idAno, EPeriodoMesType idMes, int idDia) throws PeriodoControlException{
        setIdDia(idAno, idMes, idDia);
    }
    
    private void setIdAno(int idAno) throws PeriodoControlException{
        this.cdPeriodo = null;
        if((idAno > 0)&&(idAno < 10000)){
            this.idAno = idAno;
            this.cdPeriodo = new DecimalFormat("0000").format(this.idAno);
        }else{
            throw new PeriodoControlException("Ano inválido!");
        }
        this.idMes = null;
        this.idDia = 0;
    }
    
    
    private void setMes(int idAno, EPeriodoMesType idMes) throws PeriodoControlException{
        this.cdPeriodo = null;
        setIdAno(idAno);
        if(idMes != null){
            this.idMes = idMes;
            this.cdPeriodo = new DecimalFormat("0000").format(this.idAno) + 
                    new DecimalFormat("00").format(this.idMes.ordinal() + 1);
        }else{
            throw new PeriodoControlException("Mês inválido!");
        }
        this.idDia = 0;
    }
    
    private void setIdDia(int idAno, EPeriodoMesType idMes, int idDia) throws PeriodoControlException{
        this.cdPeriodo = null;
        setMes(idAno, idMes);
        if((idDia > 0) && (idDia < 32)){
            String dsData = new DecimalFormat("00").format(this.idDia) + "/" +
                    new DecimalFormat("00").format(this.idMes.ordinal() + 1) + "/" +
                    new DecimalFormat("0000").format(this.idAno);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try{
                sdf.parse(dsData);
                this.idDia = idDia;
                this.cdPeriodo = new DecimalFormat("0000").format(this.idAno) + 
                        new DecimalFormat("00").format(this.idMes.ordinal() + 1) +
                        new DecimalFormat("00").format(this.idDia);
            }catch(ParseException pe){
                throw new PeriodoControlException("A data " + dsData + " é inválida!");
            }
        }else{
            throw new PeriodoControlException("Dia inválido!");
        }
    }
    
    public String getCdPeriodo(){
        return this.cdPeriodo;
    }
    
    public EPeriodoType getIdPeriodo(){
        switch(this.cdPeriodo.length()){
            case 4:
                return EPeriodoType.ANO;
            case 6:
                return EPeriodoType.MES;
            case 8:
                return EPeriodoType.DATA;
        }
        return null;
    }
    
    @Override
    public String toString(){
        String dsPeriodo = "";
        switch(getIdPeriodo()){
            case DATA:
                dsPeriodo += new DecimalFormat("00").format(this.idDia) + " de ";
            case MES:
                dsPeriodo += this.idMes.toString() + " de ";
            case ANO:
                dsPeriodo += new DecimalFormat("0000").format(this.idAno);
        }
        return dsPeriodo;
    }
}
