package com.geoinfo.data;

import com.geoinfo.entity.Cidade;
import com.geoinfo.entity.Cliente;
import com.geoinfo.entity.Estabelecimento;
import com.geoinfo.entity.Estado;
import com.geoinfo.entity.FormaPagamento;
import com.geoinfo.entity.FormaPagamentoPK;
import com.geoinfo.entity.FormaPagamentoVenda;
import com.geoinfo.entity.FormaPagamentoVendaPK;
import com.geoinfo.entity.ItemVenda;
import com.geoinfo.entity.ItemVendaPK;
import com.geoinfo.entity.Localizacao;
import com.geoinfo.entity.LocalizacaoDestinoVenda;
import com.geoinfo.entity.LocalizacaoDestinoVendaPK;
import com.geoinfo.entity.LocalizacaoOrigemVenda;
import com.geoinfo.entity.LocalizacaoOrigemVendaPK;
import com.geoinfo.entity.Pais;
import com.geoinfo.entity.PessoaMaster;
import com.geoinfo.entity.Produto;
import com.geoinfo.entity.ProdutoPK;
import com.geoinfo.entity.Venda;
import com.geoinfo.entity.VendaPK;
import com.geoinfo.entity.Vendedor;
import com.geoinfo.entity.VendedorVenda;
import com.geoinfo.entity.VendedorVendaPK;
import com.geoinfo.repository.ClienteRepository;
import com.geoinfo.repository.EstabelecimentoRepository;
import com.geoinfo.repository.FormaPagamentoRepository;
import com.geoinfo.repository.FormaPagamentoVendaRepository;
import com.geoinfo.repository.ItemVendaRepository;
import com.geoinfo.repository.ProdutoRepository;
import com.geoinfo.repository.VendaRepository;
import com.geoinfo.repository.VendedorRepository;
import com.geoinfo.repository.VendedorVendaRepository;
import com.geoinfo.util.EGeoInfoLogType;
import com.geoinfo.model.GeoInfoLogNode;
import com.geoinfo.repository.CidadeRepository;
import com.geoinfo.repository.EstadoRepository;
import com.geoinfo.repository.LocalizacaoDestinoVendaRepository;
import com.geoinfo.repository.LocalizacaoOrigemVendaRepository;
import com.geoinfo.repository.LocalizacaoRepository;
import com.geoinfo.repository.PaisRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

public class ImportDataGeoInfoCSVVenda extends ImportDataGeoInfoCSV{
    private Venda venda;
    private final ImportDataGeoInfoCSVCliente importDataGeoInfoCliente;
    private final ImportDataGeoInfoCSVEstabelecimento importDataGeoInfoEstabelecimento;
    private final ImportDataGeoInfoCSVVendedor importDataGeoInfoVendedor;
    private final ImportDataGeoInfoCSVFormaPagamento importDataGeoInfoFormaPagamento;
    private final ImportDataGeoInfoCSVProduto importDataGeoInfoProduto;
    
    public ImportDataGeoInfoCSVVenda(EntityManager entityManager, PessoaMaster gerente) {
        super(entityManager, gerente);
        this.venda = null;
        this.importDataGeoInfoCliente = new ImportDataGeoInfoCSVCliente(entityManager, gerente);
        this.importDataGeoInfoEstabelecimento = new ImportDataGeoInfoCSVEstabelecimento(entityManager, gerente);
        this.importDataGeoInfoVendedor = new ImportDataGeoInfoCSVVendedor(entityManager, gerente);
        this.importDataGeoInfoFormaPagamento = new ImportDataGeoInfoCSVFormaPagamento(entityManager, gerente);
        this.importDataGeoInfoProduto = new ImportDataGeoInfoCSVProduto(entityManager, gerente);
    }
    
    @Override
    public void setListaGeoInfoLogNode(List<GeoInfoLogNode> listaGeoInfoLogNode) {
        super.setListaGeoInfoLogNode(listaGeoInfoLogNode);
        this.importDataGeoInfoCliente.setListaGeoInfoLogNode(listaGeoInfoLogNode);
        this.importDataGeoInfoEstabelecimento.setListaGeoInfoLogNode(listaGeoInfoLogNode);
        this.importDataGeoInfoVendedor.setListaGeoInfoLogNode(listaGeoInfoLogNode);
        this.importDataGeoInfoFormaPagamento.setListaGeoInfoLogNode(listaGeoInfoLogNode);
        this.importDataGeoInfoProduto.setListaGeoInfoLogNode(listaGeoInfoLogNode);
    }
    
