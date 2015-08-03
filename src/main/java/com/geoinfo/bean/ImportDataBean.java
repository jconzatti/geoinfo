package com.geoinfo.bean;

import com.geoinfo.data.ImportDataGeoInfoCSV;
import com.geoinfo.data.ImportDataGeoInfoXMLNFE;
import com.geoinfo.entity.Pessoa;
import com.geoinfo.entity.PessoaMaster;
import com.geoinfo.factory.ImportDataGeoInfoCSVFactory;
import com.geoinfo.model.GeoInfoLogNode;
import com.geoinfo.util.EGeoInfoLogType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
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
    private String dsMensagem;
    private boolean inFechouImportacao;
    private List<EGeoInfoLogType> listaEGeoInfoLogType;
    
    public ImportDataBean(){
        this.listaGeoInfoLogNode = new ArrayList<GeoInfoLogNode>();
        this.dsMensagem = "Importação não iniciada!";
        this.inFechouImportacao = false;
        this.listaEGeoInfoLogType = new ArrayList<EGeoInfoLogType>();
        this.listaEGeoInfoLogType.addAll(Arrays.asList(EGeoInfoLogType.values()));
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

    public String getDsMensagem() {
        return dsMensagem;
    }

    public void setDsMensagem(String dsMensagem) {
        this.dsMensagem = dsMensagem;
    }

    public List<EGeoInfoLogType> getListaEGeoInfoLogType() {
        return listaEGeoInfoLogType;
    }

    public void setListaEGeoInfoLogType(List<EGeoInfoLogType> listaEGeoInfoLogType) {
        this.listaEGeoInfoLogType = listaEGeoInfoLogType;
    }
    
    public void importar(FileUploadEvent fuEvent){
        this.listaGeoInfoLogNode.clear();
        
        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Inicio do processo."));
        
        FacesContext fc = FacesContext.getCurrentInstance();
        if(fuEvent != null){
            UploadedFile uFile = fuEvent.getFile();
            if(uFile != null){
                String dsFileName = uFile.getFileName();
                if(dsFileName.toLowerCase().endsWith(".zip")){
                    this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Upload do arquivo: " + dsFileName));
                    
                    try {
                        File file = new File("C:\\Projetos\\@DataFor\\GeoInfo\\upload\\" + dsFileName); 
                        boolean inDiretorioExiste = file.getParentFile().exists();
                        if (!inDiretorioExiste)
                            inDiretorioExiste = file.getParentFile().mkdirs();
                        if(inDiretorioExiste){
                            if (file.createNewFile()){

                                FileOutputStream fos = new FileOutputStream(file);  

                                int read;
                                byte[] bytes = new byte[1024];
                                InputStream is = uFile.getInputstream();
                                while ((read = is.read(bytes)) != -1) {
                                    fos.write(bytes, 0, read);
                                } 
                                fos.flush();  
                                fos.close();

                                this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Arquivo salvo em " + file.getAbsolutePath()));

                                List<ZipEntry> listaZEGeoInfo = new ArrayList<ZipEntry>();
                                List<ZipEntry> listaZEGeoInfoVenda = new ArrayList<ZipEntry>();
                                List<ZipEntry> listaZEXMLNFE = new ArrayList<ZipEntry>();

                                ZipFile zipFile = new ZipFile(file);
                                Enumeration e = zipFile.entries();
                                while (e.hasMoreElements()){
                                    ZipEntry zipEntry = (ZipEntry) e.nextElement();

                                    if(!zipEntry.isDirectory()){
                                        String dsZEName = zipEntry.getName();

                                        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Extraindo " + dsZEName));
                                        if(dsZEName.toLowerCase().endsWith(".geoinfo")){
                                            if(dsZEName.toLowerCase().startsWith("venda"))
                                                listaZEGeoInfoVenda.add(zipEntry);
                                            else 
                                                listaZEGeoInfo.add(zipEntry);
                                        }else{
                                            if(dsZEName.toLowerCase().endsWith(".xml")){
                                                listaZEXMLNFE.add(zipEntry);
                                            }
                                        } 
                                    }
                                }

                                listaZEGeoInfo.addAll(listaZEGeoInfoVenda);

                                if((listaZEGeoInfo.size() > 0) || (listaZEXMLNFE.size() > 0)){
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
                                        
                                        
                                        pessoaGerente.isGerente();

                                        this.dsMensagem = "Contando registros a serem importadas!";

                                        Long nrRegistroTotal = (long) listaZEXMLNFE.size();
                                        Iterator itZE = listaZEGeoInfo.iterator();
                                        while((itZE.hasNext())&&(!this.inFechouImportacao)){
                                            ZipEntry ze = (ZipEntry) itZE.next();
                                            try {
                                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(ze)));

                                                while(bufferedReader.readLine() != null){
                                                    nrRegistroTotal++;
                                                }

                                                bufferedReader.close();

                                            } catch (IOException ex) {
                                                this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Erro na leitura do arquivo de importação! " + ex.getMessage()));

                                                FacesMessage fm = new FacesMessage("Erro na importação de arquivo! " + ex.getMessage());
                                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                fc.addMessage(null, fm);
                                            }
                                        }

                                        boolean inErroCommit = false;
                                        Long nrRegistroAtual = (long) 0;

                                        if(listaZEGeoInfo.size() > 0){
                                            itZE = listaZEGeoInfo.iterator();
                                            while((itZE.hasNext())&&(!inErroCommit)&&(!this.inFechouImportacao)){
                                                ZipEntry ze = (ZipEntry) itZE.next();

                                                String dsArquivo = ze.getName();
                                                this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importando arquivo: " + dsArquivo));

                                                ImportDataGeoInfoCSV importDataGeoInfoCSV = ImportDataGeoInfoCSVFactory.createByFileName(dsArquivo, entityManager, pessoaGerente);

                                                if(importDataGeoInfoCSV != null){
                                                    importDataGeoInfoCSV.setListaGeoInfoLogNode(listaGeoInfoLogNode);
                                                    try {
                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(ze)));

                                                        entityManager.getTransaction().begin();

                                                        String dsLinha;
                                                        Long nrLinha = (long) 0;
                                                        while(((dsLinha = bufferedReader.readLine()) != null)&&(!inErroCommit)&&(!this.inFechouImportacao)){
                                                            nrLinha++;
                                                            nrRegistroAtual++;

                                                            this.dsMensagem = "Importando linha " + nrLinha + " do arquivo " + dsArquivo;

                                                            double vlProgresso = (nrRegistroAtual.doubleValue()/nrRegistroTotal.doubleValue());
                                                            this.pcProgresso = Math.round((vlProgresso) * 100);

                                                            importDataGeoInfoCSV.importar(nrLinha, dsLinha);

                                                            if((nrRegistroAtual % 1000) == 0){
                                                                try{
                                                                    entityManager.getTransaction().commit();
                                                                    entityManager.getTransaction().begin();
                                                                }catch(Exception ex){
                                                                    inErroCommit = true;
                                                                    entityManager.getTransaction().rollback();

                                                                    this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Erro ao efetivar gravação dos registros no banco de dados! " + ex.getMessage()));

                                                                    FacesMessage fm = new FacesMessage("Erro ao efetivar gravação dos registros no banco de dados! " + ex.getMessage());
                                                                    fm.setSeverity(FacesMessage.SEVERITY_INFO);
                                                                    fc.addMessage(null, fm);
                                                                }

                                                            }
                                                        }
                                                        bufferedReader.close();

                                                        if(!inErroCommit){
                                                            try{
                                                                entityManager.getTransaction().commit();

                                                                if(this.inFechouImportacao){
                                                                    this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, "Importação de dados finalizada pelo usuário!"));

                                                                    FacesMessage fm = new FacesMessage("Importação de dados finalizada pelo usuário!");
                                                                    fm.setSeverity(FacesMessage.SEVERITY_WARN);
                                                                    fc.addMessage(null, fm);
                                                                }
                                                            }catch(Exception ex){
                                                                inErroCommit = true;
                                                                entityManager.getTransaction().rollback();

                                                                this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Erro ao efetivar gravação dos registros no banco de dados! " + ex.getMessage()));

                                                                FacesMessage fm = new FacesMessage("Erro ao efetivar gravação dos registros no banco de dados! " + ex.getMessage());
                                                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                                fc.addMessage(null, fm);
                                                            }
                                                        }

                                                    } catch (IOException ex) {
                                                        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Erro na importação de arquivo! " + ex.getMessage()));

                                                        FacesMessage fm = new FacesMessage("Erro na importação de arquivo! " + ex.getMessage());
                                                        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                        fc.addMessage(null, fm);
                                                    }
                                                }
                                            }
                                        }

                                        if(!this.inFechouImportacao){
                                            if(listaZEXMLNFE.size() > 0){
                                                ImportDataGeoInfoXMLNFE importDataGeoInfoXMLNFE = new ImportDataGeoInfoXMLNFE(entityManager, pessoaGerente);
                                                importDataGeoInfoXMLNFE.setListaGeoInfoLogNode(listaGeoInfoLogNode);

                                                Long nrArquivo = (long) 0;
                                                itZE = listaZEXMLNFE.iterator();
                                                while((itZE.hasNext())&&(!inErroCommit)&&(!this.inFechouImportacao)){
                                                    ZipEntry ze = (ZipEntry)itZE.next();

                                                    nrArquivo++;
                                                    nrRegistroAtual++;

                                                    String dsArquivo = ze.getName();
                                                    this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importando arquivo: " + dsArquivo));

                                                    this.dsMensagem = "Importando arquivo " + dsArquivo;

                                                    double vlProgresso = (nrRegistroAtual.doubleValue()/nrRegistroTotal.doubleValue());
                                                    this.pcProgresso = Math.round((vlProgresso) * 100);

                                                    try {
                                                        entityManager.getTransaction().begin();

                                                        importDataGeoInfoXMLNFE.importar(zipFile.getInputStream(ze), true);

                                                        if((nrArquivo % 50) == 0){
                                                            try{
                                                                entityManager.getTransaction().commit();
                                                                entityManager.getTransaction().begin();
                                                            }catch(Exception ex){
                                                                inErroCommit = true;
                                                                entityManager.getTransaction().rollback();

                                                                this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Erro ao efetivar gravação dos registros no banco de dados! " + ex.getMessage()));

                                                                FacesMessage fm = new FacesMessage("Erro ao efetivar gravação dos registros no banco de dados! " + ex.getMessage());
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

                                                if(!inErroCommit){
                                                    try{
                                                        entityManager.getTransaction().commit();

                                                        if(this.inFechouImportacao){
                                                            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, "Importação de dados finalizada pelo usuário!"));

                                                            FacesMessage fm = new FacesMessage("Importação de dados finalizada pelo usuário!");
                                                            fm.setSeverity(FacesMessage.SEVERITY_WARN);
                                                            fc.addMessage(null, fm);
                                                        }
                                                    }catch(Exception ex){
                                                        inErroCommit = true;
                                                        entityManager.getTransaction().rollback();

                                                        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Erro ao efetivar gravação dos registros no banco de dados! " + ex.getMessage()));

                                                        FacesMessage fm = new FacesMessage("Erro ao efetivar gravação dos registros no banco de dados! " + ex.getMessage());
                                                        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                        fc.addMessage(null, fm);
                                                    }
                                                }
                                            }
                                        }

                                        if((!inErroCommit)&&(!this.inFechouImportacao)){
                                            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importação de dados finalizada com sucesso!"));

                                            FacesMessage fm = new FacesMessage("Importação de dados finalizada com sucesso!");
                                            fm.setSeverity(FacesMessage.SEVERITY_INFO);
                                            fc.addMessage(null, fm);

                                            this.dsMensagem = "Importação finalizada com sucesso!";
                                        }else{
                                            this.dsMensagem = "Importação finalizada!";
                                        }

                                        this.pcProgresso = new Long(100);
                                    }else{
                                        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "O usuário logado não é um usuário master! Somente usuários do tipo master (Administrador ou Estabelecimento) podem importar arquivos!"));

                                        FacesMessage fm = new FacesMessage("O usuário logado não é um usuário master! Somente usuários do tipo master (Administrador ou Estabelecimento) podem importar arquivos!");
                                        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        fc.addMessage(null, fm);
                                    }
                                }else{
                                    this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, "Não existem arquivos a serem importados em: " + dsFileName));

                                    FacesMessage fm = new FacesMessage("Não existem arquivos a serem importados em: " + dsFileName);
                                    fm.setSeverity(FacesMessage.SEVERITY_WARN);
                                    fc.addMessage(null, fm);
                                }
                            }else{
                                this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Não foi possível criar o arquivo: " + file.getName()));

                                FacesMessage fm = new FacesMessage("Não foi possível criar o arquivo: " + file.getName());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                fc.addMessage(null, fm);
                            }
                        }else{
                            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Não foi possível criar o diretório: " + file.getParentFile().getName()));

                            FacesMessage fm = new FacesMessage("Não foi possível criar o diretório: " + file.getParentFile().getName());
                            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                            fc.addMessage(null, fm);
                        }
                    } catch (IOException ex) {
                        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Erro na importação de arquivo! " + ex.getMessage()));

                        FacesMessage fm = new FacesMessage("Erro na importação de arquivo! " + ex.getMessage());
                        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                        fc.addMessage(null, fm);
                    }
                }else{
                    this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Tipo de arquivo inválido para upload! O arquivo deve ser compactado com extenção ZIP!"));

                    FacesMessage fm = new FacesMessage("Tipo de arquivo inválido para upload! O arquivo deve ser compactado com extenção ZIP!");
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(null, fm);
                } 
            }else{
                this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Arquivo inválido para upload! Arquivo nulo!"));

                FacesMessage fm = new FacesMessage("Arquivo inválido para upload! Arquivo nulo!");
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
            }
        }else{
            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Evendo de upload inválido! Evento nulo!"));

            FacesMessage fm = new FacesMessage("Evento de upload inválido! Evento nulo!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
        } 
        
        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Fim do processo."));
    }
    
    public void fecharImportacao(CloseEvent closeEvent){
        this.inFechouImportacao = true;
        this.dsMensagem = "Importação não iniciada!";
        this.listaGeoInfoLogNode.clear();
    }
    
}
