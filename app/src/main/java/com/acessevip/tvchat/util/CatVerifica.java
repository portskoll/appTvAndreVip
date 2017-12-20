package com.acessevip.tvchat.util;

/**
 * Created by Henrique on 26/12/2015.
 */


public class CatVerifica {


    public static  boolean VERIFICA(String t) {

        String[] tipo = {"TvAberta","Esporte","Noticia","Infantil","Documentario","Variedade","Filme","novo"};
        boolean ver = false;

        for (int i=0;i<tipo.length;i++) {
           if(t.equals(tipo[i])){
               ver = true;
           }

       }
        return ver;
    }

}
