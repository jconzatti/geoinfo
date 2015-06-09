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
    private List<Integer> listaIdDia;
    private List<EPeriodoMesType> listaIdMes;
    private List<Integer> listaIdAno;
    private int idAnoA, idAnoB, idAnoC, idAnoD, idDiaA, idDiaB, idDiaC, idDiaD;
    private EPeriodoMesType idMesA, idMesB, idMesC, idMesD;
    private boolean inCompararCom, inAteAB, inAteCD;
    private EPeriodoType idPeriodoAB, idPeriodoCD;
    
    public PeriodoBean(){
        this.listaIdPeriodo = new ArrayList<EPeriodoType>();
        this.listaIdPeriodo.addAll(Arrays.asList(EPeriodoType.values()));
        
        this.listaIdDia = new ArrayList<Integer>();
        for(int i = 1; i < 32; i++)
            this.listaIdDia.add(i);
        
        this.listaIdMes = new ArrayList<EPeriodoMesType>();
        this.listaIdMes.addAll(Arrays.asList(EPeriodoMesType.values()));
        
        this.listaIdAno = new ArrayList<Integer>();
        for(int i = Calendar.getInstance().get(Calendar.YEAR); i > 1989; i--)
            this.listaIdAno.add(i);
        
        idPeriodoAB = EPeriodoType.ANO;
        idAnoA = Calendar.getInstance().get(Calendar.YEAR);
        idMesA = EPeriodoMesType.values()[Calendar.getInstance().get(Calendar.MONTH)];
        idDiaA = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        
        inAteAB = false;
        
        idAnoB = Calendar.getInstance().get(Calendar.YEAR);
        idMesB = EPeriodoMesType.values()[Calendar.getInstance().get(Calendar.MONTH)];
        idDiaB = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        
        
        idPeriodoCD = EPeriodoType.ANO;
        idAnoC = Calendar.getInstance().get(Calendar.YEAR);
        idMesC = EPeriodoMesType.values()[Calendar.getInstance().get(Calendar.MONTH)];
        idDiaC = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        
        inAteCD = false;
        
        idAnoD = Calendar.getInstance().get(Calendar.YEAR);
        idMesD = EPeriodoMesType.values()[Calendar.getInstance().get(Calendar.MONTH)];
        idDiaD = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public List<EPeriodoType> getListaIdPeriodo() {
        return this.listaIdPeriodo;
    }

    public void setListaIdPeriodo(List<EPeriodoType> listaIdPeriodo) {
        this.listaIdPeriodo = listaIdPeriodo;
    }

    public List<Integer> getListaIdDia() {
        return this.listaIdDia;
    }

    public void setListaIdDia(List<Integer> listaIdDia) {
        this.listaIdDia = listaIdDia;
    }

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
        return idAnoA;
    }

    public void setIdAnoA(int idAnoA) {
        this.idAnoA = idAnoA;
    }

    public int getIdAnoB() {
        return idAnoB;
    }

    public void setIdAnoB(int idAnoB) {
        this.idAnoB = idAnoB;
    }

    public int getIdAnoC() {
        return idAnoC;
    }

    public void setIdAnoC(int idAnoC) {
        this.idAnoC = idAnoC;
    }

    public int getIdAnoD() {
        return idAnoD;
    }

    public void setIdAnoD(int idAnoD) {
        this.idAnoD = idAnoD;
    }

    public int getIdDiaA() {
        return idDiaA;
    }

    public void setIdDiaA(int idDiaA) {
        this.idDiaA = idDiaA;
    }

    public int getIdDiaB() {
        return idDiaB;
    }

    public void setIdDiaB(int idDiaB) {
        this.idDiaB = idDiaB;
    }

    public int getIdDiaC() {
        return idDiaC;
    }

    public void setIdDiaC(int idDiaC) {
        this.idDiaC = idDiaC;
    }

    public int getIdDiaD() {
        return idDiaD;
    }

    public void setIdDiaD(int idDiaD) {
        this.idDiaD = idDiaD;
    }

    public EPeriodoMesType getIdMesA() {
        return idMesA;
    }

    public void setIdMesA(EPeriodoMesType idMesA) {
        this.idMesA = idMesA;
    }

    public EPeriodoMesType getIdMesB() {
        return idMesB;
    }

    public void setIdMesB(EPeriodoMesType idMesB) {
        this.idMesB = idMesB;
    }

    public EPeriodoMesType getIdMesC() {
        return idMesC;
    }

    public void setIdMesC(EPeriodoMesType idMesC) {
        this.idMesC = idMesC;
    }

    public EPeriodoMesType getIdMesD() {
        return idMesD;
    }

    public void setIdMesD(EPeriodoMesType idMesD) {
        this.idMesD = idMesD;
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
    
    public int getIdPeriodoABOrd(){
        return this.idPeriodoAB.ordinal();
    }
    
    public int getIdPeriodoCDOrd(){
        return this.idPeriodoCD.ordinal();
    }
    
    public void registrar(){
        boolean inErro = false;
        
        FacesContext fc = FacesContext.getCurrentInstance();
        
        PeriodoIntervalo periodoIntervaloA = null;
        PeriodoIntervalo periodoIntervaloB = null;
        PeriodoIntervaloComparavel periodoIntervaloComparavel = null;

        if(!inErro){
            try{
                periodoIntervaloA = PeriodoIntervalo.create(idPeriodoAB, idAnoA, idMesA, idDiaA, idAnoB, idMesB, idDiaB, inAteAB);
            }catch (PeriodoControlException pce){
                FacesMessage fm = new FacesMessage(pce.getMessage());
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
                inErro = true;
            }
        }
        
        if(!inErro){
            try{
                periodoIntervaloB = PeriodoIntervalo.create(idPeriodoCD, idAnoC, idMesC, idDiaC, idAnoD, idMesD, idDiaD, inAteCD);
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
