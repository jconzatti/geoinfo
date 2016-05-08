package com.geoinfo.data;

import com.geoinfo.entity.Cliente;
import com.geoinfo.entity.Estabelecimento;
import com.geoinfo.entity.ItemVenda;
import com.geoinfo.entity.ItemVendaPK;
import com.geoinfo.entity.Localizacao;
import com.geoinfo.entity.LocalizacaoDestinoVenda;
import com.geoinfo.entity.LocalizacaoDestinoVendaPK;
import com.geoinfo.entity.LocalizacaoOrigemVenda;
import com.geoinfo.entity.LocalizacaoOrigemVendaPK;
import com.geoinfo.entity.LocalizacaoPK;
import com.geoinfo.entity.PessoaMaster;
import com.geoinfo.entity.Produto;
import com.geoinfo.entity.ProdutoPK;
import com.geoinfo.entity.Venda;
import com.geoinfo.entity.VendaPK;
import com.geoinfo.model.GeoInfoLogNode;
import com.geoinfo.repository.CidadeRepository;
import com.geoinfo.repository.ClienteRepository;
import com.geoinfo.repository.EstabelecimentoRepository;
import com.geoinfo.repository.ItemVendaRepository;
import com.geoinfo.repository.LocalizacaoDestinoVendaRepository;
import com.geoinfo.repository.LocalizacaoOrigemVendaRepository;
import com.geoinfo.repository.LocalizacaoRepository;
import com.geoinfo.repository.ProdutoRepository;
import com.geoinfo.repository.VendaRepository;
import com.geoinfo.util.EGeoInfoLogType;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class ImportDataGeoInfoXMLNFE extends ImportDataGeoInfo{
    private final List<Integer> listaCFOPConsiderada;

    public ImportDataGeoInfoXMLNFE(EntityManager entityManager, PessoaMaster gerente) {
        super(entityManager, gerente);
        listaCFOPConsiderada = new ArrayList<Integer>();
        listaCFOPConsiderada.add(5101);
        listaCFOPConsiderada.add(5102);
        listaCFOPConsiderada.add(5103);
        listaCFOPConsiderada.add(5104);
        listaCFOPConsiderada.add(5105);
        listaCFOPConsiderada.add(5106);
        listaCFOPConsiderada.add(5109);
        listaCFOPConsiderada.add(5110);
        listaCFOPConsiderada.add(5111);
        listaCFOPConsiderada.add(5112);
        listaCFOPConsiderada.add(5113);
        listaCFOPConsiderada.add(5114);
        listaCFOPConsiderada.add(5115);
        listaCFOPConsiderada.add(5116);
        listaCFOPConsiderada.add(5117);
        listaCFOPConsiderada.add(5118);
        listaCFOPConsiderada.add(5119);
        listaCFOPConsiderada.add(5120);
        listaCFOPConsiderada.add(5122);
        listaCFOPConsiderada.add(5123);
        listaCFOPConsiderada.add(5124);
        listaCFOPConsiderada.add(5125);
        listaCFOPConsiderada.add(5251);
        listaCFOPConsiderada.add(5252);
        listaCFOPConsiderada.add(5253);
        listaCFOPConsiderada.add(5254);
        listaCFOPConsiderada.add(5255);
        listaCFOPConsiderada.add(5256);
        listaCFOPConsiderada.add(5257);
        listaCFOPConsiderada.add(5258);
        listaCFOPConsiderada.add(5301);
        listaCFOPConsiderada.add(5302);
        listaCFOPConsiderada.add(5303);
        listaCFOPConsiderada.add(5304);
        listaCFOPConsiderada.add(5305);
        listaCFOPConsiderada.add(5306);
        listaCFOPConsiderada.add(5307);
        listaCFOPConsiderada.add(5351);
        listaCFOPConsiderada.add(5352);
        listaCFOPConsiderada.add(5353);
        listaCFOPConsiderada.add(5354);
        listaCFOPConsiderada.add(5355);
        listaCFOPConsiderada.add(5356);
        listaCFOPConsiderada.add(5357);
        listaCFOPConsiderada.add(5359);
        listaCFOPConsiderada.add(5360);
        listaCFOPConsiderada.add(5401);
        listaCFOPConsiderada.add(5402);
        listaCFOPConsiderada.add(5403);
        listaCFOPConsiderada.add(5405);
        listaCFOPConsiderada.add(5551);
        listaCFOPConsiderada.add(5651);
        listaCFOPConsiderada.add(5652);
        listaCFOPConsiderada.add(5653);
        listaCFOPConsiderada.add(5654);
        listaCFOPConsiderada.add(5655);
        listaCFOPConsiderada.add(5656);
        listaCFOPConsiderada.add(5929);
        listaCFOPConsiderada.add(5932);
        listaCFOPConsiderada.add(5933);
        listaCFOPConsiderada.add(6101);
        listaCFOPConsiderada.add(6102);
        listaCFOPConsiderada.add(6103);
        listaCFOPConsiderada.add(6104);
        listaCFOPConsiderada.add(6105);
        listaCFOPConsiderada.add(6106);
        listaCFOPConsiderada.add(6107);
        listaCFOPConsiderada.add(6108);
        listaCFOPConsiderada.add(6109);
        listaCFOPConsiderada.add(6110);
        listaCFOPConsiderada.add(6111);
        listaCFOPConsiderada.add(6112);
        listaCFOPConsiderada.add(6113);
        listaCFOPConsiderada.add(6114);
        listaCFOPConsiderada.add(6115);
        listaCFOPConsiderada.add(6116);
        listaCFOPConsiderada.add(6117);
        listaCFOPConsiderada.add(6118);
        listaCFOPConsiderada.add(6119);
        listaCFOPConsiderada.add(6120);
        listaCFOPConsiderada.add(6122);
        listaCFOPConsiderada.add(6123);
        listaCFOPConsiderada.add(6124);
        listaCFOPConsiderada.add(6125);
        listaCFOPConsiderada.add(6251);
        listaCFOPConsiderada.add(6252);
        listaCFOPConsiderada.add(6253);
        listaCFOPConsiderada.add(6254);
        listaCFOPConsiderada.add(6255);
        listaCFOPConsiderada.add(6256);
        listaCFOPConsiderada.add(6257);
        listaCFOPConsiderada.add(6258);
        listaCFOPConsiderada.add(6301);
        listaCFOPConsiderada.add(6302);
        listaCFOPConsiderada.add(6303);
        listaCFOPConsiderada.add(6304);
        listaCFOPConsiderada.add(6305);
        listaCFOPConsiderada.add(6306);
        listaCFOPConsiderada.add(6307);
        listaCFOPConsiderada.add(6351);
        listaCFOPConsiderada.add(6352);
        listaCFOPConsiderada.add(6353);
        listaCFOPConsiderada.add(6354);
        listaCFOPConsiderada.add(6355);
        listaCFOPConsiderada.add(6356);
        listaCFOPConsiderada.add(6357);
        listaCFOPConsiderada.add(6359);
        listaCFOPConsiderada.add(6401);
        listaCFOPConsiderada.add(6402);
        listaCFOPConsiderada.add(6403);
        listaCFOPConsiderada.add(6404);
        listaCFOPConsiderada.add(6551);
        listaCFOPConsiderada.add(6651);
        listaCFOPConsiderada.add(6652);
        listaCFOPConsiderada.add(6653);
        listaCFOPConsiderada.add(6654);
        listaCFOPConsiderada.add(6655);
        listaCFOPConsiderada.add(6656);
        listaCFOPConsiderada.add(6929);
        listaCFOPConsiderada.add(6932);
        listaCFOPConsiderada.add(6933);
        listaCFOPConsiderada.add(7101);
        listaCFOPConsiderada.add(7102);
        listaCFOPConsiderada.add(7105);
        listaCFOPConsiderada.add(7106);
        listaCFOPConsiderada.add(7127);
        listaCFOPConsiderada.add(7251);
        listaCFOPConsiderada.add(7301);
        listaCFOPConsiderada.add(7358);
        listaCFOPConsiderada.add(7551);
        listaCFOPConsiderada.add(7651);
        listaCFOPConsiderada.add(7654);
    }
    
    public boolean importar(InputStream inputStreamXMLNFE, boolean inImportarHomologacao) throws JDOMException, IOException{
        Document document = new SAXBuilder().build(inputStreamXMLNFE);
        Element eRoot = document.getRootElement();
        if(eRoot.getName().equals("nfeProc")){
            Element eNFe = getChildElement(eRoot, "protNFe");
            if(eNFe!=null)
                eNFe = getChildElement(eNFe, "infProt");
            
            int idAmbiente = 0;
            int cdStatus = 0;
            if(eNFe!=null){
                Iterator i =  eNFe.getChildren().iterator();
                while(i.hasNext()){
                    Element e = (Element)i.next();
                    String dsNome = e.getName();
                    if(dsNome.equals("tpAmb")){
                        idAmbiente = Integer.parseInt(e.getText());
                    }else if(dsNome.equals("cStat")){
                        cdStatus = Integer.parseInt(e.getText());
                    }
                }
            }
            
            if(cdStatus == 100){
                if((idAmbiente == 1)||(inImportarHomologacao)){
                    eNFe = getChildElement(eRoot, "NFe");
                    if(eNFe!=null){
                        Element eInfNFe = getChildElement(eNFe, "infNFe");
                        if(eInfNFe!=null){
                            eNFe = getChildElement(eInfNFe, "ide");
                            
                            VendaPK vendaPK = new VendaPK();
                            Venda venda = new Venda();
                            venda.setVendaPK(vendaPK);
                            
                            Long cdModelo = null;
                            Long cdSerie = null;
                            Long nrNotaFiscal = null;
                            if(eNFe!=null){
                                Iterator i =  eNFe.getChildren().iterator();
                                while(i.hasNext()){
                                    Element e = (Element)i.next();
                                    String dsNome = e.getName();
                                    if(dsNome.equals("mod")){
                                        cdModelo = Long.parseLong(e.getText());
                                    }else if(dsNome.equals("serie")){
                                        cdSerie = Long.parseLong(e.getText());
                                    }else if(dsNome.equals("nNF")){
                                        nrNotaFiscal = Long.parseLong(e.getText());
                                    }else if(dsNome.equals("dhEmi")){
                                        String dtEmissaoNF = e.getText();
                                        try {
                                            Date dtVenda = new SimpleDateFormat("dd/MM/yyyy").parse(dtEmissaoNF.substring(8, 10) + "/" +
                                                    dtEmissaoNF.substring(5, 7) + "/" + dtEmissaoNF.substring(0, 4));
                                            venda.setDtVenda(dtVenda);
                                        } catch (ParseException ex) {
                                        }
                                    }else if(dsNome.equals("dEmi")){//para as NFes 2.10 ou inferiores
                                        String dtEmissaoNF = e.getText();
                                        try {
                                            Date dtVenda = new SimpleDateFormat("dd/MM/yyyy").parse(dtEmissaoNF.substring(8, 10) + "/" +
                                                    dtEmissaoNF.substring(5, 7) + "/" + dtEmissaoNF.substring(0, 4));
                                            venda.setDtVenda(dtVenda);
                                        } catch (ParseException ex) {
                                        }
                                    }
                                }
                                
                                boolean inErro = false;
                                if(cdModelo == null){
                                    inErro = true;
                                    if(this.getListaGeoInfoLogNode() != null){
                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                "Não foi possível capturar o modelo da NFe: 55 ou 65!"));
                                    }
                                }
                                
                                if(!inErro){
                                    if(cdSerie == null){
                                        inErro = true;
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                    "Não foi possível capturar a série da NFe: campo numérico!"));
                                        }
                                    }
                                }
                                
                                if(!inErro){
                                    if(nrNotaFiscal == null){
                                        inErro = true;
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                    "Não foi possível capturar o número da NFe: campo numérico!"));
                                        }
                                    }
                                }
                                
                                if(!inErro){
                                    if(venda.getDtVenda() == null){
                                        inErro = true;
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                    "Não foi possível capturar a data de emissão da NFe modelo: " + cdModelo
                                                            + ", série: " + cdSerie  + ", numero: " + nrNotaFiscal + "!"));
                                        }
                                    }
                                }
                                
                                if(!inErro){
                                    try{
                                        Long cdVenda = Long.parseLong(new DecimalFormat("00").format(cdModelo) +
                                                new DecimalFormat("000").format(cdSerie) +
                                                new DecimalFormat("000000000").format(nrNotaFiscal));
                                        venda.getVendaPK().setCdVenda(cdVenda);
                                    }catch(NumberFormatException nfe){
                                        inErro = true;
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                    "Não foi possível formar o código da venda: " + cdModelo
                                                            + ", série: " + cdSerie  + ", numero: " + nrNotaFiscal + "!\n"
                                                            + nfe.getMessage()));
                                        }
                                    }
                                }
                                
                                if(!inErro){
                                    eNFe = getChildElement(eInfNFe, "emit");
                                    Estabelecimento estabelecimento = new Estabelecimento();
                                    estabelecimento.setGerente(this.getGerente());
                                    if(eNFe != null){
                                        i = eNFe.getChildren().iterator();
                                        while(i.hasNext()){
                                            Element e = (Element) i.next();
                                            String dsNome = e.getName();
                                            if(dsNome.equals("CPF") || dsNome.equals("CNPJ")){
                                                estabelecimento.setCdExterno(e.getText());
                                            }else if(dsNome.equals("xNome")){
                                                estabelecimento.setDsPessoa(e.getText());
                                            }
                                        }
                                        
                                        if(!inErro){
                                            if(estabelecimento.getCdExterno() == null){
                                                inErro = true;
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                            "Não foi possível capturar o código do emitente (CPF/CNPJ) da NFe modelo: " +
                                                                    cdModelo + ", série: " + cdSerie + ", número: " + nrNotaFiscal + "!"));
                                                }
                                            }
                                        }
                                        
                                        if(!inErro){
                                            if(estabelecimento.getDsPessoa() == null){
                                                inErro = true;
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                            "Não foi possível capturar o nome do emitente da NFe modelo: " +
                                                                    cdModelo + ", série: " + cdSerie + ", número: " + nrNotaFiscal + "!"));
                                                }
                                            }
                                        }
                                        
                                        if(!inErro){
                                            eNFe = getChildElement(eNFe, "enderEmit");
                                            
                                            EstabelecimentoRepository estabelecimentoRepository = new EstabelecimentoRepository(this.getEntityManager());
                                            Estabelecimento es = estabelecimentoRepository.find(estabelecimento.getGerente(), estabelecimento.getCdExterno());
                                            if(es == null){
                                                estabelecimentoRepository.insert(estabelecimento);
                                            }else{
                                                if(!estabelecimento.getDsPessoa().equals(es.getDsPessoa())){
                                                    es.setDsPessoa(estabelecimento.getDsPessoa());
                                                    estabelecimentoRepository.edit(es);
                                                }
                                                estabelecimento = es;
                                            }
                                            
                                            LocalizacaoPK localizacaoEPK = new LocalizacaoPK();
                                            Localizacao localizacaoE = new Localizacao();
                                            localizacaoE.setLocalizacaoPK(localizacaoEPK);
                                            localizacaoE.getLocalizacaoPK().setPessoa(estabelecimento);
                                            venda.getVendaPK().setEstabelecimento(estabelecimento);
                                            
                                            if(eNFe != null){
                                                i = eNFe.getChildren().iterator();
                                                while(i.hasNext()){
                                                    Element e = (Element) i.next();
                                                    String dsNome = e.getName();
                                                    if(dsNome.equals("xLgr")){
                                                        localizacaoE.setDsEndereco(e.getText());
                                                    }else if(dsNome.equals("nro")){
                                                        localizacaoE.setDsNumero(e.getText());
                                                    }else if(dsNome.equals("xBairro")){
                                                        localizacaoE.setDsBairro(e.getText());
                                                    }else if(dsNome.equals("cMun")){
                                                        CidadeRepository cidadeRepository = new CidadeRepository(this.getEntityManager());
                                                        localizacaoE.getLocalizacaoPK().setCidade(cidadeRepository.findIBGE(Long.parseLong(e.getText())));
                                                    }
                                                }

                                                if(!inErro){
                                                    if(localizacaoE.getLocalizacaoPK().getCidade() == null){
                                                        inErro = true;
                                                        if(this.getListaGeoInfoLogNode() != null){
                                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                                    "Não foi possível capturar a cidade (Código IBGE) do emitente da NFe modelo: " +
                                                                            cdModelo + ", série: " + cdSerie + ", número: " + nrNotaFiscal + "!"));
                                                        }
                                                    }
                                                }
                                                
                                                if(!inErro){
                                                    LocalizacaoRepository localizacaoRepository = new LocalizacaoRepository(this.getEntityManager());
                                                    Localizacao le = localizacaoRepository.getLocalizacaoRecente(estabelecimento.getCdPessoa());
                                                    if((!localizacaoE.getDsBairro().equals(le.getDsBairro()))
                                                            ||(!localizacaoE.getDsEndereco().equals(le.getDsEndereco()))
                                                            ||(!localizacaoE.getDsNumero().equals(le.getDsNumero()))){
                                                        le = null;
                                                    }
                                                    if(le==null){
                                                        localizacaoE.getLocalizacaoPK().setDtLocalizacao(venda.getDtVenda());
                                                        localizacaoRepository.insert(localizacaoE);
                                                    }
                                                }

                                                Localizacao localizacaoC = null;
                                                if(!inErro){
                                                    eNFe = getChildElement(eInfNFe, "dest");
                                                    Cliente cliente = new Cliente();
                                                    cliente.setGerente(this.getGerente());
                                                    if(eNFe != null){
                                                        i = eNFe.getChildren().iterator();
                                                        while(i.hasNext()){
                                                            Element e = (Element) i.next();
                                                            String dsNome = e.getName();
                                                            if(dsNome.equals("CPF") || dsNome.equals("CNPJ") || dsNome.equals("idEstrangeiro")){
                                                                cliente.setCdExterno(e.getText());
                                                            }else if(dsNome.equals("xNome")){
                                                                cliente.setDsPessoa(e.getText());
                                                            }
                                                        }

                                                        if(!inErro){
                                                            if(cliente.getCdExterno() == null){
                                                                inErro = true;
                                                                if(this.getListaGeoInfoLogNode() != null){
                                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                                            "Não foi possível capturar o código do destinatário (CPF/CNPJ/idEstrangeiro) da NFe modelo: " +
                                                                                    cdModelo + ", série: " + cdSerie + ", número: " + nrNotaFiscal + "!"));
                                                                }
                                                            }
                                                        }

                                                        if(!inErro){
                                                            if(cliente.getDsPessoa() == null){
                                                                inErro = true;
                                                                if(this.getListaGeoInfoLogNode() != null){
                                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                                            "Não foi possível capturar o nome do destinatário da NFe modelo: " +
                                                                                    cdModelo + ", série: " + cdSerie + ", número: " + nrNotaFiscal + "!"));
                                                                }
                                                            }
                                                        }

                                                        if(!inErro){
                                                            eNFe = getChildElement(eNFe, "enderDest");
                                            
                                                            ClienteRepository clienteRepository = new ClienteRepository(this.getEntityManager());
                                                            Cliente cl = clienteRepository.find(cliente.getGerente(), cliente.getCdExterno());
                                                            if(cl == null){
                                                                clienteRepository.insert(cliente);
                                                            }else{
                                                                if(!cliente.getDsPessoa().equals(cl.getDsPessoa())){
                                                                    cl.setDsPessoa(cliente.getDsPessoa());
                                                                    clienteRepository.edit(cl);
                                                                }
                                                                cliente = cl;
                                                            }

                                                            LocalizacaoPK localizacaoCPK = new LocalizacaoPK();
                                                            localizacaoC = new Localizacao();
                                                            localizacaoC.setLocalizacaoPK(localizacaoCPK);
                                                            localizacaoC.getLocalizacaoPK().setPessoa(cliente);
                                                            venda.setCliente(cliente);

                                                            if(eNFe != null){
                                                                i = eNFe.getChildren().iterator();
                                                                while(i.hasNext()){
                                                                    Element e = (Element) i.next();
                                                                    String dsNome = e.getName();
                                                                    if(dsNome.equals("xLgr")){
                                                                        localizacaoC.setDsEndereco(e.getText());
                                                                    }else if(dsNome.equals("nro")){
                                                                        localizacaoC.setDsNumero(e.getText());
                                                                    }else if(dsNome.equals("xBairro")){
                                                                        localizacaoC.setDsBairro(e.getText());
                                                                    }else if(dsNome.equals("cMun")){
                                                                        CidadeRepository cidadeRepository = new CidadeRepository(this.getEntityManager());
                                                                        localizacaoC.getLocalizacaoPK().setCidade(cidadeRepository.findIBGE(Long.parseLong(e.getText())));
                                                                    }
                                                                }

                                                                if(!inErro){
                                                                    if(localizacaoC.getLocalizacaoPK().getCidade() == null){
                                                                        inErro = true;
                                                                        if(this.getListaGeoInfoLogNode() != null){
                                                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                                                    "Não foi possível capturar a cidade (Código IBGE) do destinatário da NFe modelo: " +
                                                                                            cdModelo + ", série: " + cdSerie + ", número: " + nrNotaFiscal + "!"));
                                                                        }
                                                                    }
                                                                }
                                                
                                                                if(!inErro){
                                                                    LocalizacaoRepository localizacaoRepository = new LocalizacaoRepository(this.getEntityManager());
                                                                    Localizacao lc = localizacaoRepository.getLocalizacaoRecente(cliente.getCdPessoa());
                                                                    if((!localizacaoC.getDsBairro().equals(lc.getDsBairro()))
                                                                            ||(!localizacaoC.getDsEndereco().equals(lc.getDsEndereco()))
                                                                            ||(!localizacaoC.getDsNumero().equals(lc.getDsNumero()))){
                                                                        lc = null;
                                                                    }
                                                                    if(lc==null){
                                                                        localizacaoC.getLocalizacaoPK().setDtLocalizacao(venda.getDtVenda());
                                                                        localizacaoRepository.insert(localizacaoC);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                                
                                                    if(!inErro){
                                                        VendaRepository vendaRepository = new VendaRepository(this.getEntityManager());
                                                        Venda v = vendaRepository.find(venda.getVendaPK());
                                                        if(v==null){
                                                            venda.setIdStatus((short)0);
                                                            vendaRepository.insert(venda);
                                                        }else{
                                                            venda.setIdStatus(v.getIdStatus());
                                                        }

                                                        eNFe = getChildElement(eInfNFe, "retirada");

                                                        LocalizacaoOrigemVendaPK localizacaoOrigemVendaPK = new LocalizacaoOrigemVendaPK();
                                                        localizacaoOrigemVendaPK.setVenda(venda);

                                                        LocalizacaoOrigemVendaRepository localizacaoOrigemVendaRepository = new LocalizacaoOrigemVendaRepository(this.getEntityManager());
                                                        if (localizacaoOrigemVendaRepository.find(localizacaoOrigemVendaPK) != null)
                                                            localizacaoOrigemVendaRepository.delete(localizacaoOrigemVendaPK);

                                                        LocalizacaoOrigemVenda localizacaoOrigemVenda = new LocalizacaoOrigemVenda();
                                                        localizacaoOrigemVenda.setLocalizacaoOrigemVendaPK(localizacaoOrigemVendaPK);

                                                        if(eNFe == null){
                                                            localizacaoOrigemVenda.setCidade(localizacaoE.getLocalizacaoPK().getCidade());
                                                            localizacaoOrigemVenda.setDsBairro(localizacaoE.getDsBairro());
                                                            localizacaoOrigemVenda.setDsEndereco(localizacaoE.getDsEndereco());
                                                            localizacaoOrigemVenda.setDsNumero(localizacaoE.getDsNumero());
                                                        }else{
                                                            i = eNFe.getChildren().iterator();
                                                            while(i.hasNext()){
                                                                Element e = (Element) i.next();
                                                                String dsNome = e.getName();
                                                                if(dsNome.equals("xLgr")){
                                                                    localizacaoOrigemVenda.setDsEndereco(e.getText());
                                                                }else if(dsNome.equals("nro")){
                                                                    localizacaoOrigemVenda.setDsNumero(e.getText());
                                                                }else if(dsNome.equals("xBairro")){
                                                                    localizacaoOrigemVenda.setDsBairro(e.getText());
                                                                }else if(dsNome.equals("cMun")){
                                                                    CidadeRepository cidadeRepository = new CidadeRepository(this.getEntityManager());
                                                                    localizacaoOrigemVenda.setCidade(cidadeRepository.findIBGE(Long.parseLong(e.getText())));
                                                                }
                                                            }

                                                            if(!inErro){
                                                                if(localizacaoOrigemVenda.getCidade() == null){
                                                                    inErro = true;
                                                                    if(this.getListaGeoInfoLogNode() != null){
                                                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                                                "Não foi possível capturar a cidade (Código IBGE) do local de retirada da NFe modelo: " +
                                                                                        cdModelo + ", série: " + cdSerie + ", número: " + nrNotaFiscal + "!"));
                                                                    }
                                                                }
                                                            }
                                                        }

                                                        eNFe = getChildElement(eInfNFe, "entrega");

                                                        LocalizacaoDestinoVendaPK localizacaoDestinoVendaPK = new LocalizacaoDestinoVendaPK();
                                                        localizacaoDestinoVendaPK.setVenda(venda);

                                                        LocalizacaoDestinoVendaRepository localizacaoDestinoVendaRepository = new LocalizacaoDestinoVendaRepository(this.getEntityManager());
                                                        if (localizacaoDestinoVendaRepository.find(localizacaoDestinoVendaPK) != null)
                                                            localizacaoDestinoVendaRepository.delete(localizacaoDestinoVendaPK);

                                                        LocalizacaoDestinoVenda localizacaoDestinoVenda = new LocalizacaoDestinoVenda();
                                                        localizacaoDestinoVenda.setLocalizacaoDestinoVendaPK(localizacaoDestinoVendaPK);

                                                        if(eNFe == null){
                                                            if((localizacaoC != null)&&(localizacaoC.getLocalizacaoPK() != null)){
                                                                localizacaoDestinoVenda.setCidade(localizacaoC.getLocalizacaoPK().getCidade());
                                                                localizacaoDestinoVenda.setDsBairro(localizacaoC.getDsBairro());
                                                                localizacaoDestinoVenda.setDsEndereco(localizacaoC.getDsEndereco());
                                                                localizacaoDestinoVenda.setDsNumero(localizacaoC.getDsNumero());
                                                            }else{
                                                                localizacaoDestinoVenda.setCidade(localizacaoE.getLocalizacaoPK().getCidade());
                                                                localizacaoDestinoVenda.setDsBairro(localizacaoE.getDsBairro());
                                                                localizacaoDestinoVenda.setDsEndereco(localizacaoE.getDsEndereco());
                                                                localizacaoDestinoVenda.setDsNumero(localizacaoE.getDsNumero());
                                                            }
                                                        }else{
                                                            i = eNFe.getChildren().iterator();
                                                            while(i.hasNext()){
                                                                Element e = (Element) i.next();
                                                                String dsNome = e.getName();
                                                                if(dsNome.equals("xLgr")){
                                                                    localizacaoDestinoVenda.setDsEndereco(e.getText());
                                                                }else if(dsNome.equals("nro")){
                                                                    localizacaoDestinoVenda.setDsNumero(e.getText());
                                                                }else if(dsNome.equals("xBairro")){
                                                                    localizacaoDestinoVenda.setDsBairro(e.getText());
                                                                }else if(dsNome.equals("cMun")){
                                                                    CidadeRepository cidadeRepository = new CidadeRepository(this.getEntityManager());
                                                                    localizacaoDestinoVenda.setCidade(cidadeRepository.findIBGE(Long.parseLong(e.getText())));
                                                                }
                                                            }

                                                            if(!inErro){
                                                                if(localizacaoDestinoVenda.getCidade() == null){
                                                                    inErro = true;
                                                                    if(this.getListaGeoInfoLogNode() != null){
                                                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                                                "Não foi possível capturar a cidade (Código IBGE) do local de entrega da NFe modelo: " +
                                                                                        cdModelo + ", série: " + cdSerie + ", número: " + nrNotaFiscal + "!"));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                                    
                                                        if(!inErro){
                                                            localizacaoOrigemVendaRepository.insert(localizacaoOrigemVenda);
                                                            localizacaoDestinoVendaRepository.insert(localizacaoDestinoVenda);

                                                            double vlDesconto = 0;
                                                            long nItem = 0;
                                                            do{
                                                                nItem++;
                                                                eNFe = getChildElement(eInfNFe, "det", "nItem", String.valueOf(nItem));
                                                                if(eNFe != null){
                                                                    Element eProdNFe = getChildElement(eNFe, "prod");
                                                                    if(eProdNFe != null){
                                                                        ProdutoPK produtoPK = new ProdutoPK();
                                                                        Produto produto = new Produto();
                                                                        produto.setProdutoPK(produtoPK);
                                                                        produto.getProdutoPK().setGerente(this.getGerente());


                                                                        ItemVendaPK itemVendaPK = new ItemVendaPK();
                                                                        ItemVenda itemVenda = new ItemVenda();
                                                                        itemVenda.setItemVendaPK(itemVendaPK);
                                                                        itemVenda.getItemVendaPK().setVenda(venda);
                                                                        itemVenda.getItemVendaPK().setCdItemVenda(nItem);

                                                                        double vlTotalItem = 0;
                                                                        int cdCFOP = 0;
                                                                        i = eProdNFe.getChildren().iterator();
                                                                        while(i.hasNext()){
                                                                            Element e = (Element) i.next();
                                                                            String dsNome = e.getName();
                                                                            if(dsNome.equals("cProd")){
                                                                                produto.getProdutoPK().setCdProduto(e.getText());
                                                                            }else if(dsNome.equals("xProd")){
                                                                                produto.setDsProduto(e.getText());
                                                                            }else if(dsNome.equals("qCom")){
                                                                                itemVenda.setQtProduto(Double.parseDouble(e.getText()));
                                                                            }else if(dsNome.equals("CFOP")){
                                                                                cdCFOP = Integer.parseInt(e.getText());
                                                                            }else if(dsNome.equals("vDesc")){
                                                                                vlDesconto += Double.parseDouble(e.getText());
                                                                            }else if(dsNome.equals("vProd")){
                                                                                vlTotalItem += Double.parseDouble(e.getText());
                                                                            }else if(dsNome.equals("vFrete")){
                                                                                vlTotalItem += Double.parseDouble(e.getText());
                                                                            }else if(dsNome.equals("vSeg")){
                                                                                vlTotalItem += Double.parseDouble(e.getText());
                                                                            }else if(dsNome.equals("vOutro")){
                                                                                vlTotalItem += Double.parseDouble(e.getText());
                                                                            }
                                                                        }

                                                                        if(listaCFOPConsiderada.contains(cdCFOP)){
                                                                            Element eImpNFe = getChildElement(eNFe, "imposto");
                                                                            if(eImpNFe != null){
                                                                                Element eImp2NFe = getChildElement(eImpNFe, "ICMS");
                                                                                if(eImp2NFe != null){
                                                                                    Element eImp3NFe = getChildElement(eImp2NFe, "ICMS10");
                                                                                    if(eImp3NFe==null)
                                                                                        eImp3NFe = getChildElement(eImp2NFe, "ICMS30");
                                                                                    if(eImp3NFe==null)
                                                                                        eImp3NFe = getChildElement(eImp2NFe, "ICMS70");
                                                                                    if(eImp3NFe==null)
                                                                                        eImp3NFe = getChildElement(eImp2NFe, "ICMS90");
                                                                                    if(eImp3NFe==null)
                                                                                        eImp3NFe = getChildElement(eImp2NFe, "ICMSPart");
                                                                                    if(eImp3NFe==null)
                                                                                        eImp3NFe = getChildElement(eImp2NFe, "ICMSSN201");
                                                                                    if(eImp3NFe==null)
                                                                                        eImp3NFe = getChildElement(eImp2NFe, "ICMSSN202");
                                                                                    if(eImp3NFe==null)
                                                                                        eImp3NFe = getChildElement(eImp2NFe, "ICMSSN900");

                                                                                    if(eImp3NFe!=null){
                                                                                        i = eImp3NFe.getChildren().iterator();
                                                                                        while(i.hasNext()){
                                                                                            Element e = (Element) i.next();
                                                                                            String dsNome = e.getName();
                                                                                            if(dsNome.equals("vICMSST")){
                                                                                                vlTotalItem += Double.parseDouble(e.getText());
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }

                                                                                eImp2NFe = getChildElement(eImpNFe, "IPI");
                                                                                if(eImp2NFe != null){
                                                                                    Element eImp3NFe = getChildElement(eImp2NFe, "IPITrib");

                                                                                    if(eImp3NFe!=null){
                                                                                        i = eImp3NFe.getChildren().iterator();
                                                                                        while(i.hasNext()){
                                                                                            Element e = (Element) i.next();
                                                                                            String dsNome = e.getName();
                                                                                            if(dsNome.equals("vIPI")){
                                                                                                vlTotalItem += Double.parseDouble(e.getText());
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }

                                                                                eImp2NFe = getChildElement(eImpNFe, "II");
                                                                                if(eImp2NFe != null){
                                                                                    i = eImp2NFe.getChildren().iterator();
                                                                                    while(i.hasNext()){
                                                                                        Element e = (Element) i.next();
                                                                                        String dsNome = e.getName();
                                                                                        if(dsNome.equals("vII")){
                                                                                            vlTotalItem += Double.parseDouble(e.getText());
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }

                                                                            ProdutoRepository produtoRepository = new ProdutoRepository(this.getEntityManager());
                                                                            Produto p = produtoRepository.find(produto.getProdutoPK());
                                                                            if(p==null){
                                                                                produtoRepository.insert(produto);
                                                                            }else{
                                                                                if(!produto.getDsProduto().equals(p.getDsProduto())){
                                                                                    produtoRepository.edit(produto);
                                                                                }
                                                                            }

                                                                            double vlUnitarioItem = 0;
                                                                            if((vlTotalItem > 0)&&(itemVenda.getQtProduto() > 0)){
                                                                                vlUnitarioItem = vlTotalItem/itemVenda.getQtProduto();
                                                                            }
                                                                            itemVenda.setProduto(produto);
                                                                            itemVenda.setVlProduto(vlUnitarioItem);

                                                                            venda.setVlDesconto(vlDesconto);
                                                                            vendaRepository.edit(venda);

                                                                            ItemVendaRepository itemVendaRepository = new ItemVendaRepository(this.getEntityManager());
                                                                            ItemVenda iv = itemVendaRepository.find(itemVenda.getItemVendaPK());
                                                                            if(iv==null){
                                                                                itemVendaRepository.insert(itemVenda);
                                                                            }else{
                                                                                itemVendaRepository.edit(itemVenda);
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }while(eNFe != null);
                                                            return true;
                                                        }
                                                    }                                                           
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private Element getChildElement(Element parentElement, String dsChildElement){
        return getChildElement(parentElement, dsChildElement, null, null);
    }
    
    private Element getChildElement(Element parentElement, String dsChildElement, String dsChildAttributeName, String dsChildAttributeValue){
        Element childElement = null;
        if(parentElement != null){
            List lista = parentElement.getChildren();
            Iterator i =  lista.iterator();
            while((i.hasNext())&&(childElement == null)){
                Element e = (Element)i.next();
                String dsNome = e.getName();
                if(dsNome.equals(dsChildElement)){
                    if((dsChildAttributeName != null)&&(!dsChildAttributeName.equals(""))){
                        String dsAtributo = e.getAttributeValue(dsChildAttributeName);
                        if(dsAtributo!=null){
                            if(dsAtributo.equals(dsChildAttributeValue))
                                childElement = e;
                        }
                    }else{
                        childElement = e;
                    }
                }
            }
        }
        return childElement;
    }
    
    
}
