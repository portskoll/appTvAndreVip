package com.acessevip.tvchat.util;

import android.app.Activity;
import android.content.Context;

import com.acessevip.tvchat.R;
import com.tjeannin.apprate.AppRate;

/**
 * Created by Henrique on 12/07/2017.
 */

public abstract class AvaliarAPP {

    public static void agora(Context ctx){

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx)
                .setTitle("Por Favor")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Ajude a manter este app, sua opinião é muito importante. Avaliar agora ?")
                .setPositiveButton("AVALIAR", null)
                .setNegativeButton("Nunca", null)
                .setNeutralButton("Talves depois", null);
        new AppRate((Activity) ctx).setCustomDialog(builder).init();


    }
}
