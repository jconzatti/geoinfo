package com.geoinfo.bean;

import com.geoinfo.data.ImportDataGeoInfoCSV;
import com.geoinfo.data.ImportDataGeoInfoXMLNFE;
import com.geoinfo.entity.Pessoa;
import com.geoinfo.entity.PessoaMaster;
import com.geoinfo.factory.ImportDataGeoInfoCSVFactory;
import com.geoinfo.model.GeoInfoLogNode;
import com.geoinfo.util.EGeoInfoLogType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jdom.JDOMException;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class ImportDataBean implements Serializable{
    private List<GeoInfoLogNode> listaGeoInfoLogNode;
    private Long pcProgresso;
    private Long nrLinha;
    private Long nrTotalLinha;
    private String dsArquivo;
    private boolean inFechouImportacao;
    private List<EGeoInfoLogType> listaEGeoInfoLogType;
    private final List<UploadedFile> listaUploadedFileXMLNFe;
    
    public ImportDataBean(){
        this.listaGeoInfoLogNode = new ArrayList<GeoInfoLogNode>();
        this.nrLinha = (long) 0;
        this.nrTotalLinha = (long) 0;
        this.dsArquivo = "";
        this.inFechouImportacao = false;
        this.listaEGeoInfoLogType = new ArrayList<EGeoInfoLogType>();
        this.listaEGeoInfoLogType.addAll(Arrays.asList(EGeoInfoLogType.values()));
        this.listaUploadedFileXMLNFe = new ArrayList<UploadedFile>();
    }

    public List<GeoInfoLogNode> getListaGeoInfoLogNode() {
        return listaGeoInfoLogNode;
    }

    public void setListaGeoInfoLogNode(List<GeoInfoLogNode> listaGeoInfoLogNode) {
        this.listaGeoInfoLogNode = listaGeoInfoLogNode;
    }

    public Long getPcProgresso() {
        return pcProgresso;
    }

    public void setPcProgresso(Long pcProgresso) {
        this.pcProgresso = pcProgresso;
    }

    public Long getNrLinha() {
        return nrLinha;
    }

    public void setNrLinha(Long nrLinha) {
        this.nrLinha = nrLinha;
    }

    public Long getNrTotalLinha() {
        return nrTotalLinha;
    }

    public void setNrTotalLinha(Long nrTotalLinha) {
        this.nrTotalLinha = nrTotalLinha;
    }

    public String getDsArquivo() {
        return dsArquivo;
    }

    public void setDsArquivo(String dsArquivo) {
        this.dsArquivo = dsArquivo;
    }

    public List<EGeoInfoLogType> getListaEGeoInfoLogType() {
        return listaEGeoInfoLogType;
    }

    public void setListaEGeoInfoLogType(List<EGeoInfoLogType> listaEGeoInfoLogType) {
        this.listaEGeoInfoLogType = listaEGeoInfoLogType;
    }
    
    public void importarCSV(FileUploadEvent fileUploaded){
        this.listaGeoInfoLogNode.clear();
        
        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Inicio da importação de arquivo."));
        
        
        FacesContext fc = FacesContext.getCurrentInstance();
        if(fileUploaded != null){
            this.inFechouImportacao = false;
            
            this.dsArquivo = fileUploaded.getFile().getFileName();
            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Nome do Arquivo: " + this.dsArquivo));
            
            ExternalContext ec = fc.getExternalContext();

            HttpSession hs = (HttpSession) ec.getSession(false);
            Pessoa pessoaLogada = (Pessoa) hs.getAttribute("pessoaLogada");
            if(pessoaLogada instanceof PessoaMaster){
                if(!this.dsArquivo.equals("")){
                    HttpServletRequest hsr = (HttpServletRequest) ec.getRequest();

                    EntityManager entityManager = (EntityManager)hsr.getAttribute("entityManager");
                    PessoaMaster pessoaGerente = pessoaLogada.getGerente();
                    if(pessoaGerente == null){
                        pessoaGerente = (PessoaMaster) pessoaLogada;
                    }
                    
                    ImportDataGeoInfoCSV importDataGeoInfoCSV = ImportDataGeoInfoCSVFactory.createByFileName(this.dsArquivo, entityManager, pessoaGerente);

                    if(importDataGeoInfoCSV != null){
                        importDataGeoInfoCSV.setListaGeoInfoLogNode(listaGeoInfoLogNode);
                        try {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileUploaded.getFile().getInputstream()));

                            nrTotalLinha = (long) 0;
                            while(bufferedReader.readLine() != null){
                                nrTotalLinha++;
                            }

                            bufferedReader.close();
                            bufferedReader = new BufferedReader(new InputStreamReader(fileUploaded.getFile().getInputstream()));

                            entityManager.getTransaction().begin();
                            
                            String dsLinha;
                            nrLinha = (long) 0;
                            boolean inErroCommit = false;
                            while(((dsLinha = bufferedReader.readLine()) != null)&&(!inErroCommit)&&(!this.inFechouImportacao)){
                                nrLinha++;
                                
                                System.out.println("Importando linha " + nrLinha);
                                
                                double vlProgresso = (nrLinha.doubleValue()/nrTotalLinha.doubleValue());
                                this.pcProgresso = Math.round((vlProgresso) * 100);
                                
                                importDataGeoInfoCSV.importar(nrLinha, dsLinha);
                                
                                if((nrLinha % 1000) == 0){
                                    try{
                                        entityManager.getTransaction().commit();
                                        entityManager.getTransaction().begin();
                                    }catch(Exception e){
                                        inErroCommit = true;
                                        entityManager.getTransaction().rollback();

                                        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Erro ao efetivar gravação dos registros no banco de dados! " + e.getMessage()));

                                        FacesMessage fm = new FacesMessage("Erro ao efetivar gravação dos registros no banco de dados! " + e.getMessage());
                                        fm.setSeverity(FacesMessage.SEVERITY_INFO);
                                        fc.addMessage(null, fm);
                                    }
                                    
                                }
                            }
                            this.pcProgresso = new Long(100);
                            bufferedReader.close();
                            
                            if(!inErroCommit){
                                try{
                                    entityManager.getTransaction().commit();
                            
                                    if(this.inFechouImportacao){
                                        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, "Importação de dados finalizada pelo usuário!"));

                                        FacesMessage fm = new FacesMessage("Importação de dados finalizada pelo usuário!");
                                        fm.setSeverity(FacesMessage.SEVERITY_WARN);
                                        fc.addMessage(null, fm);
                                    }else{
                                        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "O arquivo " + this.dsArquivo + " foi importado com sucesso!"));

                                        FacesMessage fm = new FacesMessage("O arquivo " + this.dsArquivo + " foi importado com sucesso!");
                                        fm.setSeverity(FacesMessage.SEVERITY_INFO);
                                        fc.addMessage(null, fm);
                                    }
                                }catch(Exception e){
                                    entityManager.getTransaction().rollback();
                                    
                                    this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Erro ao efetivar gravação dos registros no banco de dados! " + e.getMessage()));

                                    FacesMessage fm = new FacesMessage("Erro ao efetivar gravação dos registros no banco de dados! " + e.getMessage());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    fc.addMessage(null, fm);
                                }
                            }
                            
                        } catch (IOException ex) {
                            FacesMessage fm = new FacesMessage("Erro na importação de arquivo! " + ex.getMessage());
                            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                            fc.addMessage(null, fm);
                        }
                    }
                }
            }else{
                this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "O usuário logado não é um usuário master! Somente usuários do tipo master (Administrador ou Estabelecimento) podem importar arquivos!"));
                
                FacesMessage fm = new FacesMessage("O usuário logado não é um usuário master! Somente usuários do tipo master (Administrador ou Estabelecimento) podem importar arquivos!");
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
            }
        }else{
            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Erro na importação de arquivo! O arquivo está nulo!"));
            
            FacesMessage fm = new FacesMessage("Erro na importação de arquivo! O arquivo está nulo!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
        }
    }
    
    public void fecharImportacao(CloseEvent closeEvent){
        this.inFechouImportacao = true;
        this.listaUploadedFileXMLNFe.clear();
    }
    
    public void importarXMLNFe(FileUploadEvent fileUploaded){
        if(fileUploaded != null){
            if(this.listaUploadedFileXMLNFe.isEmpty())
                this.listaGeoInfoLogNode.clear();
            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Nome do Arquivo: " + fileUploaded.getFile().getFileName()));
            listaUploadedFileXMLNFe.add(fileUploaded.getFile());
        }
    }
    
    public void importarListaXMLNFe(){
        this.listaGeoInfoLogNode.clear();
        
        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Inicio da importação de arquivos XML de NF-e."));
        
        FacesContext fc = FacesContext.getCurrentInstance();
        if(this.listaUploadedFileXMLNFe.size() > 0){
            this.inFechouImportacao = false;
            
            ExternalContext ec = fc.getExternalContext();

            HttpSession hs = (HttpSession) ec.getSession(false);
            Pessoa pessoaLogada = (Pessoa) hs.getAttribute("pessoaLogada");
            if(pessoaLogada instanceof PessoaMaster){
                HttpServletRequest hsr = (HttpServletRequest) ec.getRequest();

                EntityManager entityManager = (EntityManager)hsr.getAttribute("entityManager");
                PessoaMaster pessoaGerente = pessoaLogada.getGerente();
                if(pessoaGerente == null){
                    pessoaGerente = (PessoaMaster) pessoaLogada;
                }

                ImportDataGeoInfoXMLNFE importDataGeoInfoXMLNFE = new ImportDataGeoInfoXMLNFE(entityManager, pessoaGerente);
                importDataGeoInfoXMLNFE.setListaGeoInfoLogNode(listaGeoInfoLogNode);
                nrTotalLinha = (long)listaUploadedFileXMLNFe.size();
                nrLinha = (long) 0;

                boolean inErroCommit = false;
                Iterator itXMLNFE = this.listaUploadedFileXMLNFe.iterator();
                while((itXMLNFE.hasNext())&&(!inErroCommit)&&(!this.inFechouImportacao)){
                    nrLinha++;
                    UploadedFile ufXMLNFE = (UploadedFile)itXMLNFE.next();
                    try {
                        this.dsArquivo = ufXMLNFE.getFileName();
                        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Nome do Arquivo: " + this.dsArquivo));
                        
                        
                        entityManager.getTransaction().begin();
                        
                        importDataGeoInfoXMLNFE.importar(ufXMLNFE.getInputstream(), true);

                        if((nrLinha % 1000) == 0){
                            try{
                                entityManager.getTransaction().commit();
                                entityManager.getTransaction().begin();
                            }catch(Exception e){
                                inErroCommit = true;
                                entityManager.getTransaction().rollback();

                                this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Erro ao efetivar gravação dos registros no banco de dados! " + e.getMessage()));

                                FacesMessage fm = new FacesMessage("Erro ao efetivar gravação dos registros no banco de dados! " + e.getMessage());
                                fm.setSeverity(FacesMessage.SEVERITY_INFO);
                                fc.addMessage(null, fm);
                            }

                        }

                    } catch (IOException ex) {
                        FacesMessage fm = new FacesMessage("Erro na importação de arquivo! " + ex.getMessage());
                        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                        fc.addMessage(null, fm);
                    } catch (JDOMException ex) {
                        FacesMessage fm = new FacesMessage("Erro na importação de arquivo! " + ex.getMessage());
                        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                        fc.addMessage(null, fm);
                    }
                }
                this.pcProgresso = new Long(100);

                if(!inErroCommit){
                    try{
                        entityManager.getTransaction().commit();

                        if(this.inFechouImportacao){
                            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, "Importação de dados finalizada pelo usuário!"));

                            FacesMessage fm = new FacesMessage("Importação de dados finalizada pelo usuário!");
                            fm.setSeverity(FacesMessage.SEVERITY_WARN);
                            fc.addMessage(null, fm);
                        }else{
                            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "O arquivo " + this.dsArquivo + " foi importado com sucesso!"));

                            FacesMessage fm = new FacesMessage("O arquivo " + this.dsArquivo + " foi importado com sucesso!");
                            fm.setSeverity(FacesMessage.SEVERITY_INFO);
                            fc.addMessage(null, fm);
                        }
                    }catch(Exception e){
                        entityManager.getTransaction().rollback();

                        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Erro ao efetivar gravação dos registros no banco de dados! " + e.getMessage()));

                        FacesMessage fm = new FacesMessage("Erro ao efetivar gravação dos registros no banco de dados! " + e.getMessage());
                        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                        fc.addMessage(null, fm);
                    }
                }
            }else{
                this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "O usuário logado não é um usuário master! Somente usuários do tipo master (Administrador ou Estabelecimento) podem importar arquivos!"));
                
                FacesMessage fm = new FacesMessage("O usuário logado não é um usuário master! Somente usuários do tipo master (Administrador ou Estabelecimento) podem importar arquivos!");
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
            }
        }else{
            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Erro! Lista de arquivos XML de NF-e está vazio!"));
            
            FacesMessage fm = new FacesMessage("Erro! Lista de arquivos XML de NF-e está vazio!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
        }
    }
    
}
