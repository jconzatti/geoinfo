package com.geoinfo.bean;

import com.geoinfo.entity.Pessoa;
import com.geoinfo.factory.PessoaFactory;
import java.io.Serializable;
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
public class PessoaLoginBean implements Serializable{
    private String dsUsuario;
    private String dsSenha;

    public String login(){
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest hsr = (HttpServletRequest) ec.getRequest();
        
        EntityManager entityManager = (EntityManager)hsr.getAttribute("entityManager");
        Pessoa pessoa = PessoaFactory.create(entityManager, this.dsUsuario, this.dsSenha);
        if(pessoa != null){
            HttpSession hs = (HttpSession) ec.getSession(false);
            hs.setAttribute("pessoaLogada", pessoa);
            return "geoinfo";
        }else{
            FacesMessage fm = new FacesMessage("Usuário e/ou senha inválidos!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
            return "index";
        }
    }
    
    public String logoff(){
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpSession hs = (HttpSession) ec.getSession(false);
        hs.removeAttribute("pessoaLogada");
        if(hs.getAttribute("listaFato") != null){
            hs.removeAttribute("listaFato");
        }
        if(hs.getAttribute("periodoIntervaloComparavel") != null){
            hs.removeAttribute("periodoIntervaloComparavel");
        }
        return "index";
    }

    public String getDsUsuario() {
        return this.dsUsuario;
    }

    public void setDsUsuario(String dsUsuario) {
        this.dsUsuario = dsUsuario;
    }

    public String getDsSenha() {
        return this.dsSenha;
    }

    public void setDsSenha(String dsSenha) {
        this.dsSenha = dsSenha;
    }
    
}
