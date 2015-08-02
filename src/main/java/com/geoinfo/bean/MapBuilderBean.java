package com.geoinfo.bean;

import com.geoinfo.entity.Cidade;
import com.geoinfo.entity.Estado;
import com.geoinfo.entity.Fato;
import com.geoinfo.entity.Pais;
import com.geoinfo.entity.Pessoa;
import com.geoinfo.exception.LegendaException;
import com.geoinfo.exception.ObjetoFatoBuilderException;
import com.geoinfo.factory.RepositoryFactory;
import com.geoinfo.model.ItemLegenda;
import com.geoinfo.model.Legenda;
import com.geoinfo.model.ListaObjetoFato;
import com.geoinfo.model.ObjetoFato;
import com.geoinfo.model.ObjetoFatoPeriodo;
import com.geoinfo.model.ObjetoFatoProporcao;
import com.geoinfo.model.PeriodoIntervaloComparavel;
import com.geoinfo.model.RegiaoGeograficaPoligonal;
import com.geoinfo.model.RegiaoGeograficaPoligonalSettings;
import com.geoinfo.repository.Repository;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class MapBuilderBean implements Serializable{
    private String dsOutputScriptMap;
    private Long pcProgresso;
    private Long idProgressoPais;
    private Long idProgressoEstado;
    private Long idProgressoCidade;
    private Legenda legendaPais;
    private Legenda legendaEstado;
    private Legenda legendaCidade;
    private List<ObjetoFato<Pais>> listaObjetoFatoPais;
    private List<ObjetoFato<Estado>> listaObjetoFatoEstado;
    private List<ObjetoFato<Cidade>> listaObjetoFatoCidade;
    private String dsTitulo;
    private ScriptMapCreator<Pais> scriptMapBuilderPais;
    private ScriptMapCreator<Estado> scriptMapBuilderEstado;
    private ScriptMapCreator<Cidade> scriptMapBuilderCidade;
    private List<Pais> listaPais;
    private List<Estado> listaEstado;
    private List<Cidade> listaCidade;
    
    public MapBuilderBean(){
        dsOutputScriptMap = getMapaPadrao();
        
        pcProgresso = new Long(0);
        idProgressoPais = new Long(0);
        idProgressoEstado = new Long(0);
        idProgressoCidade = new Long(0);
        legendaPais = null;
        legendaEstado = null;
        legendaCidade = null;
        listaObjetoFatoPais = null;
        listaObjetoFatoEstado = null;
        listaObjetoFatoCidade = null;
        dsTitulo = null;
    }
    
    private String getMapaPadrao(){
        return "var listaRegiao = [];\n" +
               "function Regiao(dsRegiao, dsItemLegenda){\n" +
               "    this.dsRegiao = dsRegiao;\n" +
               "    this.dsItemLegenda = dsItemLegenda;\n" +
               "    this.listaFato = [];\n" +
               "}\n" +
               "function onMoveEnd(evt){\n" +
               "    var zoomLevel = map.getView().getZoom();\n" +
               "    var activeIndex = PF('tviewPrincipal').getActiveIndex();\n" +
               "    switch(zoomLevel){\n" +
               "        case 0:\n" +
               "        case 1:\n" +
               "        case 2:\n" +
               "        case 3:\n" +
               "            PF('dlgLegendaEstado').hide();\n" +
               "            PF('dlgLegendaCidade').hide();\n" +
               "            PF('dlgLegendaPais').show();\n" +
               "            PF('tviewPrincipal').select(0);\n" +
               "            PF('tviewResumo').select(0);\n" +
               "            PF('tviewPrincipal').select(1);\n" +
               "            PF('tviewDetalhe').select(0);\n" +
               "            break;\n" +
               "        case 4:\n" +
               "        case 5:\n" +
               "        case 6:\n" +
               "            PF('dlgLegendaCidade').hide();\n" +
               "            PF('dlgLegendaPais').hide();\n" +
               "            PF('dlgLegendaEstado').show();\n" +
               "            PF('tviewPrincipal').select(0);\n" +
               "            PF('tviewResumo').select(1);\n" +
               "            PF('tviewPrincipal').select(1);\n" +
               "            PF('tviewDetalhe').select(1);\n" +
               "            break;\n" +
               "        default:\n" +
               "            PF('dlgLegendaEstado').hide();\n" +
               "            PF('dlgLegendaPais').hide();\n" +
               "            PF('dlgLegendaCidade').show();\n" +
               "            PF('tviewPrincipal').select(0);\n" +
               "            PF('tviewResumo').select(2);\n" +
               "            PF('tviewPrincipal').select(1);\n" +
               "            PF('tviewDetalhe').select(2);\n" +
               "            break;\n" +
               "    }\n" +
               "    PF('tviewPrincipal').select(activeIndex);\n" +
               "} \n" +
               "var map = new ol.Map({target: 'map', controls: []});\n" +
               "map.addLayer(new ol.layer.Tile({source: new ol.source.OSM()}));\n" +
               "map.addControl(new ol.control.Zoom({zoomInTipLabel: \"Ampliar\", zoomOutTipLabel: \"Diminuir\"}));\n" +
               "map.addControl(new ol.control.ZoomSlider());\n" +
               "map.addControl(new ol.control.Rotate({tipLabel: \"Desfazer Rotação\"}));\n" +
               "map.addControl(new ol.control.Attribution({tipLabel: \"Atribuições\"}));\n" +
               "map.addControl(new ol.control.ScaleLine());\n" +
               "map.addOverlay(new ol.Overlay({element: document.getElementById('popup')}));\n" +
               "map.setView(new ol.View({center: [0, 0], zoom: 2}));\n" +
               "map.on('postrender', function(evt){map.updateSize();});\n" +
               "map.on('singleclick', function(evt) {\n" +
               "    var coordinate = evt.coordinate;\n" +
               "    map.getOverlays().item(0).setPosition(coordinate);\n" +
               "    var feature = map.forEachFeatureAtPixel(evt.pixel, function(feature, layer) {return feature;});\n" +
               "    var dsPopup;\n" +
               "    var dsInfo;\n" +
               "    if(feature){\n" +
               "        dsPopup = \"País\";\n" +
               "        dsInfo = feature.get('CDPAIS');\n" +
               "        if(feature.get('CDESTADO')){\n" + 
               "            dsPopup += \" - Estado\";\n" +
               "            dsInfo += \"-\" + feature.get('CDESTADO');\n" +
               "            if(feature.get('CDCIDADE')){\n" + 
               "                dsPopup += \" - Cidade\";\n" +
               "                dsInfo += \"-\" + feature.get('CDCIDADE');\n" +
               "            }\n" +
               "        }\n" +
               "        if(listaRegiao[dsInfo]){\n" +
               "            dsPopup = '<table><tr><td class=\"ol-popup-text\"><b>' + listaRegiao[dsInfo].dsRegiao + '</b></td></tr>';\n" + 
               "            for(i = 0; i < listaRegiao[dsInfo].listaFato.length; i++){\n" +
               "                dsPopup += '<tr>' + listaRegiao[dsInfo].listaFato[i] + '</tr>';\n" +
               "            }\n" +
               "            dsPopup += '</table>';\n" +
               "            document.getElementById('popup-content').innerHTML = dsPopup;\n" +  
               "        }else{\n" +
               "            document.getElementById('popup-content').innerHTML = '<p class=\"ol-popup-text\"><b>' + dsPopup + ':</b></p><p class=\"ol-popup-text\">' + dsInfo + '</p>';\n" +  
               "        }\n" + 
               "    }else{\n" +
               "        dsPopup = \"Você clicou aqui\";\n" +
               "        dsInfo = ol.coordinate.toStringHDMS(ol.proj.transform(coordinate, 'EPSG:3857', 'EPSG:4326'));\n" +
               "        document.getElementById('popup-content').innerHTML = '<p class=\"ol-popup-text\"><b>' + dsPopup + ':</b></p><p class=\"ol-popup-text\">' + dsInfo + '</p>';\n" +  
               "    }\n" + 
               "    document.getElementById('popup').style.display = 'block';\n" +
               "});\n" +
               "document.getElementById('popup-closer').onclick = function() {\n" +
               "    document.getElementById('popup').style.display = 'none';\n" +
               "    document.getElementById('popup-closer').blur();\n" +
               "    return false;\n" +
               "};";
    }
    
    public void processar(){
        pcProgresso = new Long(0);
        idProgressoPais = new Long(0);
        idProgressoEstado = new Long(0);
        idProgressoCidade = new Long(0);
        
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        
        HttpSession hs = (HttpSession) ec.getSession(false);
        List<Fato> listaFato = (List<Fato>)hs.getAttribute("listaFato");
        PeriodoIntervaloComparavel periodoIntervaloComparavel = (PeriodoIntervaloComparavel)hs.getAttribute("periodoIntervaloComparavel");
        Pessoa pessoaLogada = (Pessoa) hs.getAttribute("pessoaLogada");
        
        HttpServletRequest hsr = (HttpServletRequest) ec.getRequest();
        EntityManager entityManager = (EntityManager)hsr.getAttribute("entityManager");
        
        if((listaFato!=null)&&(listaFato.size() > 0)){
            try{
                idProgressoPais = new Long(1);

                scriptMapBuilderPais = new ScriptMapCreator<Pais>(listaFato, periodoIntervaloComparavel, entityManager, Pais.class, pessoaLogada);
                dsTitulo = scriptMapBuilderPais.getListaObjetoFato().getLista().get(0).getDsTitulo();
                legendaPais = scriptMapBuilderPais.getListaObjetoFato().getLista().get(0).getLegenda();
                listaObjetoFatoPais = scriptMapBuilderPais.getListaObjetoFato().getLista();
                listaPais = scriptMapBuilderPais.getListaRegiaoGeograficaPoligonal();

                idProgressoPais = new Long(2);
                idProgressoEstado = new Long(1);

                scriptMapBuilderEstado = new ScriptMapCreator<Estado>(listaFato, periodoIntervaloComparavel, entityManager, Estado.class, pessoaLogada);
                legendaEstado = scriptMapBuilderEstado.getListaObjetoFato().getLista().get(0).getLegenda();
                listaObjetoFatoEstado = scriptMapBuilderEstado.getListaObjetoFato().getLista();
                listaEstado = scriptMapBuilderEstado.getListaRegiaoGeograficaPoligonal();

                idProgressoEstado = new Long(2);
                idProgressoCidade = new Long(1);

                scriptMapBuilderCidade = new ScriptMapCreator<Cidade>(listaFato, periodoIntervaloComparavel, entityManager, Cidade.class, pessoaLogada);
                legendaCidade = scriptMapBuilderCidade.getListaObjetoFato().getLista().get(0).getLegenda();
                listaObjetoFatoCidade = scriptMapBuilderCidade.getListaObjetoFato().getLista();
                listaCidade = scriptMapBuilderCidade.getListaRegiaoGeograficaPoligonal();

                idProgressoCidade = new Long(2);

                this.dsOutputScriptMap = getMapaPadrao();
                this.dsOutputScriptMap += "inProcessMap = true;";
                this.dsOutputScriptMap += scriptMapBuilderPais.getDsScriptMap();
                this.dsOutputScriptMap += scriptMapBuilderEstado.getDsScriptMap();
                this.dsOutputScriptMap += scriptMapBuilderCidade.getDsScriptMap();
                this.dsOutputScriptMap += "map.on('moveend', onMoveEnd);";
                this.dsOutputScriptMap += "document.getElementById(\"form-map:panel-titulo-map\").style.height = \"30px\";";
                this.dsOutputScriptMap += "document.getElementById(\"map\").style.height = \"calc(100% - 35px)\";";
                
            }catch (ObjetoFatoBuilderException ofbe){
                FacesMessage fm = new FacesMessage(ofbe.getMessage());
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
            }catch (LegendaException le){
                FacesMessage fm = new FacesMessage(le.getMessage());
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, fm);
            }
        }
    }
    
    private Pessoa getPessoaLogada(){
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpSession hs = (HttpSession) ec.getSession(false);
        return (Pessoa) hs.getAttribute("pessoaLogada");
    }

    public String getDsOutputScriptMap() {
        if(getPessoaLogada() == null)
            this.dsOutputScriptMap = getMapaPadrao();
        return dsOutputScriptMap;
    }

    public void setDsOutputScriptMap(String dsOutputScriptMap) {
        this.dsOutputScriptMap = dsOutputScriptMap;
    }

    public Long getPcProgresso() {
        return pcProgresso;
    }

    public void setPcProgresso(Long pcProgresso) {
        this.pcProgresso = pcProgresso;
    }

    public Long getIdProgressoPais() {
        return idProgressoPais;
    }

    public void setIdProgressoPais(Long idProgressoPais) {
        this.idProgressoPais = idProgressoPais;
    }

    public Long getIdProgressoEstado() {
        return idProgressoEstado;
    }

    public void setIdProgressoEstado(Long idProgressoEstado) {
        this.idProgressoEstado = idProgressoEstado;
    }

    public Long getIdProgressoCidade() {
        return idProgressoCidade;
    }

    public void setIdProgressoCidade(Long idProgressoCidade) {
        this.idProgressoCidade = idProgressoCidade;
    }

    public Legenda getLegendaPais() {
        return legendaPais;
    }

    public void setLegendaPais(Legenda legendaPais) {
        this.legendaPais = legendaPais;
    }

    public Legenda getLegendaEstado() {
        return legendaEstado;
    }

    public void setLegendaEstado(Legenda legendaEstado) {
        this.legendaEstado = legendaEstado;
    }

    public Legenda getLegendaCidade() {
        return legendaCidade;
    }

    public void setLegendaCidade(Legenda legendaCidade) {
        this.legendaCidade = legendaCidade;
    }

    public List<ObjetoFato<Pais>> getListaObjetoFatoPais() {
        return listaObjetoFatoPais;
    }

    public void setListaObjetoFatoPais(List<ObjetoFato<Pais>> listaObjetoFatoPais) {
        this.listaObjetoFatoPais = listaObjetoFatoPais;
    }

    public List<ObjetoFato<Estado>> getListaObjetoFatoEstado() {
        return listaObjetoFatoEstado;
    }

    public void setListaObjetoFatoEstado(List<ObjetoFato<Estado>> listaObjetoFatoEstado) {
        this.listaObjetoFatoEstado = listaObjetoFatoEstado;
    }

    public List<ObjetoFato<Cidade>> getListaObjetoFatoCidade() {
        return listaObjetoFatoCidade;
    }

    public void setListaObjetoFatoCidade(List<ObjetoFato<Cidade>> listaObjetoFatoCidade) {
        this.listaObjetoFatoCidade = listaObjetoFatoCidade;
    }

    public String getDsTitulo() {
        if(getPessoaLogada() == null)
            this.dsTitulo = null;
        return dsTitulo;
    }

    public void setDsTitulo(String dsTitulo) {
        this.dsTitulo = dsTitulo;
    }

    public List<Pais> getListaPais() {
        return listaPais;
    }

    public void setListaPais(List<Pais> listaPais) {
        this.listaPais = listaPais;
    }

    public List<Estado> getListaEstado() {
        return listaEstado;
    }

    public void setListaEstado(List<Estado> listaEstado) {
        this.listaEstado = listaEstado;
    }

    public List<Cidade> getListaCidade() {
        return listaCidade;
    }

    public void setListaCidade(List<Cidade> listaCidade) {
        this.listaCidade = listaCidade;
    }
    
    private final class ScriptMapCreator<T extends RegiaoGeograficaPoligonal> {
        private String dsScriptMap;
        private final ListaObjetoFato<T> listaObjetoFato;
        private final String dsClassScript;
        private final RegiaoGeograficaPoligonalSettings rgps;
        private List<T> listaRegiaoGeograficaPoligonal;
        private boolean inConstruiu;

        private void construir() throws LegendaException{
            StringBuffer sbuffer = new StringBuffer();
            
            pcProgresso = new Long(0);

            if((listaObjetoFato != null)&&(listaRegiaoGeograficaPoligonal != null)){
                Long qtTotal = (long) listaObjetoFato.getLista().size() * listaRegiaoGeograficaPoligonal.size();
                Long qtAtual = (long) 0;

                if(listaObjetoFato.getLista().size() > 0){

                    //criar legenda principal
                    //o mapa só aceita uma legenda esta é criada aqui
                    Map<String, String> mapItemLegenda = new HashMap<String, String>();

                    sbuffer.append("var listaEstilo");
                    sbuffer.append(dsClassScript);
                    sbuffer.append(" = {\n");
                    
                    Legenda legenda = listaObjetoFato.getLista().get(0).getLegenda();
                    for(int i = 0; i < legenda.getListaItemLegenda().size(); i++){
                        String dsHexCor = ((ItemLegenda)legenda.getListaItemLegenda().get(i)).getHexCorItemLegenda();
                        int r = ((ItemLegenda)legenda.getListaItemLegenda().get(i)).getCorItemLegenda().getRed();
                        int g = ((ItemLegenda)legenda.getListaItemLegenda().get(i)).getCorItemLegenda().getGreen();
                        int b = ((ItemLegenda)legenda.getListaItemLegenda().get(i)).getCorItemLegenda().getBlue();
                        float a = 0.5f;
                        if(dsHexCor.equalsIgnoreCase("#ffffff")){
                            a = 0.0f;
                        }
                        String dsRGBACor = "rgba(" + String.valueOf(r) + 
                                ", " + String.valueOf(g) + 
                                ", " + String.valueOf(b) + 
                                ", " + String.valueOf(a) + ")";
                        String dsItemLegenda = "legenda" + dsClassScript + String.valueOf(i);
                        mapItemLegenda.put(dsHexCor, dsItemLegenda);

                        sbuffer.append("'");
                        sbuffer.append(dsItemLegenda);
                        sbuffer.append("':[\n");
                        sbuffer.append("    new ol.style.Style({\n");
                        sbuffer.append("        fill: new ol.style.Fill({\n");
                        sbuffer.append("            color: '");
                        sbuffer.append(dsRGBACor);
                        sbuffer.append("'\n");
                        sbuffer.append("        }),\n");
                        sbuffer.append("        stroke: new ol.style.Stroke({\n");
                        sbuffer.append("            color: '#959595',\n");
                        sbuffer.append("            width: 1\n");
                        sbuffer.append("        })\n");
                        sbuffer.append("    })\n");
                        sbuffer.append("]");
                        if(i < (legenda.getListaItemLegenda().size() - 1)){
                            sbuffer.append(",");
                        }
                        sbuffer.append("\n");
                    }
                    sbuffer.append("};\n");

                    //montando o mapa
                    for(int i = 0; i < listaObjetoFato.getLista().size(); i++){
                        ObjetoFato<T> of = listaObjetoFato.getLista().get(i);
                        ObjetoFatoPeriodo<T> objetoFatoPeriodoA = of.getObjetoFatoPeriodoA();
                        ObjetoFatoPeriodo<T> objetoFatoPeriodoB = of.getObjetoFatoPeriodoB();

                        for (T t : listaRegiaoGeograficaPoligonal) {
                            Double vlItemA = objetoFatoPeriodoA.getVlItem(t);
                            Double vlItemB = null;
                            if(objetoFatoPeriodoB != null){
                                vlItemB = objetoFatoPeriodoB.getVlItem(t);
                            }

                            if(vlItemA == null)
                                vlItemA = 0d;
                            if(vlItemB == null)
                                vlItemB = 0d;

                            if(i == 0){
                                String dsHexCor = ItemLegenda.getHexCor(legenda.getCor(vlItemA, vlItemB));
                                sbuffer.append("listaRegiao[\""); 
                                sbuffer.append(t.getDsCodigo());
                                sbuffer.append("\"] = new Regiao(\"");
                                sbuffer.append(t.toString());
                                sbuffer.append("\", '");
                                sbuffer.append(mapItemLegenda.get(dsHexCor));
                                sbuffer.append("');\n");
                            }
                            if(objetoFatoPeriodoB == null){
                                sbuffer.append("listaRegiao[\"");
                                sbuffer.append(t.getDsCodigo());
                                sbuffer.append("\"].listaFato[");
                                sbuffer.append(String.valueOf(i));
                                sbuffer.append("] = '<td class=\"ol-popup-text\">");
                                sbuffer.append(of.getFato().getDsFato());
                                sbuffer.append(" = ");
                                sbuffer.append(of.getFato().getFormatedValue(vlItemA));
                                sbuffer.append(" ");
                                sbuffer.append(of.getFato().getDsUnidade());
                                sbuffer.append("s</td>';\n");
                            }else{
                                ObjetoFatoProporcao objetoFatoProporcao = new ObjetoFatoProporcao(vlItemA, vlItemB);
                                sbuffer.append("listaRegiao[\"");
                                sbuffer.append(t.getDsCodigo());
                                sbuffer.append("\"].listaFato[");
                                sbuffer.append(String.valueOf(i));
                                sbuffer.append("] = '<td><table><tr><td class=\"ol-popup-text\">");
                                sbuffer.append(of.getFato().getDsFato());
                                sbuffer.append("</td></tr><tr><td class=\"ol-popup-text\">- ");
                                sbuffer.append(objetoFatoPeriodoA.getPeriodoIntervalo().toString());
                                sbuffer.append(" = ");
                                sbuffer.append(of.getFato().getFormatedValue(vlItemA));
                                sbuffer.append(" ");
                                sbuffer.append(of.getFato().getDsUnidade());
                                sbuffer.append("s</td></tr><tr><td class=\"ol-popup-text\">- ");
                                sbuffer.append(objetoFatoPeriodoB.getPeriodoIntervalo().toString());
                                sbuffer.append(" = ");
                                sbuffer.append(of.getFato().getFormatedValue(vlItemB));
                                sbuffer.append(" ");
                                sbuffer.append(of.getFato().getDsUnidade());
                                sbuffer.append("s</td></tr><tr><td class=\"ol-popup-text\">- ");
                                sbuffer.append(objetoFatoProporcao.toString());
                                sbuffer.append("</td></tr></table></td>';\n");
                            }

                            qtAtual++;
                            double vlProgresso = (qtAtual.doubleValue()/qtTotal.doubleValue());
                            pcProgresso = Math.round((vlProgresso) * 100);
                        }
                    }

                    sbuffer.append("map.addLayer(new ol.layer.Vector({\n");
                    sbuffer.append("    maxResolution: "); 
                    sbuffer.append(rgps.getDsMaxResolution()); 
                    sbuffer.append(",\n");
                    sbuffer.append("    minResolution: "); 
                    sbuffer.append(rgps.getDsMinResolution()); 
                    sbuffer.append(",\n");
                    sbuffer.append("    source: new ol.source.GeoJSON({\n");
                    sbuffer.append("        projection: 'EPSG:3857',\n");
                    sbuffer.append("        url: \"/GeoInfo/faces/javax.faces.resource/data/"); 
                    sbuffer.append(rgps.getDsGeoJSON()); 
                    sbuffer.append("\"\n");
                    sbuffer.append("    }),\n");
                    sbuffer.append("    style: function(feature, resolution){\n");
                    sbuffer.append("        var dsInfo;\n");
                    sbuffer.append("        if(feature){\n");
                    sbuffer.append("            dsInfo = feature.get('CDPAIS');\n");
                    sbuffer.append("            if(feature.get('CDESTADO')){\n"); 
                    sbuffer.append("                dsInfo += \"-\" + feature.get('CDESTADO');\n");
                    sbuffer.append("                if(feature.get('CDCIDADE')){\n"); 
                    sbuffer.append("                    dsInfo += \"-\" + feature.get('CDCIDADE');\n");
                    sbuffer.append("                }\n");
                    sbuffer.append("            }\n");
                    sbuffer.append("            return listaEstilo"); 
                    sbuffer.append(dsClassScript); 
                    sbuffer.append("[listaRegiao[dsInfo].dsItemLegenda];\n"); 
                    sbuffer.append("        }else{\n");
                    sbuffer.append("            return new ol.style.Style({\n");
                    sbuffer.append("                fill: new ol.style.Fill({\n");
                    sbuffer.append("                    color: 'rgba(255, 255, 255, 0.0)'\n");
                    sbuffer.append("                }),\n");
                    sbuffer.append("                stroke: new ol.style.Stroke({\n");
                    sbuffer.append("                    color: '#959595',\n");
                    sbuffer.append("                    width: 1\n");
                    sbuffer.append("                })\n");
                    sbuffer.append("            });\n");
                    sbuffer.append("        }\n");
                    sbuffer.append("    }\n");
                    sbuffer.append("}));");
                }
            }
            dsScriptMap = sbuffer.toString();
            inConstruiu = true;
        }

        public ScriptMapCreator(List<Fato> listaFato, PeriodoIntervaloComparavel periodoIntervaloComparavel, 
                    EntityManager entityManager, Class<T> classScript, Pessoa pessoaLogada) throws ObjetoFatoBuilderException{
            inConstruiu = false;
            dsScriptMap = "";
            pcProgresso = new Long(0);

            dsClassScript = classScript.getSimpleName();
            listaObjetoFato = ListaObjetoFato.create(listaFato, periodoIntervaloComparavel, entityManager, classScript, pessoaLogada);
            Repository repository = RepositoryFactory.createByGroupable(classScript, entityManager);
            rgps = new RegiaoGeograficaPoligonalSettings(classScript);

            listaRegiaoGeograficaPoligonal = null;
            if(repository != null){
                listaRegiaoGeograficaPoligonal = repository.getList();
            }
        }

        public String getDsScriptMap() throws LegendaException {
            if(!inConstruiu)
                construir();
            return dsScriptMap;
        }

        public ListaObjetoFato<T> getListaObjetoFato() throws LegendaException {
            if(!inConstruiu)
                construir();
            return listaObjetoFato;
        }

        public List<T> getListaRegiaoGeograficaPoligonal() throws LegendaException {
            if(!inConstruiu)
                construir();
            return listaRegiaoGeograficaPoligonal;
        }
        
    }
    
}
