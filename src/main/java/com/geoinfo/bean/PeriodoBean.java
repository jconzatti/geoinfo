package com.geoinfo.bean;

import com.geoinfo.exception.PeriodoControlException;
import com.geoinfo.exception.PeriodoIntervaloComparavelException;
import com.geoinfo.model.PeriodoIntervalo;
import com.geoinfo.model.PeriodoIntervaloComparavel;
import com.geoinfo.util.EPeriodoMesType;
import com.geoinfo.util.EPeriodoType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class PeriodoBean implements Serializable{
    private List<EPeriodoType> listaIdPeriodo;
    //private List<Integer> listaIdDia;
    private List<EPeriodoMesType> listaIdMes;
    private List<Integer> listaIdAno;
    //private int idAnoA, idAnoB, idAnoC, idAnoD, idDiaA, idDiaB, idDiaC, idDiaD;
    //private EPeriodoMesType idMesA, idMesB, idMesC, idMesD;
    private Date dtPeriodoA, dtPeriodoB, dtPeriodoC, dtPeriodoD;
    private boolean inCompararCom, inAteAB, inAteCD;
    private EPeriodoType idPeriodoAB, idPeriodoCD;
    
    public PeriodoBean(){
        this.listaIdPeriodo = new ArrayList<EPeriodoType>();
        this.listaIdPeriodo.addAll(Arrays.asList(EPeriodoType.values()));
        
        /*this.listaIdDia = new ArrayList<Integer>();
        for(int i = 1; i < 32; i++)
            this.listaIdDia.add(i);*/
        
        this.listaIdMes = new ArrayList<EPeriodoMesType>();
        this.listaIdMes.addAll(Arrays.asList(EPeriodoMesType.values()));
        
        this.listaIdAno = new ArrayList<Integer>();
        for(int i = Calendar.getInstance().get(Calendar.YEAR); i > 1989; i--)
            this.listaIdAno.add(i);
        
        idPeriodoAB = EPeriodoType.ANO;
        dtPeriodoA = Calendar.getInstance().getTime();
        //idAnoA = Calendar.getInstance().get(Calendar.YEAR);;
        //idMesA = EPeriodoMesType.values()[Calendar.getInstance().get(Calendar.MONTH)];
        //idDiaA = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        
        inAteAB = false;
        
        dtPeriodoB = Calendar.getInstance().getTime();
        //idAnoB = Calendar.getInstance().get(Calendar.YEAR);
        //idMesB = EPeriodoMesType.values()[Calendar.getInstance().get(Calendar.MONTH)];
        //idDiaB = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        
        
        idPeriodoCD = EPeriodoType.ANO;
        dtPeriodoC = Calendar.getInstance().getTime();
        //idAnoC = Calendar.getInstance().get(Calendar.YEAR);
        //idMesC = EPeriodoMesType.values()[Calendar.getInstance().get(Calendar.MONTH)];
        //idDiaC = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        
        inAteCD = false;
        
        dtPeriodoD = Calendar.getInstance().getTime();
        //idAnoD = Calendar.getInstance().get(Calendar.YEAR);
        //idMesD = EPeriodoMesType.values()[Calendar.getInstance().get(Calendar.MONTH)];
        //idDiaD = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public List<EPeriodoType> getListaIdPeriodo() {
        return this.listaIdPeriodo;
    }

    public void setListaIdPeriodo(List<EPeriodoType> listaIdPeriodo) {
        this.listaIdPeriodo = listaIdPeriodo;
    }

    /*public List<Integer> getListaIdDia() {
        return this.listaIdDia;
    }

    public void setListaIdDia(List<Integer> listaIdDia) {
        this.listaIdDia = listaIdDia;
    }*/

    public List<EPeriodoMesType> getListaIdMes() {
        return this.listaIdMes;
    }

    public void setListaIdMes(List<EPeriodoMesType> listaIdMes) {
        this.listaIdMes = listaIdMes;
    }

    public List<Integer> getListaIdAno() {
        return this.listaIdAno;
    }

    public void setListaIdAno(List<Integer> listaIdAno) {
        this.listaIdAno = listaIdAno;
    }

    public int getIdAnoA() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoA);
        return calendar.get(Calendar.YEAR);
    }

    public void setIdAnoA(int idAnoA) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoA);
        calendar.set(Calendar.YEAR, idAnoA);
        this.dtPeriodoA = calendar.getTime();
    }

    public int getIdAnoB() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoB);
        return calendar.get(Calendar.YEAR);
    }

    public void setIdAnoB(int idAnoB) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoB);
        calendar.set(Calendar.YEAR, idAnoB);
        this.dtPeriodoB = calendar.getTime();
    }

    public int getIdAnoC() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoC);
        return calendar.get(Calendar.YEAR);
    }

    public void setIdAnoC(int idAnoC) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoC);
        calendar.set(Calendar.YEAR, idAnoC);
        this.dtPeriodoC = calendar.getTime();
    }

    public int getIdAnoD() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoD);
        return calendar.get(Calendar.YEAR);
    }

    public void setIdAnoD(int idAnoD) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoD);
        calendar.set(Calendar.YEAR, idAnoD);
        this.dtPeriodoD = calendar.getTime();
    }

    public EPeriodoMesType getIdMesA() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoA);
        return EPeriodoMesType.values()[calendar.get(Calendar.MONTH)];
    }

    public void setIdMesA(EPeriodoMesType idMesA) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoA);
        calendar.set(Calendar.MONTH, idMesA.ordinal());
        this.dtPeriodoA = calendar.getTime();
    }

    public EPeriodoMesType getIdMesB() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoB);
        return EPeriodoMesType.values()[calendar.get(Calendar.MONTH)];
    }

    public void setIdMesB(EPeriodoMesType idMesB) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoB);
        calendar.set(Calendar.MONTH, idMesB.ordinal());
        this.dtPeriodoB = calendar.getTime();
    }

    public EPeriodoMesType getIdMesC() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoC);
        return EPeriodoMesType.values()[calendar.get(Calendar.MONTH)];
    }

    public void setIdMesC(EPeriodoMesType idMesC) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoC);
        calendar.set(Calendar.MONTH, idMesC.ordinal());
        this.dtPeriodoC = calendar.getTime();
    }

    public EPeriodoMesType getIdMesD() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoD);
        return EPeriodoMesType.values()[calendar.get(Calendar.MONTH)];
    }

    public void setIdMesD(EPeriodoMesType idMesD) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoD);
        calendar.set(Calendar.MONTH, idMesD.ordinal());
        this.dtPeriodoD = calendar.getTime();
    }

    public int getIdDiaA() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoA);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void setIdDiaA(int idDiaA) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoA);
        calendar.set(Calendar.DAY_OF_MONTH, idDiaA);
        this.dtPeriodoA = calendar.getTime();
    }

    public int getIdDiaB() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoB);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void setIdDiaB(int idDiaB) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoB);
        calendar.set(Calendar.DAY_OF_MONTH, idDiaB);
        this.dtPeriodoB = calendar.getTime();
    }

    public int getIdDiaC() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoC);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void setIdDiaC(int idDiaC) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoC);
        calendar.set(Calendar.DAY_OF_MONTH, idDiaC);
        this.dtPeriodoC = calendar.getTime();
    }

    public int getIdDiaD() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoD);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void setIdDiaD(int idDiaD) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtPeriodoD);
        calendar.set(Calendar.DAY_OF_MONTH, idDiaD);
        this.dtPeriodoD = calendar.getTime();
    }

    public Date getDtPeriodoA() {
        return dtPeriodoA;
    }

    public void setDtPeriodoA(Date dtPeriodoA) {
        this.dtPeriodoA = dtPeriodoA;
    }

    public Date getDtPeriodoB() {
        return dtPeriodoB;
    }

    public void setDtPeriodoB(Date dtPeriodoB) {
        this.dtPeriodoB = dtPeriodoB;
    }

    public Date getDtPeriodoC() {
        return dtPeriodoC;
    }

    public void setDtPeriodoC(Date dtPeriodoC) {
        this.dtPeriodoC = dtPeriodoC;
    }

    public Date getDtPeriodoD() {
        return dtPeriodoD;
    }

    public void setDtPeriodoD(Date dtPeriodoD) {
        this.dtPeriodoD = dtPeriodoD;
    }

    public boolean isInCompararCom() {
        return inCompararCom;
    }

    public void setInCompararCom(boolean inCompararCom) {
        this.inCompararCom = inCompararCom;
    }

    public boolean isInAteAB() {
        return inAteAB;
    }

    public void setInAteAB(boolean inAteAB) {
        this.inAteAB = inAteAB;
    }

    public boolean isInAteCD() {
        return inAteCD;
    }

    public void setInAteCD(boolean inAteCD) {
        this.inAteCD = inAteCD;
    }

    public EPeriodoType getIdPeriodoAB() {
        return idPeriodoAB;
    }

    public void setIdPeriodoAB(EPeriodoType idPeriodoAB) {
        this.idPeriodoAB = idPeriodoAB;
    }

    public EPeriodoType getIdPeriodoCD() {
        return idPeriodoCD;
    }

    public void setIdPeriodoCD(EPeriodoType idPeriodoCD) {
        this.idPeriodoCD = idPeriodoCD;
    }
    
    public void registrar(){
        boolean inErro = false;
        
        FacesContext fc = FacesContext.getCurrentInstance();
        
        PeriodoIntervalo periodoIntervaloA = null;
        PeriodoIntervalo periodoIntervaloB = null;
        PeriodoIntervaloComparavel periodoIntervaloComparavel = null;

        if(!inErro){
            try{
                periodoIntervaloA = PeriodoIntervalo.create(idPeriodoAB, getIdAnoA(), getIdMesA(), getIdDiaA(), getIdAnoB(), getIdMesB(), getIdDiaB(), inAteAB);
            }catch (PeriodoControlException pce){
                FacesMessage fm = new FacesMessage(pce.getMessage());
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
                inErro = true;
            }
        }
        
        if(!inErro){
            try{
                periodoIntervaloB = PeriodoIntervalo.create(idPeriodoCD, getIdAnoC(), getIdMesC(), getIdDiaC(), getIdAnoD(), getIdMesD(), getIdDiaD(), inAteCD);
            }catch (PeriodoControlException pce){
                FacesMessage fm = new FacesMessage(pce.getMessage());
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
                inErro = true;
            }
        }
        
        if(!inErro){
            try{
                periodoIntervaloComparavel = PeriodoIntervaloComparavel.create(periodoIntervaloA, periodoIntervaloB, inCompararCom);
            }catch (PeriodoIntervaloComparavelException pice){
                FacesMessage fm = new FacesMessage(pice.getMessage());
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
                inErro = true;
            }
        }

        if(!inErro){
            ExternalContext ec = fc.getExternalContext();
            HttpSession hs = (HttpSession) ec.getSession(false);
            if(hs.getAttribute("periodoIntervaloComparavel") != null){
                hs.removeAttribute("periodoIntervaloComparavel");
            }
            hs.setAttribute("periodoIntervaloComparavel", periodoIntervaloComparavel);
        }
    }
    
}
