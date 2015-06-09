package com.geoinfo.ws;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class SimpleWS<T> {
    protected String dsBaseURL;
    
    protected abstract URL getURL() throws MalformedURLException;
    
    protected InputStream getInputStream() throws MalformedURLException, IOException{
        URL url = getURL();
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        return httpURLConnection.getInputStream();
    }
    
    public abstract T getResponse() throws MalformedURLException, IOException;

    public String getDsBaseURL() {
        return dsBaseURL;
    }
    
    
    
}
