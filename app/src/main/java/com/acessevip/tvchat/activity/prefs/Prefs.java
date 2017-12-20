package com.acessevip.tvchat.activity.prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Henrique on 10/07/2017.
 */


public class Prefs {
    public static final String PREF_ID = "VipChat";

    public static void setString(Context context,String chave,String valor){
        SharedPreferences pref = context.getSharedPreferences(PREF_ID, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(chave,valor);
        editor.commit();
    }

    public static String getString(Context context,String chave){
        SharedPreferences pref = context.getSharedPreferences(PREF_ID, 0);
        String s =pref.getString(chave,"");
        return s;
    }

}
