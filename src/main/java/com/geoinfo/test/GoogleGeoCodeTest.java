package com.geoinfo.test;

import com.geoinfo.model.GoogleGeoCodeAddressComponent;
import com.geoinfo.model.GoogleGeoCodeResponse;
import com.geoinfo.model.GoogleGeoCodeResult;
import com.geoinfo.ws.GoogleGeoCodeWS;
import java.io.IOException;

public class GoogleGeoCodeTest {
    
    public static void main(String args[]) throws IOException{
        GoogleGeoCodeWS googleGeoCodeWS = new GoogleGeoCodeWS();
        googleGeoCodeWS.setDsEndereco("Rua 2 de Setembro, 798, Blumenau - SC");
        
        GoogleGeoCodeResponse googleGeoCodeResponse = googleGeoCodeWS.getResponse();
        
        if(googleGeoCodeResponse.getDsResposta().equalsIgnoreCase("OK")){
            for(GoogleGeoCodeResult ggcr : googleGeoCodeResponse.getListaResultado()){
                for(GoogleGeoCodeAddressComponent ggcac : ggcr.getListaComponenteEndereco()){
                    System.out.println(ggcac.getDsNomeLongo());
                    System.out.println(ggcac.getDsNomeCurto());
                    for(String s : ggcac.getListaTipo()){
                        System.out.println("-"+s);
                    }
                }
                System.out.println(ggcr.getGeometry().getLocCentro().getDsLatitude());
            }
        }else{
            System.out.println("Nenhum resultado!");
        }
    }
}
