package com.geoinfo.model;

import com.geoinfo.util.IGroupable;
import com.geoinfo.ws.GoogleGeoCodeWS;
import java.io.IOException;

public abstract class RegiaoGeografica implements IGroupable{
    
    public GoogleGeoCodeResponse getGoogleGeoCodeResponse() throws IOException{
        GoogleGeoCodeWS googleGeoCodeWS = new GoogleGeoCodeWS();
        googleGeoCodeWS.setDsEndereco(toString());
        return googleGeoCodeWS.getResponse();
    }
    
}
