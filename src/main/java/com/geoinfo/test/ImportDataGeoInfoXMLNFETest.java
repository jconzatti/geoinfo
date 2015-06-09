package com.geoinfo.test;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class ImportDataGeoInfoXMLNFETest {
    
    private static Element getChildElement(Element parentElement, String dsChildElement){
        Element childElement = null;
        List lista = parentElement.getChildren();
        Iterator i =  lista.iterator();
        while((i.hasNext())&&(childElement == null)){
            Element e = (Element)i.next();
            String dsNome = e.getName();
            if(dsNome.equals(dsChildElement)){
                childElement = e;
            }
        }
        return childElement;
    }
    
    public static void main(String[] args) {
        try {
            Document document = new SAXBuilder().build(new File("C:\\Users\\Administrador\\Desktop\\42150203522601000152550030000006001000014399-procNFe.xml"));
            Element element = document.getRootElement();
            if(element.getName().equals("nfeProc"))
                System.out.println("É XML NFe!");
            else
                System.out.println("Não é XML NFe!");
            
            Element e1 = getChildElement(element, "NFe");
            if(e1 != null){
                Element e2 = getChildElement(e1, "infNFe");
                if(e2 != null){
                    Element e3 = getChildElement(e2, "ide");
                    if(e3 != null){
                        Iterator i =  e3.getChildren().iterator();
                        while(i.hasNext()){
                            Element e = (Element)i.next();
                            String dsNome = e.getName();
                            if(dsNome.equals("mod")){
                                System.out.println("Modelo: "+e.getText());
                            }else if(dsNome.equals("serie")){
                                System.out.println("Série: "+e.getText());
                            }else if(dsNome.equals("nNF")){
                                System.out.println("Nota Fiscal: "+e.getText());
                            }
                        }
                    }
                }
            }
        } catch (JDOMException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        Long cdModelo = (long) 55;
        Long cdSerie = (long) 3;
        Long nrNotaFiscal = (long) 600;
        Long cdVenda = Long.parseLong(new DecimalFormat("00").format(cdModelo) +
                                            new DecimalFormat("000").format(cdSerie) +
                                            new DecimalFormat("000000000").format(nrNotaFiscal));
        System.out.println("Venda: "+cdVenda);
    }
    
}
