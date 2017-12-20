package com.acessevip.tvchat.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.acessevip.tvchat.R;

/**
 * Created by Henrique on 02/12/2015.
 */
public class PrefsUtils {
    //verifica se o usuario marcou o checbox de push ON nas configuraçoes
    public static boolean isCheckPushOn(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(context.getString(R.string.push_pref),false);
    }
}
