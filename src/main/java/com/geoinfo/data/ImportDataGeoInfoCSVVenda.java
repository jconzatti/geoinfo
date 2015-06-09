package com.geoinfo.data;

import com.geoinfo.entity.Cliente;
import com.geoinfo.entity.Estabelecimento;
import com.geoinfo.entity.FormaPagamento;
import com.geoinfo.entity.FormaPagamentoPK;
import com.geoinfo.entity.FormaPagamentoVenda;
import com.geoinfo.entity.FormaPagamentoVendaPK;
import com.geoinfo.entity.ItemVenda;
import com.geoinfo.entity.ItemVendaPK;
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

    @Override
    public boolean importar(Long nrLinha, String dsLinha) {
        String[] listaVenda = dsLinha.split(";", -1);

        if(listaVenda[0].equalsIgnoreCase("V")){
            if(this.getListaGeoInfoLogNode() != null){
                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importando Linha (" + nrLinha + "): " + dsLinha, nrLinha));
            }

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
                                        return true;
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
                                    return true;
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
                                        return true;
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
                                                vlProduto = Double.valueOf(listaVenda[3].replace(",", "."));
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
                                                return true;
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
                        if(listaVenda[0].equalsIgnoreCase("C") || listaVenda[0].equalsIgnoreCase("LC")){
                            return this.importDataGeoInfoCliente.importar(nrLinha, dsLinha);
                        }else{
                            if(listaVenda[0].equalsIgnoreCase("D") || listaVenda[0].equalsIgnoreCase("LD")){
                                return this.importDataGeoInfoVendedor.importar(nrLinha, dsLinha);
                            }else{
                                if(listaVenda[0].equalsIgnoreCase("E") || listaVenda[0].equalsIgnoreCase("LE")){
                                    return this.importDataGeoInfoEstabelecimento.importar(nrLinha, dsLinha);
                                }else{
                                    if(listaVenda[0].equalsIgnoreCase("G")){
                                        return this.importDataGeoInfoFormaPagamento.importar(nrLinha, dsLinha);
                                    }else{
                                        if(listaVenda[0].equalsIgnoreCase("P")){
                                            return this.importDataGeoInfoProduto.importar(nrLinha, dsLinha);
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
        return false;
    }
    
}