    private void setLocalizacaoVenda(){
        if(venda != null){
            Localizacao localizacaoE = null;
            Localizacao localizacaoC = null;
            
            if(venda.getVendaPK().getEstabelecimento() != null)
                localizacaoE = new LocalizacaoRepository(this.getEntityManager()).getLocalizacaoRecenteAnteriorA(venda.getVendaPK().getEstabelecimento().getCdPessoa(), venda.getDtVenda());
            
            if(venda.getCliente() != null)
                localizacaoC = new LocalizacaoRepository(this.getEntityManager()).getLocalizacaoRecenteAnteriorA(venda.getCliente().getCdPessoa(), venda.getDtVenda());
            
            if((localizacaoE != null)&&(localizacaoE.getLocalizacaoPK() != null)){
                LocalizacaoOrigemVendaPK localizacaoOrigemVendaPK = new LocalizacaoOrigemVendaPK();
                localizacaoOrigemVendaPK.setVenda(venda);

                LocalizacaoOrigemVendaRepository localizacaoOrigemVendaRepository = new LocalizacaoOrigemVendaRepository(this.getEntityManager());
                if (localizacaoOrigemVendaRepository.find(localizacaoOrigemVendaPK) == null){

                    LocalizacaoOrigemVenda localizacaoOrigemVenda = new LocalizacaoOrigemVenda();
                    localizacaoOrigemVenda.setLocalizacaoOrigemVendaPK(localizacaoOrigemVendaPK);
                    localizacaoOrigemVenda.setCidade(localizacaoE.getLocalizacaoPK().getCidade());
                    localizacaoOrigemVenda.setDsBairro(localizacaoE.getDsBairro());
                    localizacaoOrigemVenda.setDsEndereco(localizacaoE.getDsEndereco());
                    localizacaoOrigemVenda.setDsNumero(localizacaoE.getDsNumero());

                    localizacaoOrigemVendaRepository.insert(localizacaoOrigemVenda);
                }
            }
            
            if((localizacaoE != null)||(localizacaoC != null)){
                LocalizacaoDestinoVendaPK localizacaoDestinoVendaPK = new LocalizacaoDestinoVendaPK();
                localizacaoDestinoVendaPK.setVenda(venda);

                LocalizacaoDestinoVendaRepository localizacaoDestinoVendaRepository = new LocalizacaoDestinoVendaRepository(this.getEntityManager());
                if (localizacaoDestinoVendaRepository.find(localizacaoDestinoVendaPK) == null){
                    LocalizacaoDestinoVenda localizacaoDestinoVenda = new LocalizacaoDestinoVenda();
                    localizacaoDestinoVenda.setLocalizacaoDestinoVendaPK(localizacaoDestinoVendaPK);
                    
                    boolean inDefiniu = false;
                    if((localizacaoC != null)&&(localizacaoC.getLocalizacaoPK() != null)){
                        localizacaoDestinoVenda.setCidade(localizacaoC.getLocalizacaoPK().getCidade());
                        localizacaoDestinoVenda.setDsBairro(localizacaoC.getDsBairro());
                        localizacaoDestinoVenda.setDsEndereco(localizacaoC.getDsEndereco());
                        localizacaoDestinoVenda.setDsNumero(localizacaoC.getDsNumero());
                        inDefiniu = true;
                    }else{
                        if((localizacaoE != null)&&(localizacaoE.getLocalizacaoPK() != null)){
                            localizacaoDestinoVenda.setCidade(localizacaoE.getLocalizacaoPK().getCidade());
                            localizacaoDestinoVenda.setDsBairro(localizacaoE.getDsBairro());
                            localizacaoDestinoVenda.setDsEndereco(localizacaoE.getDsEndereco());
                            localizacaoDestinoVenda.setDsNumero(localizacaoE.getDsNumero());
                            inDefiniu = true;
                        }
                    }
                    
                    if(inDefiniu) 
                        localizacaoDestinoVendaRepository.insert(localizacaoDestinoVenda);
                }
            }
        }

    }

    @Override
    public boolean importar(long nrLinha, String dsLinha, boolean inUltima) {
        String[] listaVenda = dsLinha.split(";", -1);
        
        boolean inImportou = false;

        if(listaVenda[0].equalsIgnoreCase("V")){
            if(this.getListaGeoInfoLogNode() != null){
                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importando Linha (" + nrLinha + "): " + dsLinha, nrLinha));
            }
            
            setLocalizacaoVenda();

            venda = null;
            if(listaVenda.length == 7){
                String cdExternoEstabVenda = listaVenda[1];
                EstabelecimentoRepository estabelecimentoRepository = new EstabelecimentoRepository((this.getEntityManager()));
                Estabelecimento estabelecimento = estabelecimentoRepository.find(this.getGerente(), cdExternoEstabVenda);

                if (estabelecimento != null){
                    Long cdVenda = null;
                    try{
                        cdVenda = Long.valueOf(listaVenda[2]);
                    }catch(NumberFormatException nfe){ 
                        if(this.getListaGeoInfoLogNode() != null){
                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                    "Linha (" + nrLinha + "): Problema no código da venda! " + nfe.getMessage(), nrLinha));
                        }
                    }

                    if (cdVenda != null){
                        String cdExternoClienteVenda = listaVenda[3];
                        ClienteRepository clienteRepository = new ClienteRepository((this.getEntityManager()));
                        Cliente cliente = clienteRepository.find(this.getGerente(), cdExternoClienteVenda);

                        if (cliente != null){
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date dtVenda = null;
                            try {
                                dtVenda = sdf.parse(listaVenda[4]);
                            } catch (ParseException pe) { 
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                            "Linha (" + nrLinha + "): Problema na data da venda! " + pe.getMessage(), nrLinha));
                                }
                            }

                            if (dtVenda != null){
                                Double vlDesconto = null;
                                try{
                                    vlDesconto = Double.valueOf(listaVenda[5].replace(",", "."));
                                }catch(NumberFormatException nfe){ 
                                    if(this.getListaGeoInfoLogNode() != null){
                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                "Linha (" + nrLinha + "): Problema no desconto da venda! " + nfe.getMessage(), nrLinha));
                                    }
                                }

