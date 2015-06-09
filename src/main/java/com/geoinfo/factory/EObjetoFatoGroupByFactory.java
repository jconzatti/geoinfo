package com.geoinfo.factory;

import com.geoinfo.util.EObjetoFatoGroupBy;
import com.geoinfo.util.IGroupable;

public class EObjetoFatoGroupByFactory {
    
    public static EObjetoFatoGroupBy create(Class<? extends IGroupable> classIGroupable){
        if(classIGroupable.getSimpleName().equalsIgnoreCase("Cidade")){
            return EObjetoFatoGroupBy.GROUP_BY_CIDADE;
        }else if(classIGroupable.getSimpleName().equalsIgnoreCase("Estado")){
            return EObjetoFatoGroupBy.GROUP_BY_ESTADO;
        }else if(classIGroupable.getSimpleName().equalsIgnoreCase("Pais")){
            return EObjetoFatoGroupBy.GROUP_BY_PAIS;
        }else if(classIGroupable.getSimpleName().equalsIgnoreCase("Cliente")){
            return EObjetoFatoGroupBy.GROUP_BY_CLIENTE;
        }else if(classIGroupable.getSimpleName().equalsIgnoreCase("Vendedor")){
            return EObjetoFatoGroupBy.GROUP_BY_VENDEDOR;
        }else if(classIGroupable.getSimpleName().equalsIgnoreCase("Estabelecimento")){
            return EObjetoFatoGroupBy.GROUP_BY_ESTABELECIMENTO;
        }else if(classIGroupable.getSimpleName().equalsIgnoreCase("Produto")){
            return EObjetoFatoGroupBy.GROUP_BY_PRODUTO;
        }else if(classIGroupable.getSimpleName().equalsIgnoreCase("FormaPagamento")){
            return EObjetoFatoGroupBy.GROUP_BY_FORMAPAGAMENTO;
        }else if(classIGroupable.getSimpleName().equalsIgnoreCase("Venda")){
            return EObjetoFatoGroupBy.GROUP_BY_VENDA;
        }else{
            return null;
        }
    }
    
}
