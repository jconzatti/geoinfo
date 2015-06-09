package com.geoinfo.model;

public class RegiaoGeograficaPoligonalSettings {
    private final String dsMaxResolution;
    private final String dsMinResolution;
    private final String dsGeoJSON;

    public RegiaoGeograficaPoligonalSettings(Class<? extends RegiaoGeograficaPoligonal> classRegiaoGeograficaPoligonal) {
        if(classRegiaoGeograficaPoligonal.getSimpleName().equalsIgnoreCase("Pais")){
            dsMaxResolution = "160000";
            dsMinResolution = "10000";
            dsGeoJSON = "geoinfo.mundo.geojson";
        }else if(classRegiaoGeograficaPoligonal.getSimpleName().equalsIgnoreCase("Estado")){
            dsMaxResolution = "10000";
            dsMinResolution = "1600";
            dsGeoJSON = "geoinfo.brauf.geojson";
        }else if(classRegiaoGeograficaPoligonal.getSimpleName().equalsIgnoreCase("Cidade")){
            dsMaxResolution = "1600";
            dsMinResolution = "70";
            dsGeoJSON = "geoinfo.bract.geojson";
        }else{
            dsMaxResolution = "";
            dsMinResolution = "";
            dsGeoJSON = "";
        }
    }

    public String getDsMaxResolution() {
        return dsMaxResolution;
    }

    public String getDsMinResolution() {
        return dsMinResolution;
    }

    public String getDsGeoJSON() {
        return dsGeoJSON;
    }
    
    
}
