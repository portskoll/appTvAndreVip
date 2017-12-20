package com.acessevip.tvchat.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Henrique on 09/12/2015.
 */
public class PrefUser {
    public static final String USERID = "id_usuario";
    public static final String AJUDA = "tela_cheia";


    public static  void setUserID(Context context,String id, int valor) {
        SharedPreferences pref = context.getSharedPreferences(USERID,0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(id,valor);
        editor.commit();
    }

    public  static int getUserID(Context context,String id){
        SharedPreferences pref = context.getSharedPreferences(USERID,0);
        int idU = pref.getInt(id,-1);
        return idU;
    }

    public static  void setUserTcheia(Context context,String id, int valor) {
        SharedPreferences pref = context.getSharedPreferences(AJUDA,0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(id,valor);
        editor.commit();
    }

    public  static int getUserTcheia(Context context,String id){
        SharedPreferences pref = context.getSharedPreferences(AJUDA,0);
        int idU = pref.getInt(id,-1);
        return idU;
    }
}
