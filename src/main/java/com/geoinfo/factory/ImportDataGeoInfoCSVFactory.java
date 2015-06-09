package com.geoinfo.factory;

import com.geoinfo.data.ImportDataGeoInfoCSV;
import com.geoinfo.data.ImportDataGeoInfoCSVCliente;
import com.geoinfo.data.ImportDataGeoInfoCSVEstabelecimento;
import com.geoinfo.data.ImportDataGeoInfoCSVFormaPagamento;
import com.geoinfo.data.ImportDataGeoInfoCSVProduto;
import com.geoinfo.data.ImportDataGeoInfoCSVVenda;
import com.geoinfo.data.ImportDataGeoInfoCSVVendedor;
import com.geoinfo.entity.PessoaMaster;
import com.geoinfo.util.EImportFileType;
import javax.persistence.EntityManager;

public class ImportDataGeoInfoCSVFactory {
    
    public static ImportDataGeoInfoCSV create(EImportFileType idImportacao, EntityManager entityManager, PessoaMaster gerente){
        switch(idImportacao){
            case FILE_CLIENTE_GEOINFO:
                return new ImportDataGeoInfoCSVCliente(entityManager, gerente);
            case FILE_ESTABELECIMENTO_GEOINFO:
                return new ImportDataGeoInfoCSVEstabelecimento(entityManager, gerente);
            case FILE_FORMAPAGAMENTO_GEOINFO:
                return new ImportDataGeoInfoCSVFormaPagamento(entityManager, gerente);
            case FILE_PRODUTO_GEOINFO:
                return new ImportDataGeoInfoCSVProduto(entityManager, gerente);
            case FILE_VENDEDOR_GEOINFO:
                return new ImportDataGeoInfoCSVVendedor(entityManager, gerente);
            case FILE_VENDA_GEOINFO:
                return new ImportDataGeoInfoCSVVenda(entityManager, gerente);
            case FILE_XML_NFE_GEOINFO:
                return null;
        }
        return null;
    }
    
    public static ImportDataGeoInfoCSV createByFileName(String dsFileName, EntityManager entityManager, PessoaMaster gerente){
        EImportFileType idImportacao = null;
        if(dsFileName.toLowerCase().endsWith(".geoinfo")){
            if(dsFileName.toLowerCase().startsWith("cliente"))
                idImportacao = EImportFileType.FILE_CLIENTE_GEOINFO;
            else if(dsFileName.toLowerCase().startsWith("estabelecimento"))
                idImportacao = EImportFileType.FILE_ESTABELECIMENTO_GEOINFO;
            else if(dsFileName.toLowerCase().startsWith("formapagamento"))
                idImportacao = EImportFileType.FILE_FORMAPAGAMENTO_GEOINFO;
            else if(dsFileName.toLowerCase().startsWith("produto"))
                idImportacao = EImportFileType.FILE_PRODUTO_GEOINFO;
            else if(dsFileName.toLowerCase().startsWith("vendedor"))
                idImportacao = EImportFileType.FILE_VENDEDOR_GEOINFO;
            else if(dsFileName.toLowerCase().startsWith("venda"))
                idImportacao = EImportFileType.FILE_VENDA_GEOINFO;
        } 
        
        return ImportDataGeoInfoCSVFactory.create(idImportacao, entityManager, gerente);
    }
}
