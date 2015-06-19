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
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
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
    private final List<UploadedFile> listaUploadedFileGeoInfo;
    private final List<UploadedFile> listaUploadedFileGeoInfoVenda;
    private final List<UploadedFile> listaUploadedFileXMLNFe;
    
    public ImportDataBean(){
        this.listaGeoInfoLogNode = new ArrayList<GeoInfoLogNode>();
        this.dsMensagem = "Importação não iniciada!";
        this.inFechouImportacao = false;
        this.listaEGeoInfoLogType = new ArrayList<EGeoInfoLogType>();
        this.listaEGeoInfoLogType.addAll(Arrays.asList(EGeoInfoLogType.values()));
        this.listaUploadedFileGeoInfo = new ArrayList<UploadedFile>();
        this.listaUploadedFileGeoInfoVenda = new ArrayList<UploadedFile>();
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
                    
                    File file = new File("http://localhost:8080/GeoInfo/resources/upload/upload.properties");  
                    if(file.exists()) 
                        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Arquivo existe!"));
                    else
                        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Arquivo não existe!"));

                    try {
                        ZipInputStream zin = new ZipInputStream(uFile.getInputstream());
                        ZipEntry ze;
                        while ((ze = zin.getNextEntry()) != null) {
                            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Unzipping " + ze.getName()));
                            zin.closeEntry();
                        }
                        zin.close();
                        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, dsFileName));

                        FacesMessage fm = new FacesMessage("ZIP ZIP!!! " + dsFileName);
                        fm.setSeverity(FacesMessage.SEVERITY_INFO);
                        fc.addMessage(null, fm);
                        /*File arquivo = null;
                        InputStream is = null;
                        OutputStream os = null;
                        byte[] buffer = new byte[TAMANHO_BUFFER];
                        try {
                        //cria diretório informado, caso não exista
                        if( !diretorio.exists() ) {
                        diretorio.mkdirs();
                        }
                        if( !diretorio.exists() || !diretorio.isDirectory() ) {
                        throw new IOException("Informe um diretório válido");
                        }
                        zip = new ZipFile( arquivoZip );
                        Enumeration e = zip.entries();
                        while( e.hasMoreElements() ) {
                        ZipEntry entrada = (ZipEntry) e.nextElement();
                        arquivo = new File( diretorio, entrada.getName() );
                        //se for diretório inexistente, cria a estrutura
                        //e pula pra próxima entrada
                        if( entrada.isDirectory() && !arquivo.exists() ) {
                        arquivo.mkdirs();
                        continue;
                        }
                        //se a estrutura de diretórios não existe, cria
                        if( !arquivo.getParentFile().exists() ) {
                        arquivo.getParentFile().mkdirs();
                        }
                        try {
                        //lê o arquivo do zip e grava em disco
                        is = zip.getInputStream( entrada );
                        os = new FileOutputStream( arquivo );
                        int bytesLidos = 0;
                        if( is == null ) {
                        throw new ZipException("Erro ao ler a entrada do zip: "+entrada.getName());
                        }
                        while( (bytesLidos = is.read( buffer )) > 0 ) {
                        os.write( buffer, 0, bytesLidos );
                        }
                        } finally {
                        if( is != null ) {
                        try {
                        is.close();
                        } catch( Exception ex ) {}
                        }
                        if( os != null ) {
                        try {
                        os.close();
                        } catch( Exception ex ) {}
                        }
                        }
                        }
                        } finally {
                        if( zip != null ) {
                        try {
                        zip.close();
                        } catch( Exception e ) {}
                        }
                        } */
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

            FacesMessage fm = new FacesMessage("Evendo de upload inválido! Evento nulo!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
        } 
        
        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Fim do processo."));
    }
    
    public void importarCSV(FileUploadEvent fileUploaded){
        if(fileUploaded != null){
            String dsFileName = fileUploaded.getFile().getFileName();
            if(dsFileName.toLowerCase().endsWith(".geoinfo")){
                this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Arquivo carregado: " + dsFileName));
                if(dsFileName.toLowerCase().startsWith("venda"))
                    this.listaUploadedFileGeoInfoVenda.add(fileUploaded.getFile());
                else 
                    this.listaUploadedFileGeoInfo.add(fileUploaded.getFile());
            } 
        }
    }
    
    public void importarListaCSV(){
        this.listaGeoInfoLogNode.clear();
        
        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Inicio da importação de arquivo."));
        
        this.listaUploadedFileGeoInfo.addAll(this.listaUploadedFileGeoInfoVenda);
        
        
        FacesContext fc = FacesContext.getCurrentInstance();
        if(this.listaUploadedFileGeoInfo.size() > 0){
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
                
                this.dsMensagem = "Contando linhas a serem importadas!";
                
                Long nrTotalLinha = (long) 0;
                Iterator itCSV = this.listaUploadedFileGeoInfo.iterator();
                while((itCSV.hasNext())&&(!this.inFechouImportacao)){
                    UploadedFile ufCSV = (UploadedFile) itCSV.next();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ufCSV.getInputstream()));

                        while(bufferedReader.readLine() != null){
                            nrTotalLinha++;
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
                Long nrLinhaTodo = (long) 0;
                itCSV = this.listaUploadedFileGeoInfo.iterator();
                while((itCSV.hasNext())&&(!inErroCommit)&&(!this.inFechouImportacao)){
                    UploadedFile ufCSV = (UploadedFile) itCSV.next();

                    String dsArquivo = ufCSV.getFileName();
                    this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importando arquivo: " + dsArquivo));

                    ImportDataGeoInfoCSV importDataGeoInfoCSV = ImportDataGeoInfoCSVFactory.createByFileName(dsArquivo, entityManager, pessoaGerente);

                    if(importDataGeoInfoCSV != null){
                        importDataGeoInfoCSV.setListaGeoInfoLogNode(listaGeoInfoLogNode);
                        try {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ufCSV.getInputstream()));

                            entityManager.getTransaction().begin();

                            String dsLinha;
                            Long nrLinha = (long) 0;
                            while(((dsLinha = bufferedReader.readLine()) != null)&&(!inErroCommit)&&(!this.inFechouImportacao)){
                                nrLinha++;
                                nrLinhaTodo++;
                                
                                this.dsMensagem = "Importando linha " + nrLinha + " do arquivo " + dsArquivo;
                                System.out.println("Importando linha " + nrLinha + " do arquivo " + dsArquivo);

                                double vlProgresso = (nrLinhaTodo/nrTotalLinha);
                                this.pcProgresso = Math.round((vlProgresso) * 100);

                                importDataGeoInfoCSV.importar(nrLinha, dsLinha);

                                if((nrLinhaTodo % 1000) == 0){
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
                                        this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importação de dados finalizada com sucesso!"));

                                        FacesMessage fm = new FacesMessage("Importação de dados finalizada com sucesso!");
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
                            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, "Erro na importação de arquivo! " + ex.getMessage()));
                            
                            FacesMessage fm = new FacesMessage("Erro na importação de arquivo! " + ex.getMessage());
                            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                            fc.addMessage(null, fm);
                        }
                    }
                }
                this.pcProgresso = new Long(100);
            }else{
                this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "O usuário logado não é um usuário master! Somente usuários do tipo master (Administrador ou Estabelecimento) podem importar arquivos!"));
                
                FacesMessage fm = new FacesMessage("O usuário logado não é um usuário master! Somente usuários do tipo master (Administrador ou Estabelecimento) podem importar arquivos!");
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
            }
        }else{
            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Erro! Lista de arquivos GeoInfo (CSV) está vazia!"));
            
            FacesMessage fm = new FacesMessage("Erro! Lista de arquivos GeoInfo (CSV) está vazia!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, fm);
        }
    }
    
    public void fecharImportacao(CloseEvent closeEvent){
        this.inFechouImportacao = true;
        this.dsMensagem = "Importação não iniciada!";
        this.listaGeoInfoLogNode.clear();
        this.listaUploadedFileGeoInfo.clear();
        this.listaUploadedFileGeoInfoVenda.clear();
        this.listaUploadedFileXMLNFe.clear();
    }
    
    public void importarXMLNFe(FileUploadEvent fileUploaded){
        if(fileUploaded != null){
            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Arquivo carregado: " + fileUploaded.getFile().getFileName()));
            this.listaUploadedFileXMLNFe.add(fileUploaded.getFile());
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
                Long nrTotalArquivo = (long)listaUploadedFileXMLNFe.size();
                Long nrArquivo = (long) 0;

                boolean inErroCommit = false;
                Iterator itXMLNFE = this.listaUploadedFileXMLNFe.iterator();
                while((itXMLNFE.hasNext())&&(!inErroCommit)&&(!this.inFechouImportacao)){
                    UploadedFile ufXMLNFE = (UploadedFile)itXMLNFE.next();
                    
                    nrArquivo++;
                    
                    String dsArquivo = ufXMLNFE.getFileName();
                    this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importando arquivo: " + dsArquivo));
                                
                    this.dsMensagem = "Importando arquivo " + dsArquivo;
                    System.out.println("Importando arquivo " + dsArquivo);

                    double vlProgresso = (nrArquivo/nrTotalArquivo);
                    this.pcProgresso = Math.round((vlProgresso) * 100);
                    
                    try {
                        
                        
                        entityManager.getTransaction().begin();
                        
                        importDataGeoInfoXMLNFE.importar(ufXMLNFE.getInputstream(), true);

                        if((nrArquivo % 50) == 0){
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
                            this.listaGeoInfoLogNode.add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importação de dados finalizada com sucesso!"));

                            FacesMessage fm = new FacesMessage("Importação de dados finalizada com sucesso!");
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