                                if (vlDesconto != null){
                                    Short idStatus = null;
                                    try{
                                        idStatus = Short.parseShort(listaVenda[6]);
                                    }catch(NumberFormatException nfe){ 
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                    "Linha (" + nrLinha + "): Problema no status da venda! " + nfe.getMessage(), nrLinha));
                                        }
                                    }  

                                    if (idStatus != null){
                                        boolean inInsert = false;
                                        VendaRepository vendaRepository = new VendaRepository(this.getEntityManager());
                                        VendaPK vendaPK = new VendaPK();
                                        vendaPK.setEstabelecimento(estabelecimento);
                                        vendaPK.setCdVenda(cdVenda);

                                        venda = vendaRepository.find(vendaPK);
                                        if(venda == null){
                                            venda = new Venda();
                                            venda.setVendaPK(vendaPK);
                                            inInsert = true;
                                        }

                                        venda.setCliente(cliente);
                                        venda.setDtVenda(dtVenda);
                                        venda.setVlDesconto(vlDesconto);
                                        venda.setIdStatus(idStatus);

                                        if(inInsert)
                                            vendaRepository.insert(venda);
                                        else
                                            vendaRepository.edit(venda);
                                        inImportou = true;
                                    }else{
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                    "Linha (" + nrLinha + "): O status da Venda (campo 7 obrigatório) está em branco ou nulo!", nrLinha));
                                        } 
                                    }
                                }else{
                                    if(this.getListaGeoInfoLogNode() != null){
                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                "Linha (" + nrLinha + "): O valor do desconto da Venda (campo 6 obrigatório) está em branco ou nulo!", nrLinha));
                                    } 
                                }
                            }else{
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                            "Linha (" + nrLinha + "): A data da Venda (campo 5 obrigatório) está em branco ou nulo!", nrLinha));
                                } 
                            }
                        }else{
                            if(this.getListaGeoInfoLogNode() != null){
                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                        "Linha (" + nrLinha + "): O código do Cliente da Venda (campo 4 obrigatório, valor = " + 
                                                cdExternoClienteVenda + ") está em branco ou nulo ou não cadstrado!", nrLinha));
                            } 
                        }
                    }else{
                        if(this.getListaGeoInfoLogNode() != null){
                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                    "Linha (" + nrLinha + "): O código da Venda (campo 3 obrigatório) está em branco ou nulo!", nrLinha));
                        }
                    }
                }else{
                    if(this.getListaGeoInfoLogNode() != null){
                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                "Linha (" + nrLinha + "): O código do Estabelecimento da Venda (campo 2 obrigatório, valor = " +
                                        cdExternoEstabVenda + ") está em branco ou nulo ou não cadastrado!", nrLinha));
                    }
                }
            }else{
                if(this.getListaGeoInfoLogNode() != null){
                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                            "Linha (" + nrLinha + "): Número de campos no registro de Venda diferente do esperado!", nrLinha));
                }
            }
        }else{
            if(listaVenda[0].equalsIgnoreCase("F")){
                if(this.getListaGeoInfoLogNode() != null){
                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importando Linha (" + nrLinha + "): " + dsLinha, nrLinha));
                }

                if(venda != null){
                    if(listaVenda.length == 4){
                        Long nrSequencia = null;
                        try{
                            nrSequencia = Long.parseLong(listaVenda[1]);
                        }catch(NumberFormatException nfe){ 
                            if(this.getListaGeoInfoLogNode() != null){
                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                        "Linha (" + nrLinha + "): Problema na sequencia da forma de pagamento da venda! " + nfe.getMessage(), nrLinha));
                            }
                        } 

                        if(nrSequencia != null){
                            String cdFormaPagamento = listaVenda[2];

                            FormaPagamentoPK formaPagamentoPK = new FormaPagamentoPK();
                            formaPagamentoPK.setCdFormaPagamento(cdFormaPagamento);
                            formaPagamentoPK.setGerente(this.getGerente());

                            FormaPagamentoRepository formaPagamentoRepository = new FormaPagamentoRepository(this.getEntityManager());
                            FormaPagamento formaPagamento = formaPagamentoRepository.find(formaPagamentoPK);

                            if(formaPagamento != null){

                                Double vlFormaPagamento = null;
                                try{
                                    vlFormaPagamento = Double.valueOf(listaVenda[3].replace(",", "."));
                                }catch(NumberFormatException nfe){ 
                                    if(this.getListaGeoInfoLogNode() != null){
                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                "Linha (" + nrLinha + "): Problema no valor da forma de pagamento da venda! " + nfe.getMessage(), nrLinha));
                                    }
                                }

                                if (vlFormaPagamento != null){
                                    boolean inInsert = false;
                                    FormaPagamentoVendaRepository formaPagamentoVendaRepository = new FormaPagamentoVendaRepository((this.getEntityManager()));
                                    FormaPagamentoVendaPK formaPagamentoVendaPK = new FormaPagamentoVendaPK();
                                    formaPagamentoVendaPK.setCdFormaPagamentoVenda(nrSequencia);
                                    formaPagamentoVendaPK.setVenda(venda);

                                    FormaPagamentoVenda formaPagamentoVenda = formaPagamentoVendaRepository.find(formaPagamentoVendaPK);
                                    if(formaPagamentoVenda == null){
                                        formaPagamentoVenda = new FormaPagamentoVenda();
                                        formaPagamentoVenda.setFormaPagamentoVendaPK(formaPagamentoVendaPK);
                                        inInsert = true;
                                    }
                                    formaPagamentoVenda.setFormaPagamento(formaPagamento);
                                    formaPagamentoVenda.setVlFormaPagamento(vlFormaPagamento);

                                    if(inInsert)
                                        formaPagamentoVendaRepository.insert(formaPagamentoVenda);
                                    else
                                        formaPagamentoVendaRepository.edit(formaPagamentoVenda);
                                    inImportou = true;
                                }else{
                                    if(this.getListaGeoInfoLogNode() != null){
                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                "Linha (" + nrLinha + "): O valor da forma de pagamento da venda (campo 4 obrigatório) está em branco ou nulo!", nrLinha));
                                    } 
                                }
                            }else{
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                            "Linha (" + nrLinha + "): O código da forma de pagamento da venda (campo 3 obrigatório) está em branco ou nulo ou não cadastrado!", nrLinha));
                                } 
                            }
                        }else{
                            if(this.getListaGeoInfoLogNode() != null){
                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                        "Linha (" + nrLinha + "): A sequencia da forma de pagamento da venda (campo 2 obrigatório) está em branco ou nulo!", nrLinha));
                            }
                        }

                    }else{
                        if(this.getListaGeoInfoLogNode() != null){
                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                    "Linha (" + nrLinha + "): Número de campos no registro de Forma de Pagamento da Venda diferente do esperado!", nrLinha));
                        }
                    }
                }else{
                    if(this.getListaGeoInfoLogNode() != null){
                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                "Linha (" + nrLinha + "): Falta o registro de Venda para o registro Forma de Pagamento da Venda!", nrLinha));
                    }
                }
            }else{
                if(listaVenda[0].equalsIgnoreCase("R")){
                    if(this.getListaGeoInfoLogNode() != null){
                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importando Linha (" + nrLinha + "): " + dsLinha, nrLinha));
                    }

                    if(venda != null){
                        if(listaVenda.length == 4){
                            Long nrSequencia = null;
                            try{
                                nrSequencia = Long.parseLong(listaVenda[1]);
                            }catch(NumberFormatException nfe){ 
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                            "Linha (" + nrLinha + "): Problema na sequencia do vendedor da venda! " + nfe.getMessage(), nrLinha));
                                }
                            } 

                            if(nrSequencia != null){
                                String cdExternoVendedorVenda = listaVenda[2];
                                VendedorRepository vendedorRepository = new VendedorRepository(this.getEntityManager());
                                Vendedor vendedor = vendedorRepository.find(this.getGerente(), cdExternoVendedorVenda);

                                if(vendedor != null){

                                    Double vlComissaoVendedor = null;
                                    try{
                                        vlComissaoVendedor = Double.valueOf(listaVenda[3].replace(",", "."));
                                    }catch(NumberFormatException nfe){ 
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                    "Linha (" + nrLinha + "): Problema no valor da comissão do vendedor da venda! " + nfe.getMessage(), nrLinha));
                                        }
                                    }

                                    if (vlComissaoVendedor != null){
                                        boolean inInsert = false;
                                        VendedorVendaRepository vendedorVendaRepository = new VendedorVendaRepository(this.getEntityManager());
                                        VendedorVendaPK vendedorVendaPK = new VendedorVendaPK();
                                        vendedorVendaPK.setCdVendedorVenda(nrSequencia);
                                        vendedorVendaPK.setVenda(venda);

                                        VendedorVenda vendedorVenda = vendedorVendaRepository.find(vendedorVendaPK);
                                        if(vendedorVenda == null){
                                            vendedorVenda = new VendedorVenda();
                                            vendedorVenda.setVendedorVendaPK(vendedorVendaPK);
                                            inInsert = true;
                                        }
                                        vendedorVenda.setVendedor(vendedor);
                                        vendedorVenda.setVlComissao(vlComissaoVendedor);

                                        if(inInsert)
                                            vendedorVendaRepository.insert(vendedorVenda);
                                        else
                                            vendedorVendaRepository.edit(vendedorVenda);
                                        inImportou = true;
                                    }else{
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                    "Linha (" + nrLinha + "): O valor da comissão do vendedor da venda (campo 4 obrigatório) está em branco ou nulo!", nrLinha));
                                        } 
                                    }
                                }else{
                                    if(this.getListaGeoInfoLogNode() != null){
                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                "Linha (" + nrLinha + "): O código do vendedor da venda (campo 3 obrigatório) está em branco ou nulo ou não cadastrado!", nrLinha));
                                    } 
                                }
                            }else{
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                            "Linha (" + nrLinha + "): A sequencia da forma de pagamento da venda (campo 2 obrigatório) está em branco ou nulo!", nrLinha));
                                }
                            }
                        }else{
                            if(this.getListaGeoInfoLogNode() != null){
                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                        "Linha (" + nrLinha + "): Número de campos no registro de Vendedor da Venda diferente do esperado!", nrLinha));
                            }
                        }
                    }else{
                        if(this.getListaGeoInfoLogNode() != null){
                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                    "Linha (" + nrLinha + "): Falta o registro de Venda para o registro de Vendedor da Venda!", nrLinha));
                        }
                    }
                }else{
                    if(listaVenda[0].equalsIgnoreCase("I")){
                        if(this.getListaGeoInfoLogNode() != null){
                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importando Linha (" + nrLinha + "): " + dsLinha, nrLinha));
                        }

                        if(venda != null){
                            if(listaVenda.length == 5){
                                Long nrSequencia = null;
                                try{
                                    nrSequencia = Long.parseLong(listaVenda[1]);
                                }catch(NumberFormatException nfe){ 
                                    if(this.getListaGeoInfoLogNode() != null){
                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                "Linha (" + nrLinha + "): Problema na sequencia do item da venda! " + nfe.getMessage(), nrLinha));
                                    }
                                } 

                                if(nrSequencia != null){
                                    String cdProduto = listaVenda[2];
                                    ProdutoPK produtoPK = new ProdutoPK();
                                    produtoPK.setCdProduto(cdProduto);
                                    produtoPK.setGerente(this.getGerente());

                                    ProdutoRepository produtoRepository = new ProdutoRepository(this.getEntityManager());
                                    Produto produto = produtoRepository.find(produtoPK);

                                    if(produto != null){

                                        Double qtProduto = null;
                                        try{
                                            qtProduto = Double.valueOf(listaVenda[3].replace(",", "."));
                                        }catch(NumberFormatException nfe){ 
                                            if(this.getListaGeoInfoLogNode() != null){
                                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                        "Linha (" + nrLinha + "): Problema na quantidade do item da venda! " + nfe.getMessage(), nrLinha));
                                            }
                                        }

                                        if (qtProduto != null){

                                            Double vlProduto = null;
                                            try{
                                                vlProduto = Double.valueOf(listaVenda[4].replace(",", "."));
                                            }catch(NumberFormatException nfe){ 
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                            "Linha (" + nrLinha + "): Problema no valor unitário do item da venda! " + nfe.getMessage(), nrLinha));
                                                }
                                            }

                                            if (vlProduto != null){
                                                boolean inInsert = false;
                                                ItemVendaRepository itemVendaRepository = new ItemVendaRepository(this.getEntityManager());
                                                ItemVendaPK itemVendaPK = new ItemVendaPK();
                                                itemVendaPK.setCdItemVenda(nrSequencia);
                                                itemVendaPK.setVenda(venda);

                                                ItemVenda itemVenda = itemVendaRepository.find(itemVendaPK);
                                                if(itemVenda == null){
                                                    itemVenda = new ItemVenda();
                                                    itemVenda.setItemVendaPK(itemVendaPK);
                                                    inInsert = true;
                                                }
                                                itemVenda.setProduto(produto);
                                                itemVenda.setQtProduto(qtProduto);
                                                itemVenda.setVlProduto(vlProduto);

                                                if(inInsert)
                                                    itemVendaRepository.insert(itemVenda);
                                                else
                                                    itemVendaRepository.edit(itemVenda);
                                                inImportou = true;
                                            }else{
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                            "Linha (" + nrLinha + "): O valor unitário do item da venda (campo 5 obrigatório) está em branco ou nulo!", nrLinha));
                                                }  
                                            }
                                        }else{
                                            if(this.getListaGeoInfoLogNode() != null){
                                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                        "Linha (" + nrLinha + "): A quantidade do item da venda (campo 4 obrigatório) está em branco ou nulo!", nrLinha));
                                            } 
                                        }
                                    }else{
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                    "Linha (" + nrLinha + "): O código do produto do item da venda (campo 3 obrigatório) está em branco ou nulo ou não cadastrado!", nrLinha));
                                        } 
                                    }
                                }else{
                                    if(this.getListaGeoInfoLogNode() != null){
                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                "Linha (" + nrLinha + "): A sequencia da forma de pagamento da venda (campo 2 obrigatório) está em branco ou nulo!", nrLinha));
                                    }
                                }
                            }else{
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                            "Linha (" + nrLinha + "): Número de campos no registro de Item da Venda diferente do esperado!", nrLinha));
                                }  
                            }
                        }else{
                            if(this.getListaGeoInfoLogNode() != null){
                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                        "Falta o registro de Venda para o registro de Item da Venda!", nrLinha));
                            }
                        }
                    }else{                        
                        if(listaVenda[0].equalsIgnoreCase("DV")){
                            if(this.getListaGeoInfoLogNode() != null){
                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importando Linha (" + nrLinha + "): " + dsLinha, nrLinha));
                            }

                            if(venda != null){
                                if(listaVenda.length == 10){
                                    Pais pais = null;
                                    Estado estado = null;
                                    Cidade cidade = null;

                                    if((listaVenda[4] != null)&&(!listaVenda[4].equalsIgnoreCase(""))){
                                        PaisRepository paisRepository = new PaisRepository(this.getEntityManager());
                                        int idPais = 2;
                                        try{
                                            idPais = Integer.parseInt(listaVenda[1]);
                                        }catch(NumberFormatException nfe){ 
                                            if(this.getListaGeoInfoLogNode() != null){
                                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                        "Linha (" + nrLinha + "): Problema no tipo de localização de país ("+listaVenda[1]+")! " + nfe.getMessage(), nrLinha));
                                            }
                                        }

                                        switch(idPais){
                                            case 0:
                                                Long cdBACENPais = (long) 0;
                                                try{
                                                    cdBACENPais = Long.valueOf(listaVenda[4]);
                                                }catch(NumberFormatException nfe){ 
                                                    if(this.getListaGeoInfoLogNode() != null){
                                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                                "Linha (" + nrLinha + "): Código BACEN de país (campo 6 com valor "+listaVenda[5]+") inválido! " + nfe.getMessage(), nrLinha));
                                                    }
                                                }

                                                if(cdBACENPais > 0){

                                                    pais = paisRepository.findBACEN(cdBACENPais);
                                                }
                                                break;
                                            case 1:
                                                String cdGMIPais = listaVenda[4];
                                                pais = paisRepository.findGMI(cdGMIPais);
                                                break;
                                            case 2:
                                                String dsPais = listaVenda[4];
                                                pais = paisRepository.find(dsPais);
                                                break;
                                            default:
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                            "Linha (" + nrLinha + "): Valor (" + String.valueOf(idPais) + ") inválido para o tipo de localização de país!", nrLinha));
                                                }
                                                break;
                                        }
                                        if(pais != null){
                                            if(this.getListaGeoInfoLogNode() != null){
                                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, 
                                                        "Linha (" + nrLinha + "): País localizado: " + pais.getDsPais(), nrLinha));
                                            }
                                        }else{
                                            if(this.getListaGeoInfoLogNode() != null){
                                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                        "Linha (" + nrLinha + "): País não localizado! " + venda.toString(), nrLinha));
                                            }
                                        }
                                    }

                                    if((listaVenda[5] != null)&&(!listaVenda[5].equalsIgnoreCase(""))){
                                        EstadoRepository estadoRepository = new EstadoRepository(this.getEntityManager());
                                        int idEstado = 2;
                                        try{
                                            idEstado = Integer.parseInt(listaVenda[2]);
                                        }catch(NumberFormatException nfe){ 
                                            if(this.getListaGeoInfoLogNode() != null){
                                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                        "Linha (" + nrLinha + "): Problema no tipo de localização de estado ("+listaVenda[2]+")! " + nfe.getMessage(), nrLinha));
                                            }
                                        }

                                        switch(idEstado){
                                            case 0:
                                                Long cdIBGEEstado = (long) 0;
                                                try{
                                                    cdIBGEEstado = Long.valueOf(listaVenda[5]);
                                                }catch(NumberFormatException nfe){ 
                                                    if(this.getListaGeoInfoLogNode() != null){
                                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                                "Linha (" + nrLinha + "): Código IBGE de estado (campo 7 com valor "+listaVenda[5]+") inválido! " + nfe.getMessage(), nrLinha));
                                                    }
                                                }

                                                if(cdIBGEEstado > 0){
                                                    estado = estadoRepository.findIBGE(cdIBGEEstado);
                                                }
                                                break;
                                            case 1:
                                                if(pais != null){
                                                    String cdUFEstado = listaVenda[5];
                                                    estado = estadoRepository.findUF(pais, cdUFEstado);
                                                }else{
                                                    if(this.getListaGeoInfoLogNode() != null){
                                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                                "Linha (" + nrLinha + "): Impossível localizar estado pela UF sem ter localizado país!", nrLinha));
                                                    }
                                                }
                                                break;
                                            case 2:
                                                if(pais != null){
                                                    String dsEstado = listaVenda[5];
                                                    estado = estadoRepository.find(pais, dsEstado);
                                                }else{
                                                    if(this.getListaGeoInfoLogNode() != null){
                                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                                "Linha (" + nrLinha + "): Impossível localizar estado pela descrição sem ter localizado país!", nrLinha));
                                                    }
                                                }
                                                break;
                                            default:
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                            "Linha (" + nrLinha + "): Valor (" + String.valueOf(idEstado) + ") inválido para o tipo de localização de estado!", nrLinha));
                                                }
                                                break;
                                        }
                                        if(estado != null){
                                            if(this.getListaGeoInfoLogNode() != null){
                                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, 
                                                        "Linha (" + nrLinha + "): Estado localizado: " + estado.getDsEstado()));
                                            }
                                        }else{
                                            if(this.getListaGeoInfoLogNode() != null){
                                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                        "Linha (" + nrLinha + "): Estado não localizado! " + venda.toString(), nrLinha));
                                            }
                                        }
                                    }

                                    if((listaVenda[6] != null)&&(!listaVenda[6].equalsIgnoreCase(""))){
                                        CidadeRepository cidadeRepository = new CidadeRepository(this.getEntityManager());
                                        int idCidade = 1;
                                        try{
                                            idCidade = Integer.parseInt(listaVenda[3]);
                                        }catch(NumberFormatException nfe){ 
                                            if(this.getListaGeoInfoLogNode() != null){
                                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                        "Linha (" + nrLinha + "): Problema no tipo de localização de cidade ("+listaVenda[3]+")! " + nfe.getMessage(), nrLinha));
                                            }
                                        }

                                        switch(idCidade){
                                            case 0:
                                                Long cdIBGECidade = (long) 0;
                                                try{
                                                    cdIBGECidade = Long.valueOf(listaVenda[6]);
                                                }catch(NumberFormatException nfe){
                                                    if(this.getListaGeoInfoLogNode() != null){
                                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                                "Linha (" + nrLinha + "): Código IBGE de cidade (campo 8 com valor "+listaVenda[6]+") inválido! " + nfe.getMessage(), nrLinha));
                                                    }
                                                }

                                                if(cdIBGECidade > 0){
                                                    cidade = cidadeRepository.findIBGE(cdIBGECidade);
                                                }
                                                break;
                                            case 1:
                                                if(estado != null){
                                                    String dsCidade = listaVenda[6];
                                                    cidade = cidadeRepository.find(estado, dsCidade);
                                                }else{
                                                    if(this.getListaGeoInfoLogNode() != null){
                                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                                "Linha (" + nrLinha + "): Impossível localizar cidade pela descrição sem ter localizado estado!", nrLinha));
                                                    }
                                                }
                                                break;
                                            default:
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                            "Linha (" + nrLinha + "): Valor (" + String.valueOf(idCidade) + ") inválido para o tipo de localização de cidade!", nrLinha));
                                                }
                                                break;
                                        }
                                        if(cidade != null){
                                            if(this.getListaGeoInfoLogNode() != null){
                                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, 
                                                        "Linha (" + nrLinha + "): Cidade localizada: " + cidade.getDsCidade()));
                                            }
                                        }else{
                                            if(this.getListaGeoInfoLogNode() != null){
                                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                        "Linha (" + nrLinha + "): Cidade não localizada! " + venda.toString(), nrLinha));
                                            }
                                        }
                                    }

                                    if(cidade != null){
                                        boolean inInsert = false;
                                        LocalizacaoDestinoVendaRepository localizacaoDestinoVendaRepository = new LocalizacaoDestinoVendaRepository(this.getEntityManager());
                                        LocalizacaoDestinoVendaPK localizacaoDestinoVendaPK = new LocalizacaoDestinoVendaPK();
                                        localizacaoDestinoVendaPK.setVenda(venda);

                                        LocalizacaoDestinoVenda localizacaoDestinoVenda = localizacaoDestinoVendaRepository.find(localizacaoDestinoVendaPK);
                                        if(localizacaoDestinoVenda == null){
                                            localizacaoDestinoVenda = new LocalizacaoDestinoVenda();
                                            localizacaoDestinoVenda.setLocalizacaoDestinoVendaPK(localizacaoDestinoVendaPK);
                                            inInsert = true;
                                        }
                                            
                                        localizacaoDestinoVenda.setCidade(cidade);
                                        localizacaoDestinoVenda.setDsBairro(listaVenda[7]);
                                        localizacaoDestinoVenda.setDsEndereco(listaVenda[8]);
                                        localizacaoDestinoVenda.setDsNumero(listaVenda[9]);

                                        if(inInsert)
                                            localizacaoDestinoVendaRepository.insert(localizacaoDestinoVenda);
                                        else
                                            localizacaoDestinoVendaRepository.edit(localizacaoDestinoVenda);
                                        inImportou = true;
                               
                                    }else{
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                    "Linha (" + nrLinha + "): Cidade (" + listaVenda[6] + ") de " + venda.toString() + " não encontada!", nrLinha));
                                        }
                                    }
                                }else{
                                    if(this.getListaGeoInfoLogNode() != null){
                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                "Linha (" + nrLinha + "): Número de campos no registro de localização de destino da venda (DV) diferente do esperado!", nrLinha));
                                    }
                                }
                            }else{
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                            "Falta o registro de Venda para o registro de Localização de Destino da Venda!", nrLinha));
                                }
                            }
                        }else{
                            if(listaVenda[0].equalsIgnoreCase("OV")){
                                if(this.getListaGeoInfoLogNode() != null){
                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importando Linha (" + nrLinha + "): " + dsLinha, nrLinha));
                                }

                                if(venda != null){
                                    if(listaVenda.length == 10){
                                        Pais pais = null;
                                        Estado estado = null;
                                        Cidade cidade = null;

                                        if((listaVenda[4] != null)&&(!listaVenda[4].equalsIgnoreCase(""))){
                                            PaisRepository paisRepository = new PaisRepository(this.getEntityManager());
                                            int idPais = 2;
                                            try{
                                                idPais = Integer.parseInt(listaVenda[1]);
                                            }catch(NumberFormatException nfe){ 
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                            "Linha (" + nrLinha + "): Problema no tipo de localização de país ("+listaVenda[1]+")! " + nfe.getMessage(), nrLinha));
                                                }
                                            }

                                            switch(idPais){
                                                case 0:
                                                    Long cdBACENPais = (long) 0;
                                                    try{
                                                        cdBACENPais = Long.valueOf(listaVenda[4]);
                                                    }catch(NumberFormatException nfe){ 
                                                        if(this.getListaGeoInfoLogNode() != null){
                                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                                    "Linha (" + nrLinha + "): Código BACEN de país (campo 6 com valor "+listaVenda[5]+") inválido! " + nfe.getMessage(), nrLinha));
                                                        }
                                                    }

                                                    if(cdBACENPais > 0){

                                                        pais = paisRepository.findBACEN(cdBACENPais);
                                                    }
                                                    break;
                                                case 1:
                                                    String cdGMIPais = listaVenda[4];
                                                    pais = paisRepository.findGMI(cdGMIPais);
                                                    break;
                                                case 2:
                                                    String dsPais = listaVenda[4];
                                                    pais = paisRepository.find(dsPais);
                                                    break;
                                                default:
                                                    if(this.getListaGeoInfoLogNode() != null){
                                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                                "Linha (" + nrLinha + "): Valor (" + String.valueOf(idPais) + ") inválido para o tipo de localização de país!", nrLinha));
                                                    }
                                                    break;
                                            }
                                            if(pais != null){
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, 
                                                            "Linha (" + nrLinha + "): País localizado: " + pais.getDsPais(), nrLinha));
                                                }
                                            }else{
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                            "Linha (" + nrLinha + "): País não localizado! " + venda.toString(), nrLinha));
                                                }
                                            }
                                        }

                                        if((listaVenda[5] != null)&&(!listaVenda[5].equalsIgnoreCase(""))){
                                            EstadoRepository estadoRepository = new EstadoRepository(this.getEntityManager());
                                            int idEstado = 2;
                                            try{
                                                idEstado = Integer.parseInt(listaVenda[2]);
                                            }catch(NumberFormatException nfe){ 
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                            "Linha (" + nrLinha + "): Problema no tipo de localização de estado ("+listaVenda[2]+")! " + nfe.getMessage(), nrLinha));
                                                }
                                            }

                                            switch(idEstado){
                                                case 0:
                                                    Long cdIBGEEstado = (long) 0;
                                                    try{
                                                        cdIBGEEstado = Long.valueOf(listaVenda[5]);
                                                    }catch(NumberFormatException nfe){ 
                                                        if(this.getListaGeoInfoLogNode() != null){
                                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                                    "Linha (" + nrLinha + "): Código IBGE de estado (campo 7 com valor "+listaVenda[5]+") inválido! " + nfe.getMessage(), nrLinha));
                                                        }
                                                    }

                                                    if(cdIBGEEstado > 0){
                                                        estado = estadoRepository.findIBGE(cdIBGEEstado);
                                                    }
                                                    break;
                                                case 1:
                                                    if(pais != null){
                                                        String cdUFEstado = listaVenda[5];
                                                        estado = estadoRepository.findUF(pais, cdUFEstado);
                                                    }else{
                                                        if(this.getListaGeoInfoLogNode() != null){
                                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                                    "Linha (" + nrLinha + "): Impossível localizar estado pela UF sem ter localizado país!", nrLinha));
                                                        }
                                                    }
                                                    break;
                                                case 2:
                                                    if(pais != null){
                                                        String dsEstado = listaVenda[5];
                                                        estado = estadoRepository.find(pais, dsEstado);
                                                    }else{
                                                        if(this.getListaGeoInfoLogNode() != null){
                                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                                    "Linha (" + nrLinha + "): Impossível localizar estado pela descrição sem ter localizado país!", nrLinha));
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    if(this.getListaGeoInfoLogNode() != null){
                                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                                "Linha (" + nrLinha + "): Valor (" + String.valueOf(idEstado) + ") inválido para o tipo de localização de estado!", nrLinha));
                                                    }
                                                    break;
                                            }
                                            if(estado != null){
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, 
                                                            "Linha (" + nrLinha + "): Estado localizado: " + estado.getDsEstado()));
                                                }
                                            }else{
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                            "Linha (" + nrLinha + "): Estado não localizado! " + venda.toString(), nrLinha));
                                                }
                                            }
                                        }

                                        if((listaVenda[6] != null)&&(!listaVenda[6].equalsIgnoreCase(""))){
                                            CidadeRepository cidadeRepository = new CidadeRepository(this.getEntityManager());
                                            int idCidade = 1;
                                            try{
                                                idCidade = Integer.parseInt(listaVenda[3]);
                                            }catch(NumberFormatException nfe){ 
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                            "Linha (" + nrLinha + "): Problema no tipo de localização de cidade ("+listaVenda[3]+")! " + nfe.getMessage(), nrLinha));
                                                }
                                            }

                                            switch(idCidade){
                                                case 0:
                                                    Long cdIBGECidade = (long) 0;
                                                    try{
                                                        cdIBGECidade = Long.valueOf(listaVenda[6]);
                                                    }catch(NumberFormatException nfe){
                                                        if(this.getListaGeoInfoLogNode() != null){
                                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                                    "Linha (" + nrLinha + "): Código IBGE de cidade (campo 8 com valor "+listaVenda[6]+") inválido! " + nfe.getMessage(), nrLinha));
                                                        }
                                                    }

                                                    if(cdIBGECidade > 0){
                                                        cidade = cidadeRepository.findIBGE(cdIBGECidade);
                                                    }
                                                    break;
                                                case 1:
                                                    if(estado != null){
                                                        String dsCidade = listaVenda[6];
                                                        cidade = cidadeRepository.find(estado, dsCidade);
                                                    }else{
                                                        if(this.getListaGeoInfoLogNode() != null){
                                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                                    "Linha (" + nrLinha + "): Impossível localizar cidade pela descrição sem ter localizado estado!", nrLinha));
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    if(this.getListaGeoInfoLogNode() != null){
                                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                                "Linha (" + nrLinha + "): Valor (" + String.valueOf(idCidade) + ") inválido para o tipo de localização de cidade!", nrLinha));
                                                    }
                                                    break;
                                            }
                                            if(cidade != null){
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, 
                                                            "Linha (" + nrLinha + "): Cidade localizada: " + cidade.getDsCidade()));
                                                }
                                            }else{
                                                if(this.getListaGeoInfoLogNode() != null){
                                                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_WARN, 
                                                            "Linha (" + nrLinha + "): Cidade não localizada! " + venda.toString(), nrLinha));
                                                }
                                            }
                                        }

                                        if(cidade != null){
                                            boolean inInsert = false;
                                            LocalizacaoOrigemVendaRepository localizacaoOrigemVendaRepository = new LocalizacaoOrigemVendaRepository(this.getEntityManager());
                                            LocalizacaoOrigemVendaPK localizacaoOrigemVendaPK = new LocalizacaoOrigemVendaPK();
                                            localizacaoOrigemVendaPK.setVenda(venda);

                                            LocalizacaoOrigemVenda localizacaoOrigemVenda = localizacaoOrigemVendaRepository.find(localizacaoOrigemVendaPK);
                                            if(localizacaoOrigemVenda == null){
                                                localizacaoOrigemVenda = new LocalizacaoOrigemVenda();
                                                localizacaoOrigemVenda.setLocalizacaoOrigemVendaPK(localizacaoOrigemVendaPK);
                                                inInsert = true;
                                            }

                                            localizacaoOrigemVenda.setCidade(cidade);
                                            localizacaoOrigemVenda.setDsBairro(listaVenda[7]);
                                            localizacaoOrigemVenda.setDsEndereco(listaVenda[8]);
                                            localizacaoOrigemVenda.setDsNumero(listaVenda[9]);

                                            if(inInsert)
                                                localizacaoOrigemVendaRepository.insert(localizacaoOrigemVenda);
                                            else
                                                localizacaoOrigemVendaRepository.edit(localizacaoOrigemVenda);
                                            inImportou = true;

                                        }else{
                                            if(this.getListaGeoInfoLogNode() != null){
                                                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                        "Linha (" + nrLinha + "): Cidade (" + listaVenda[6] + ") de " + venda.toString() + " não encontada!", nrLinha));
                                            }
                                        }
                                    }else{
                                        if(this.getListaGeoInfoLogNode() != null){
                                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                    "Linha (" + nrLinha + "): Número de campos no registro de localização de destino da venda (DV) diferente do esperado!", nrLinha));
                                        }
                                    }
                                }else{
                                    if(this.getListaGeoInfoLogNode() != null){
                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                "Falta o registro de Venda para o registro de Localização de Destino da Venda!", nrLinha));
                                    }
                                }
                            }else{
                                if(listaVenda[0].equalsIgnoreCase("C") || listaVenda[0].equalsIgnoreCase("LC")){
                                    inImportou = this.importDataGeoInfoCliente.importar(nrLinha, dsLinha, inUltima);
                                }else{
                                    if(listaVenda[0].equalsIgnoreCase("D") || listaVenda[0].equalsIgnoreCase("LD")){
                                        inImportou = this.importDataGeoInfoVendedor.importar(nrLinha, dsLinha, inUltima);
                                    }else{
                                        if(listaVenda[0].equalsIgnoreCase("E") || listaVenda[0].equalsIgnoreCase("LE")){
                                            inImportou = this.importDataGeoInfoEstabelecimento.importar(nrLinha, dsLinha, inUltima);
                                        }else{
                                            if(listaVenda[0].equalsIgnoreCase("G")){
                                                inImportou = this.importDataGeoInfoFormaPagamento.importar(nrLinha, dsLinha, inUltima);
                                            }else{
                                                if(listaVenda[0].equalsIgnoreCase("P")){
                                                    inImportou = this.importDataGeoInfoProduto.importar(nrLinha, dsLinha, inUltima);
                                                }else{
                                                    if(this.getListaGeoInfoLogNode() != null){
                                                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                                                "Linha (" + nrLinha + "): Tipo de registro (campo 1) inválido para o arquivo de Venda!", nrLinha));
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
        
        if(inUltima) 
            setLocalizacaoVenda();
        
        return inImportou;
    }
    
}
