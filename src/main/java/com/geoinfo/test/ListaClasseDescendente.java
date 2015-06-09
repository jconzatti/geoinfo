package com.geoinfo.test;

import com.geoinfo.model.RegiaoGeograficaPoligonal;
import java.lang.reflect.Method;

public class ListaClasseDescendente {
    
    public static void main(String[] args) {
        System.out.println("Listando classes descendentes de " + RegiaoGeograficaPoligonal.class.getSimpleName() + " (ISTO NÃO DEU CERTO)");
        Method[] listaMetodo = RegiaoGeograficaPoligonal.class.getSuperclass().getDeclaredMethods();
        for(Method m : listaMetodo)
            System.out.println(m.getName());
    }
}
