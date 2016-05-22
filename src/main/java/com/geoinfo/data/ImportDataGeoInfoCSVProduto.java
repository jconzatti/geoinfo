package com.geoinfo.data;

import com.geoinfo.entity.PessoaMaster;
import com.geoinfo.entity.Produto;
import com.geoinfo.entity.ProdutoPK;
import com.geoinfo.repository.ProdutoRepository;
import com.geoinfo.util.EGeoInfoLogType;
import com.geoinfo.model.GeoInfoLogNode;
import javax.persistence.EntityManager;

public class ImportDataGeoInfoCSVProduto extends ImportDataGeoInfoCSV{
    private Produto produto;

    public ImportDataGeoInfoCSVProduto(EntityManager entityManager, PessoaMaster gerente) {
        super(entityManager, gerente);
        this.produto = null;
    }

    @Override
    public boolean importar(long nrLinha, String dsLinha, boolean inUltima) {
        String[] listaProduto = dsLinha.split(";", -1);
        if(this.getListaGeoInfoLogNode() != null){
            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_INFO, "Importando Linha (" + nrLinha + "): " + dsLinha, nrLinha));
        }
        
        if(listaProduto[0].equalsIgnoreCase("P")){
            produto = null;
            if(listaProduto.length == 3){
                String cdProduto = listaProduto[1];
                if((cdProduto != null) && (!cdProduto.equalsIgnoreCase(""))){
                    String dsProduto = listaProduto[2];
                    if((dsProduto != null) && (!dsProduto.equalsIgnoreCase(""))){
                        boolean inInsert = false;
                        ProdutoRepository produtoRepository = new ProdutoRepository(this.getEntityManager());
                        ProdutoPK produtoPK = new ProdutoPK();
                        produtoPK.setCdProduto(cdProduto);
                        produtoPK.setGerente(this.getGerente());

                        produto = produtoRepository.find(produtoPK);
                        if(produto == null){
                            produto = new Produto();
                            produto.setProdutoPK(produtoPK);
                            inInsert = true;
                        }

                        produto.setDsProduto(dsProduto);

                        if(inInsert)
                            produtoRepository.insert(produto);
                        else
                            produtoRepository.edit(produto);
                        return true;
                    }else{
                        if(this.getListaGeoInfoLogNode() != null){
                            this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                    "Linha (" + nrLinha + "): A descrição do Produto (campo 3 obrigatório) está em branco ou nulo!", nrLinha));
                        }
                    }
                }else{
                    if(this.getListaGeoInfoLogNode() != null){
                        this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                                "Linha (" + nrLinha + "): O código do Produto (campo 2 obrigatório) está em branco ou nulo!", nrLinha));
                    }
                }
            }else{
                if(this.getListaGeoInfoLogNode() != null){
                    this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                            "Linha (" + nrLinha + "): Número de campos no registro de Produtos está diferente do esperado!", nrLinha));
                }
            }
        }else{
            if(this.getListaGeoInfoLogNode() != null){
                this.getListaGeoInfoLogNode().add(new GeoInfoLogNode(EGeoInfoLogType.LOG_ERROR, 
                        "Linha (" + nrLinha + "): Tipo de registro (campo 1) inválido para o arquivo de Produto!", nrLinha));
            }
        }
        return false;
    }
    
}
