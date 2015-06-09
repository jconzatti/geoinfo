package com.geoinfo.ws;

import com.geoinfo.model.GoogleGeoCodeAddressComponent;
import com.geoinfo.model.GoogleGeoCodeGeometry;
import com.geoinfo.model.GoogleGeoCodeLocation;
import com.geoinfo.model.GoogleGeoCodeResponse;
import com.geoinfo.model.GoogleGeoCodeResult;
import com.geoinfo.model.GoogleGeoCodeViewPort;
import com.geoinfo.util.EGoogleGeoCodeType;
import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class GoogleGeoCodeWS extends SimpleWS<GoogleGeoCodeResponse>{
    private EGoogleGeoCodeType idFormato;
    private String dsEndereco;
    private boolean inSensor;

    public GoogleGeoCodeWS(EGoogleGeoCodeType idFormato, String dsEndereco, boolean inSensor) {
        this.dsBaseURL = "http://maps.google.com/maps/api/geocode/";
        this.idFormato = idFormato;
        this.dsEndereco = dsEndereco;
        this.inSensor = inSensor;
    }

    public GoogleGeoCodeWS() {
        this(EGoogleGeoCodeType.XML, null, false);
    }
    
    @Override
    protected URL getURL() throws MalformedURLException{
        String dsFormatoTratado = "xml";
        if(idFormato != null)
            dsFormatoTratado = idFormato.toString();
        String dsEnderecoTratado = "";
        if(dsEndereco != null)
            dsEnderecoTratado = dsEndereco.replace(" ", "+");
        String dsSensorTratado = "false";
        if(inSensor)
            dsSensorTratado = "true";
        return new URL(dsBaseURL + dsFormatoTratado + "?address=" + dsEnderecoTratado + "&sensor=" + dsSensorTratado);
    }

    public EGoogleGeoCodeType getIdFormato() {
        return idFormato;
    }

    public void setIdFormato(EGoogleGeoCodeType idFormato) {
        this.idFormato = idFormato;
    }

    public String getDsEndereco() {
        return dsEndereco;
    }

    public void setDsEndereco(String dsEndereco) {
        this.dsEndereco = dsEndereco;
    }

    public boolean isInSensor() {
        return inSensor;
    }

    public void setInSensor(boolean inSensor) {
        this.inSensor = inSensor;
    }

    @Override
    public GoogleGeoCodeResponse getResponse() throws MalformedURLException, IOException{
        InputStream iStream = getInputStream();
        
        XStream xStream = new XStream();
        xStream.alias("GeocodeResponse", GoogleGeoCodeResponse.class);
        xStream.alias("result", GoogleGeoCodeResult.class);
        xStream.alias("address_component", GoogleGeoCodeAddressComponent.class);
        xStream.alias("geometry", GoogleGeoCodeGeometry.class);
        xStream.alias("location", GoogleGeoCodeLocation.class);
        xStream.alias("viewport", GoogleGeoCodeViewPort.class);
        xStream.alias("bounds", GoogleGeoCodeViewPort.class);
        xStream.alias("southwest", GoogleGeoCodeLocation.class);
        xStream.alias("northeast", GoogleGeoCodeLocation.class);
        xStream.alias("type", String.class);
        xStream.alias("formatted_address", String.class);
        xStream.alias("long_name", String.class);
        xStream.alias("short_name", String.class);
        xStream.alias("location_type", String.class);
        xStream.alias("lat", String.class);
        xStream.alias("lng", String.class);
        
        xStream.aliasField("status", GoogleGeoCodeResponse.class, "dsResposta");
        xStream.aliasField("result", GoogleGeoCodeResponse.class, "listaResultado");
        xStream.addImplicitCollection(GoogleGeoCodeResponse.class, "listaResultado");
        
        xStream.aliasField("type", GoogleGeoCodeResult.class, "listaTipo");
        xStream.addImplicitCollection(GoogleGeoCodeResult.class, "listaTipo");
        xStream.aliasField("formatted_address", GoogleGeoCodeResult.class, "dsEndereco");
        xStream.aliasField("address_component", GoogleGeoCodeResult.class, "listaComponenteEndereco");
        xStream.addImplicitCollection(GoogleGeoCodeResult.class, "listaComponenteEndereco");
        xStream.aliasField("geometry", GoogleGeoCodeResult.class, "geometry");
        //xStream.omitField(GoogleGeoCodeResult.class, "place_id");
        xStream.aliasField("place_id", GoogleGeoCodeResult.class, "cdRegiao");
        
        xStream.aliasField("long_name", GoogleGeoCodeAddressComponent.class, "dsNomeLongo");
        xStream.aliasField("short_name", GoogleGeoCodeAddressComponent.class, "dsNomeCurto");
        xStream.aliasField("type", GoogleGeoCodeAddressComponent.class, "listaTipo");
        xStream.addImplicitCollection(GoogleGeoCodeAddressComponent.class, "listaTipo");
        
        xStream.aliasField("location", GoogleGeoCodeGeometry.class, "locCentro");
        xStream.aliasField("location_type", GoogleGeoCodeGeometry.class, "dsTipoLocalizacao");
        xStream.aliasField("viewport", GoogleGeoCodeGeometry.class, "viewport");
        xStream.aliasField("bounds", GoogleGeoCodeGeometry.class, "bounds");
        
        xStream.aliasField("southwest", GoogleGeoCodeViewPort.class, "locSudoeste");
        xStream.aliasField("northeast", GoogleGeoCodeViewPort.class, "locNordeste");
        
        xStream.aliasField("lat", GoogleGeoCodeLocation.class, "dsLatitude");
        xStream.aliasField("lng", GoogleGeoCodeLocation.class, "dsLongitude");
        
        
        return (GoogleGeoCodeResponse)xStream.fromXML(iStream);
    }
    
}   
