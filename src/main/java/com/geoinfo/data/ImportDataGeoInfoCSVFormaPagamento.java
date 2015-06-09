package com.geoinfo.data;

import com.geoinfo.entity.FormaPagamento;
import com.geoinfo.entity.FormaPagamentoPK;
import com.geoinfo.entity.PessoaMaster;
import com.geoinfo.repository.FormaPagamentoRepository;
import com.geoinfo.util.EGeoInfoLogType;
import com.geoinfo.model.GeoInfoLogNode;
import javax.persistence.EntityManager;

public class ImportDataGeoInfoCSVFormaPagamento extends ImportDataGeoInfoCSV{
    private FormaPagamento formaPagamento;

    public ImportDataGeoInfoCSVFormaPagamento(EntityManager entityManager, PessoaMaster gerente) {
        super(entityManager, gerente);
        this.formaPagamento = null;
    }

    @Override
    public boolean importar(Long nrLinha, String dsLinha) {
        String[] listaFormaPagamento = dsLinha.split(";", -1);
        if(this.getListaGeoInfoLogNode() != null){
            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importando Linha (" + nrLinha + "): " + dsLinha, nrLinha));
        }
        
        if(listaFormaPagamento[0].equalsIgnoreCase("G")){
            formaPagamento = null;
            if(listaFormaPagamento.length == 3){
                String cdFormaPagamento = listaFormaPagamento[1];
                if ((cdFormaPagamento != null) && (!cdFormaPagamento.equalsIgnoreCase(""))){
                    String dsFormaPagamento = listaFormaPagamento[2];
                    if ((dsFormaPagamento != null) && (!dsFormaPagamento.equalsIgnoreCase(""))){
                        boolean inInsert = false;
                        FormaPagamentoRepository formaPagamentoRepository = new FormaPagamentoRepository(this.getEntityManager());
                        FormaPagamentoPK formaPagamentoPK = new FormaPagamentoPK();
                        formaPagamentoPK.setCdFormaPagamento(cdFormaPagamento);
                        formaPagamentoPK.setGerente(this.getGerente());

                        formaPagamento = formaPagamentoRepository.find(formaPagamentoPK);
                        if(formaPagamento == null){
                            formaPagamento = new FormaPagamento();
                            formaPagamento.setFormaPagamentoPK(formaPagamentoPK);
                            inInsert = true;
                        }

                        formaPagamento.setDsFormaPagamento(dsFormaPagamento);

                        if(inInsert)
                            formaPagamentoRepository.insert(formaPagamento);
                        else
                            formaPagamentoRepository.edit(formaPagamento);
                        return true;
                    }else{
                        if(this.getListaGeoInfoLogNode() != null){
                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                    "Linha (" + nrLinha + "): A descrição da Forma de Pagamento (campo 3 obrigatório) está em branco ou nulo!", nrLinha));
                        }
                    }
                }else{
                    if(this.getListaGeoInfoLogNode() != null){
                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                "Linha (" + nrLinha + "): O código da Forma de Pagamento (campo 2 obrigatório) está em branco ou nulo!", nrLinha));
                    }
                }
            }else{
                if(this.getListaGeoInfoLogNode() != null){
                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                            "Linha (" + nrLinha + "): Número de campos no registro de Forma de Pagamento diferente do esperado!", nrLinha));
                }
            }
        }else{
            if(this.getListaGeoInfoLogNode() != null){
                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                        "Linha (" + nrLinha + "): Tipo de registro (campo 1) inválido para o arquivo de Forma de Pagamento!", nrLinha));
            }
        }
        return false;
    }
    
}
