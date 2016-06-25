package com.geoinfo.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.component.layout.LayoutUnit;
import org.primefaces.event.ToggleEvent;

@ManagedBean
@SessionScoped
public class LayoutViewBean implements Serializable{
    private String dsOutputScriptLayout;
    private boolean inFilterGeoinfoColapsed;
    private boolean inAnalizeGeoinfoColapsed;

    public LayoutViewBean() {
        inicializar();
    }
    
    private void inicializar(){
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append("inFilterGeoinfoColapsed = false;\n");
        sbuffer.append("inAnalizeGeoinfoColapsed = true;\n");
        this.dsOutputScriptLayout = sbuffer.toString();
        this.inFilterGeoinfoColapsed = false;
        this.inAnalizeGeoinfoColapsed = true;
    }
    
    public void reiniciar(){
        inicializar();
    }

    public String getDsOutputScriptLayout() {
        return dsOutputScriptLayout;
    }

    public void setDsOutputScriptLayout(String dsOutputScriptLayout) {
        this.dsOutputScriptLayout = dsOutputScriptLayout;
    }
	
    public void handleToggle(ToggleEvent event) {
        if(((LayoutUnit)event.getComponent()).getPosition().equalsIgnoreCase("west")){
            switch(event.getVisibility()){
                case VISIBLE: 
                    this.inFilterGeoinfoColapsed = false;
                    break;
                case HIDDEN: 
                    this.inFilterGeoinfoColapsed = true;
                    break;
            }
        }else if(((LayoutUnit)event.getComponent()).getPosition().equalsIgnoreCase("east")){
            switch(event.getVisibility()){
                case VISIBLE: 
                    this.inAnalizeGeoinfoColapsed = false;
                    break;
                case HIDDEN: 
                    this.inAnalizeGeoinfoColapsed = true;
                    break;
            }
        }
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append("inFilterGeoinfoColapsed = ");
        if(this.inFilterGeoinfoColapsed)
            sbuffer.append("true;\n");
        else
            sbuffer.append("false;\n");
        sbuffer.append("inAnalizeGeoinfoColapsed = ");
        if(this.inAnalizeGeoinfoColapsed)
            sbuffer.append("true;\n");
        else
            sbuffer.append("false;\n");
        this.dsOutputScriptLayout = sbuffer.toString();
    }
    
}
