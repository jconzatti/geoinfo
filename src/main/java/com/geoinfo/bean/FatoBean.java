package com.geoinfo.bean;

import com.geoinfo.entity.Fato;
import com.geoinfo.repository.FatoRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class FatoBean implements Serializable{
    private List<Fato> listaFato;
    private List<Fato> listaFatoSelecionado;
    private String cdFatoSelecionado;
    
    public FatoBean(){
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest hsr = (HttpServletRequest) ec.getRequest();
        
        EntityManager entityManager = (EntityManager)hsr.getAttribute("entityManager");
        FatoRepository fatoRepository = new FatoRepository(entityManager);
        listaFato = fatoRepository.getList();
        listaFatoSelecionado = new ArrayList<Fato>();
        cdFatoSelecionado = null;
    }

    public List<Fato> getListaFato() {
        return listaFato;
    }

    public void setListaFato(List<Fato> listaFato) {
        this.listaFato = listaFato;
    }

    public List<Fato> getListaFatoSelecionado() {
        return listaFatoSelecionado;
    }

    public void setListaFatoSelecionado(List<Fato> listaFatoSelecionado) {
        this.listaFatoSelecionado = listaFatoSelecionado;
    }

    public String getCdFatoSelecionado() {
        return cdFatoSelecionado;
    }

    public void setCdFatoSelecionado(String cdFatoSelecionado) {
        this.cdFatoSelecionado = cdFatoSelecionado;
    }
    
    public void addFatoSelecionado(){
        if((this.cdFatoSelecionado != null) && (!this.cdFatoSelecionado.isEmpty())){
            for(int i = 0; i < this.listaFato.size(); i++){
                if(this.listaFato.get(i).getCdFato().equalsIgnoreCase(this.cdFatoSelecionado)){
                    this.listaFatoSelecionado.add(this.listaFato.get(i));
                    this.listaFato.remove(i);
                    break;
                }
            }
            this.cdFatoSelecionado = null;
        }else{
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage("Selecione uma informação!");
            fm.setSeverity(FacesMessage.SEVERITY_WARN);
            fc.addMessage(null, fm);
        }
    }
    
    public void removeFatoSelecionado(){
        Map<String, String> mapParametro = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String cdFatoSelecionadoParMap = mapParametro.get("cdFatoSelecionado");
        if((cdFatoSelecionadoParMap != null) && (!cdFatoSelecionadoParMap.isEmpty())){
            for(int i = 0; i < this.listaFatoSelecionado.size(); i++){
                if(this.listaFatoSelecionado.get(i).getCdFato().equalsIgnoreCase(cdFatoSelecionadoParMap)){
                    this.listaFato.add(this.listaFatoSelecionado.get(i));
                    this.listaFatoSelecionado.remove(i);
                    break;
                }
            }
        }
    }
    
    public void registrar(){
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpSession hs = (HttpSession) ec.getSession(false);
        if(hs.getAttribute("listaFato") != null){
            hs.removeAttribute("listaFato");
        }
        if(this.listaFatoSelecionado.isEmpty()){
            FacesMessage fm = new FacesMessage("Selecione pelo menos uma informação!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
        }else{
            hs.setAttribute("listaFato", this.listaFatoSelecionado);
        }
    }
}
