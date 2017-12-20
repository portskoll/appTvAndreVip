package com.acessevip.tvchat.activity.prefs;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;

import com.acessevip.tvchat.R;

/**
 * Created by Henrique on 02/12/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ConfiguracoesV11Activity  extends android.app.Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //adiciona o fragmente de configuraçoes
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content,new PrefsFragment());
        ft.commit();
    }
    public static class PrefsFragment extends android.preference.PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
