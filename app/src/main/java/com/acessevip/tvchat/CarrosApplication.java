package com.acessevip.tvchat;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

/**
 * Created by Henrique on 14/11/2015.
 */
public class CarrosApplication  extends Application{
    private static final String TAG = "CarrosApplication";
    private static CarrosApplication instance = null;

    public static CarrosApplication getInstance() {
        return instance;//singleton
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate() {
        Log.d(TAG,"CarrosApplication.onCreate()");
        instance = this;

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG,"CarrosApplication.onTerminate()");
    }
}
