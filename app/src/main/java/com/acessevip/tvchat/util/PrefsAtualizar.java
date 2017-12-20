package com.acessevip.tvchat.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Henrique on 06/02/2016.
 */
public  class PrefsAtualizar {
    public static final String ATUDO = "atualizar";


    public static  void setAtualiza(Context context,String at, int valor1) {
        SharedPreferences pref = context.getSharedPreferences(ATUDO,0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(at,valor1);
        editor.commit();
    }

    public  static int getAtualiza(Context context,String at){
        SharedPreferences pref = context.getSharedPreferences(ATUDO,0);
        int atU = pref.getInt(at,-1);
        return atU;
    }
}
